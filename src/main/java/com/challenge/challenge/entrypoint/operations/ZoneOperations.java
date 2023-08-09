package com.challenge.challenge.entrypoint.operations;


import com.challenge.challenge.entrypoint.response.TopZoneResponse;
import com.challenge.challenge.entrypoint.response.TripResponse;
import com.challenge.challenge.entrypoint.response.ZoneResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

public interface ZoneOperations {

    @GetMapping(value="/zone-trips")
    ResponseEntity<ZoneResponse> getZoneTrips(@RequestParam String zone, @RequestParam @DateTimeFormat(iso = DATE) LocalDate date);

    @GetMapping(value="/top-zones")
    ResponseEntity<TopZoneResponse> getTopZones(@RequestParam String order);


}
