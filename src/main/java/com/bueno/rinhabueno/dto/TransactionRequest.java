package com.bueno.rinhabueno.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"valor", "tipo", "descricao"})
public record TransactionRequest(
        @JsonProperty(value = "valor", required = true)
        Long valor,
        @JsonProperty(value = "tipo", required = true)
        String tipo,
        @JsonProperty(value = "descricao", required = true)
        String descricao
) {
}
