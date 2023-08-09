package com.challenge.challenge.entrypoint.operations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface ImportDataOperations {

    @GetMapping(value="/import-data")
    ResponseEntity<Void> importInitialData();

}
