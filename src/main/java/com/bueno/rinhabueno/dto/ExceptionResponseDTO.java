package com.bueno.rinhabueno.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder(value = {"timestamp", "message", "details"})
public record ExceptionResponseDTO(
        LocalDateTime timestamp,
        String message,
        String details

) {
}
