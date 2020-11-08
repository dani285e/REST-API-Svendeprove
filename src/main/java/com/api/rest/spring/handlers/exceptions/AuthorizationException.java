package com.api.rest.spring.handlers.exceptions;


public class AuthorizationException extends ApiException {

    public AuthorizationException(String rejectReason) {
        super(rejectReason);
    }
}
