package com.challenge.challenge.usecase;

import com.challenge.challenge.util.ParquetIterator;
import com.challenge.challenge.util.Utilitary;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericRecordBuilder;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
public class ParquetDataImportUseCase {

    public <T> List<T> convertFile(File parquetFile, Class<T> clazz) throws IOException {
        long fileSize = parquetFile.length();
        int batchSize = calculateBatchSize(fileSize);

        ExecutorService executorService = Executors.newFixedThreadPool(batchSize);

        try (ParquetReader<GenericRecord> reader = AvroParquetReader.<GenericRecord>builder(new Path(parquetFile.getAbsolutePath())).build()) {
            Spliterator<GenericRecord> spliterator = Spliterators.spliteratorUnknownSize(new ParquetIterator(reader), Spliterator.ORDERED);

            Stream<GenericRecord> recordStream = StreamSupport.stream(spliterator, false);

            List<T> results = recordStream
                    .collect(Collectors.groupingByConcurrent(e -> Thread.currentThread().getId() % batchSize))
                    .values().parallelStream()
                    .flatMap(batch -> CompletableFuture.supplyAsync(() -> batch, executorService).join().stream())
                    .map(record -> mapGenericRecordToObject(record, clazz))
                    .collect(Collectors.toList());

            return results;
        } catch (IOException e) {
            log.error("Error reading Parquet file: " + e.getMessage());
            throw e;
        } finally {
            executorService.shutdown();
        }
    }


    private  <T> T mapGenericRecordToObject(GenericRecord record, Class<T> clazz) {
        GenericRecordBuilder recordBuilder = new GenericRecordBuilder(record.getSchema());

        Object obj = null;
        try {
            obj = clazz.getConstructor().newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields){
                field.setAccessible(true);

                Object value = Utilitary.convertAvroValue(field.getName(), record.get(field.getName()));

                field.set(obj, value);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return (T) obj;
    }

    private int calculateBatchSize(long fileSize) {
        int cores = Runtime.getRuntime().availableProcessors();

        // Calcula o batchSize com base no número total de registros e no número de núcleos disponíveis.
        int batchSize = (int) Math.ceil((double) fileSize / cores);

        return batchSize;
    }

}
