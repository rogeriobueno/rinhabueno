package com.bueno.rinhabueno.exceptions.handler;


import com.bueno.rinhabueno.dto.ExceptionResponseDTO;
import com.bueno.rinhabueno.exceptions.BadRequestException;
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
        LOG.error(ex.getMessage(), ex);
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleNotResourceFoundException(Exception ex, WebRequest request) {
        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceUnprocessableException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleResourceUnprocessableException(Exception ex, WebRequest request) {
        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
