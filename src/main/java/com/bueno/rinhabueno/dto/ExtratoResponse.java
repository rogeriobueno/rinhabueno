package com.bueno.rinhabueno.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"saldo", "ultimas_transacoes"})
public record ExtratoResponse(
        @JsonProperty("saldo")
        SaldoResponse saldo,
        @JsonProperty("ultimas_transacoes")
        List<TransactionItemResponse> ultimasTransacoes

) {
}
