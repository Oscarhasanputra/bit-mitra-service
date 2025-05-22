package com.bit.microservices.mitra.model.annotation;


import com.bit.microservices.mitra.model.annotation.validators.ValidationRequestFieldValidator;
import com.bit.microservices.mitra.model.constant.EnumAction;
import com.bit.microservices.mitra.model.constant.FieldType;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidationRequestFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidationRequestField {

  boolean notEqualALLString() default false;
  boolean notContainWhitespace() default false;
  boolean notNull() default false;
  boolean notEmpty() default false;
  boolean notBlank() default false;
  boolean notZero() default false;
  boolean mustContainTilde() default false;
  boolean mustContainAlphanumeric() default false;
  FieldType fieldType();
  Class<? extends EnumAction> enumClass() default EnumAction.class;

  String message() default "Invalid field value";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};

}