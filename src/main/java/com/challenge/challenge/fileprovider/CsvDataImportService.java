package com.challenge.challenge.fileprovider;

import com.challenge.challenge.util.Utilitary;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CsvDataImportService {


    public <T> List<T> convertFile(InputStream is, Class<T> clazz) throws IOException {
        List<T> results = new ArrayList<>();

        long fileSize = is.available();
        int batchSize = calculateBatchSize(fileSize);

        ExecutorService executorService = Executors.newFixedThreadPool(batchSize);

        try (Reader reader = new InputStreamReader(is);
             CSVParser parser = CSVFormat.EXCEL.withHeader().parse(reader)){

            Stream<CSVRecord> records = parser.getRecords().stream();

            results = records
                    .collect(Collectors.groupingByConcurrent(e -> Thread.currentThread().getId() % batchSize))
                    .values().parallelStream()
                    .flatMap(batch -> CompletableFuture.supplyAsync(() -> batch, executorService).join().stream())
                    .map(record -> mapRecordToObj(record,clazz))
                    .collect(Collectors.toList());

            return results;
        }catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }finally {
            executorService.shutdown();
        }

    }


    private int calculateBatchSize(long fileSize) {
        int cores = Runtime.getRuntime().availableProcessors();

        // Calcula o batchSize com base no número total de registros e no número de núcleos disponíveis.
        int batchSize = (int) Math.ceil((double) fileSize / cores);

        return batchSize;
    }

    private <T> T mapRecordToObj(CSVRecord record, Class<T> clazz) {
        Object obj = null;
        try {
             obj = clazz.getConstructor().newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields){
                field.setAccessible(true);
                Object value = Utilitary.convertToFieldType(field.getType(), record.get(field.getName()));
                field.set(obj, value);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return (T) obj;
    }


}
