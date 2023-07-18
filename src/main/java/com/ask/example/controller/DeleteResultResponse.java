package com.ask.example.controller;

import lombok.Getter;

@Getter
public class DeleteResultResponse {

    private final boolean success;
    private final String message;

    public DeleteResultResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
