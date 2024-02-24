package com.bueno.rinhabueno.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"limite", "saldo"})
public record TransactionResponse(
        @JsonProperty("limite")
        Long limite,
        @JsonProperty("saldo")
        Long saldo
) {
}
