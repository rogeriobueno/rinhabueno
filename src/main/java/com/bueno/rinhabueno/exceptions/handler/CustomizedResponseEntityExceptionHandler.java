package com.bueno.rinhabueno.exceptions.handler;



import com.bueno.rinhabueno.dto.ExceptionResponseDTO;
import com.bueno.rinhabueno.exceptions.BadRequestException;
import com.bueno.rinhabueno.exceptions.ConflictRequestException;
import com.bueno.rinhabueno.exceptions.ResourceNotFoundException;
import com.bueno.rinhabueno.exceptions.ResourceUnprocessableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Map<String, String> validationsErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();
        validationErrorList.forEach(objError -> {
            String fieldName = ((FieldError) objError).getField();
            String validationMsg = objError.getDefaultMessage();
            validationsErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationsErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponseDTO> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        log.error(ex.getMessage(), ex);
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

    @ExceptionHandler(ConflictRequestException.class)
    public final ResponseEntity<ExceptionResponseDTO> handleConflictRequestException(Exception ex, WebRequest request) {
        ExceptionResponseDTO exceptionResponseDTO =
                new ExceptionResponseDTO(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false));
        return new ResponseEntity<>(exceptionResponseDTO, HttpStatus.CONFLICT);
    }

}
