package com.ask.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonController {

    @GetMapping(path = "/**")
    public ResponseEntity<String> getPageNotFound() {
        return new ResponseEntity<>(
                "Bad Routing Error",
                HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/**")
    public ResponseEntity<String> postPageNotFound() {
        return new ResponseEntity<>(
                "Bad Routing Error",
                HttpStatus.BAD_REQUEST);
    }
}
