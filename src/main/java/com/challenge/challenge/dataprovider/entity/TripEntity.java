package com.challenge.challenge.dataprovider.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TripEntity {

    private LocalDateTime tpep_pickup_datetime;
    private LocalDateTime tpep_dropoff_datetime;
    private Long PULocationID;
    private Long DOLocationID;

}
