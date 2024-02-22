package com.bueno.rinhabueno.dto;

import com.bueno.rinhabueno.entity.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"valor", "tipo", "descricao"})
public record TransactionRequest(
        @NotNull(message = "deve ser informado")
        @Positive(message = "deve ser positivo")
        @JsonProperty("valor")
        Long valor,
        @Pattern(regexp = "^([cdCD])$", message = "deve ser (c) ou (d)")
        @NotEmpty(message = "deve ser informado")
        @JsonProperty("tipo")
        String tipo,
        @NotEmpty(message = "deve ser informada")
        @JsonProperty("descricao")
        String descricao
) {
}
