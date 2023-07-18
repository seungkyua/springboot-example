package com.ask.example.domain;

public class BadReqeustException extends RuntimeException {

    private String errorMessage;

    public BadReqeustException(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
