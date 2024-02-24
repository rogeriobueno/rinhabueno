package com.bueno.rinhabueno.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"valor", "tipo", "descricao"})
public record TransactionRequest(
        @JsonProperty("valor")
        Long valor,
        @JsonProperty("tipo")
        String tipo,

        @JsonProperty("descricao")
        String descricao
) {
}
