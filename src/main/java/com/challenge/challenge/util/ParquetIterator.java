package com.challenge.challenge.util;

import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.hadoop.ParquetReader;

import java.io.IOException;
import java.util.Iterator;

public class ParquetIterator implements Iterator<GenericRecord> {

    private final ParquetReader<GenericRecord> reader;
    private GenericRecord nextRecord;

    public ParquetIterator(ParquetReader<GenericRecord> reader) {
        this.reader = reader;
        try {
            this.nextRecord = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasNext() {
        return nextRecord != null;
    }

    @Override
    public GenericRecord next() {
        GenericRecord currentRecord = nextRecord;
        try {
            nextRecord = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return currentRecord;
    }

}
