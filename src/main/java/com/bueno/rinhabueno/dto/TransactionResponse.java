package com.bueno.rinhabueno.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@JsonPropertyOrder({"limite", "saldo"})
public final class TransactionResponse {
    @JsonIgnore
    private final Integer returnStatus;
    @JsonProperty("limite")
    private final Long limite;
    @JsonProperty("saldo")
    private final Long saldo;
}
