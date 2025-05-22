package com.bit.microservices.mitra.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmptyStringValidator implements ConstraintValidator<ValidateEmptyString, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return !value.trim().isEmpty();
    }
}
