package com.challenge.challenge.fileprovider;

import com.challenge.challenge.dataprovider.entities.ZoneEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class CsvDataImportServiceTest {

    @Mock
    private ExecutorService executorServiceMock;

    private CsvDataImportService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CsvDataImportService();
    }

    @Test
    void testConvertCsvFile() throws Exception {

        // given
        String csvData = "Zone,Borough,LocationID,service_zone\nBedford,Manhattan,13,Yellow Zone\nWoodside,Queens,260,Boro Zone";
        InputStream inputStream = new ByteArrayInputStream(csvData.getBytes());
        Class<ZoneEntity> clazz = ZoneEntity.class;

        CSVRecord record1 = CSVParser.parse(csvData, CSVFormat.EXCEL.withHeader()).getRecords().get(0);
        CSVRecord record2 = CSVParser.parse(csvData, CSVFormat.EXCEL.withHeader()).getRecords().get(1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<List<CSVRecord>> completableFuture = CompletableFuture.completedFuture(List.of(record1, record2));
        when(executorServiceMock.submit(any(Callable.class))).thenReturn(completableFuture);

        // when
        List<ZoneEntity> results = service.convertFile(inputStream, ZoneEntity.class);

        // then
        assertEquals(2, results.size());
        assertEquals("Manhattan", results.get(0).getBorough());
        assertEquals("Bedford", results.get(0).getZone());
        assertEquals(13, results.get(0).getLocationID());
        assertEquals("Yellow Zone", results.get(0).getService_zone());

        assertEquals("Queens", results.get(1).getBorough());
        assertEquals("Woodside", results.get(1).getZone());
        assertEquals(260, results.get(1).getLocationID());
        assertEquals("Boro Zone", results.get(1).getService_zone());


    }

}
