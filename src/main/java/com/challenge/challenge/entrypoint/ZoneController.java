package com.challenge.challenge.entrypoint;

import com.challenge.challenge.entrypoint.operations.ZoneOperations;
import com.challenge.challenge.entrypoint.response.TopZoneResponse;
import com.challenge.challenge.entrypoint.response.TripResponse;
import com.challenge.challenge.entrypoint.response.ZoneResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class ZoneController implements ZoneOperations {


    @Override
    public ResponseEntity<ZoneResponse> getZoneTrips(String zone, LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<TopZoneResponse> getTopZones(String order) {
        return null;
    }

}
