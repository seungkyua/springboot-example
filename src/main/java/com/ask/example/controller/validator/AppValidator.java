package com.ask.example.controller.validator;

import com.ask.example.controller.AppRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

public class AppValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AppRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppRequest request = (AppRequest) target;

        if (Objects.isNull(request.getAppName())) {
            errors.rejectValue("appName", "NotNull", "appName is null");
            return;
        }

        if (request.getAppName().length() < 1 || request.getAppName().length() > 100) {
            errors.rejectValue(
                    "appName",
                    "LengthError",
                    "appName's length must be between 1 to 100");
        }

    }
}
