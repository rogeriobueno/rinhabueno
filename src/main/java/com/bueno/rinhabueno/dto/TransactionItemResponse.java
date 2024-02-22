package com.bueno.rinhabueno.dto;

import com.bueno.rinhabueno.entity.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Builder
@JsonPropertyOrder({"valor","tipo","descricao","realizada_em"})
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
