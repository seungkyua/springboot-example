package com.ask.example.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class CommonResponse {

    @JsonProperty(value = "result")
    private CommonResponseInterface body;

    public CommonResponse (@Nullable CommonResponseInterface body) {
        this.body = body;
    }
}
