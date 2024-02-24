package com.bueno.rinhabueno.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.ZonedDateTime;

@JsonPropertyOrder({"valor", "tipo", "descricao", "realizada_em"})
public record TransactionItemResponse(
        @JsonProperty("valor")
        Long valor,
        @JsonProperty("tipo")
        String tipo,

        @JsonProperty("descricao")
        String descricao,

        @JsonProperty("realizada_em")
        ZonedDateTime createAt
) {


}
