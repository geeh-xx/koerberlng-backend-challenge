package com.challenge.challenge.entrypoint.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Builder
@Getter
@Setter
public class ZoneResponse {

    private String zone;
    @JsonProperty("pu_total")
    private Long puTotal;
    @JsonProperty("do_total")
    private Long doTotal;


}
