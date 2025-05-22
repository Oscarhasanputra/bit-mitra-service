package com.bit.microservices.mitra.model.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmptyStringValidator.class)
public @interface ValidateEmptyString {
    String message() default "Field cannot be an empty string";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
