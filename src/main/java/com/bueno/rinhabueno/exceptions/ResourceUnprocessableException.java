package com.bueno.rinhabueno.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ResourceUnprocessableException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -1621458548784153112L;

    public ResourceUnprocessableException(String exception) {
        super(exception);
    }

}
