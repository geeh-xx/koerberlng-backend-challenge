package com.challenge.challenge.entrypoint.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class TripResponse {

    private LocalDateTime pickUpDatetime;
    private LocalDateTime dropOffDatetime;
    private Long PuLocationID;
    private Long DoLocationID;

}
