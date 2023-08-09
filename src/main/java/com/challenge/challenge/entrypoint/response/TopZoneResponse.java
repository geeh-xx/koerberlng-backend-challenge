package com.challenge.challenge.entrypoint.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Builder
@Getter
@Setter
public class TopZoneResponse {

    @JsonProperty("top_zones")
    private List<ZoneResponse> topZones;

}
