package com.api.rest.spring.handlers.exceptions;

public class ValidationException extends ApiException {

    public ValidationException(String rejectReason) {
        super(rejectReason);
    }
}
