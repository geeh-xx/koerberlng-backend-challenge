package com.challenge.challenge;

import com.challenge.challenge.dataprovider.entities.TripEntity;
import com.challenge.challenge.fileprovider.CsvDataImportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ChallengeApplication {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(ChallengeApplication.class, args);
  }

}
