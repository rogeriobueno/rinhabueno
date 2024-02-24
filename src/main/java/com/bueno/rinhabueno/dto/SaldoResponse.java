package com.bueno.rinhabueno.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;

@JsonPropertyOrder({"total", "data_extrato", "limite"})
public record SaldoResponse(
        @JsonProperty("total")
        Long total,
        @JsonProperty("data_extrato")
        ZonedDateTime dataExtrato,
        @JsonProperty("limite")
        Long limite
) {

}
