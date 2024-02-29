package com.bueno.rinhabueno.exceptions.handler;


import com.bueno.rinhabueno.dto.ExceptionResponseDTO;
import com.bueno.rinhabueno.exceptions.ResourceNotFoundException;
import com.bueno.rinhabueno.exceptions.ResourceUnprocessableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponseDTO> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        LOG.error("Message 500 {} description {} ", ex.getMessage(), request.getDescription(true), ex);
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleNotResourceFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceUnprocessableException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleResourceUnprocessableException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
