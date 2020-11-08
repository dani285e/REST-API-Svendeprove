package com.api.rest.spring.handlers.exceptions;

public class ValidationException extends Exception {
    private String rejectReason;

    public ValidationException(String rejectReason){
        this.rejectReason = rejectReason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
