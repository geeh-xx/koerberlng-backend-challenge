package com.challenge.challenge.entrypoint;

import com.challenge.challenge.entrypoint.operations.TripOperations;
import com.challenge.challenge.entrypoint.response.TripResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController implements TripOperations {

    @Override
    public ResponseEntity<TripResponse> getYellowTrip() {
        return null;
    }

}
