package com.sventas.sventas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModelNotFoundException extends RuntimeException {

    @Getter
    private HttpStatus status;

    public ModelNotFoundException() {
    }

    public ModelNotFoundException(String msg) {
        super(msg);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public ModelNotFoundException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }
}
