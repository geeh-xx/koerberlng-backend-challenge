package com.challenge.challenge.entrypoint;

import com.challenge.challenge.entrypoint.operations.ImportDataOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportDataController implements ImportDataOperations {
    @Override
    public ResponseEntity<Void> importInitialData() {
        return null;
    }
}
