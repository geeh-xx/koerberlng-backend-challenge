package com.challenge.challenge.entrypoint.operations;

import com.challenge.challenge.entrypoint.response.TripResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface TripOperations {


    @GetMapping(value="/list-yellow")
    ResponseEntity<TripResponse> getYellowTrip();

}
