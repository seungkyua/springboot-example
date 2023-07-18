package com.ask.example.controller;

import com.ask.example.domain.BadReqeustException;
import com.ask.example.domain.FileDownloadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadReqeustException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadReqeustException ex) {

        System.out.println("Error Message: " + ex.getErrorMessage());
        return new ResponseEntity<>(
                new ErrorResponse(ex.getErrorMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity handleFileDownloadException(FileDownloadException e) {
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        return new ResponseEntity<>(
                new ErrorResponse(ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
