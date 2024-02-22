package com.bueno.rinhabueno.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@JsonPropertyOrder({"total","data_extrato", "limite"})
public record SaldoResponse(
        @JsonProperty("total")
        Long total,
        @JsonProperty("data_extrato")
        ZonedDateTime dataExtrato,
        @JsonProperty("limite")
        Long limite
) {

}
