package com.ask.example.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@ToString
public class AppRequest {

//    @NotNull(message = "appName can't be null")
//    @Length(min = 1, max = 10, message = "appName's length must be between 1 and 10")
    private String appName;

    public Long createApp(String appName) {
        this.appName = appName;

        System.out.println(this.appName);

        return 1_001_002L;
    }
}
