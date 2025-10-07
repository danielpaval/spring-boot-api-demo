package com.example.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.openapitools.jackson.nullable.JsonNullable;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, JsonNullable<String>> {

    @Override
    public boolean isValid(JsonNullable<String> value, ConstraintValidatorContext context) {
        if (value == null || !value.isPresent()) {
            return true;
        }
        String actualValue = value.get();
        return actualValue != null && !actualValue.trim().isEmpty();
    }
}

