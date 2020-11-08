package com.api.rest.spring.handlers.exceptions;

public class ApiException extends Exception {
    protected String errorReason;

    public ApiException(String rejectReason){
        this.errorReason = rejectReason;
    }

    public String getErrorReason() {
        return errorReason;
    }

    public void setErrorReason(String errorReason) {
        this.errorReason = errorReason;
    }
}
