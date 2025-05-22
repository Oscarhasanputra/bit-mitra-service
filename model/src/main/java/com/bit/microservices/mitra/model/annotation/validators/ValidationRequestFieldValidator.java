package com.bit.microservices.mitra.model.annotation.validators;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.EnumAction;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
public class ValidationRequestFieldValidator implements ConstraintValidator<ValidationRequestField, Object> {

    private boolean notEqualALLString;
    private boolean notContainWhitespace;
    private boolean notNull;
    private boolean notEmpty;
    private boolean notBlank;
    private boolean notZero;
    private boolean mustContainTilde;


    private boolean mustContainAlphanumeric;

    private FieldType fieldType;
    private Class<? extends EnumAction> enumClass;

    @Override
    public void initialize(ValidationRequestField constraintAnnotation) {
        this.notNull = constraintAnnotation.notNull();
        this.notEmpty = constraintAnnotation.notEmpty();
        this.notBlank = constraintAnnotation.notBlank();
        this.fieldType = constraintAnnotation.fieldType();
        this.enumClass = constraintAnnotation.enumClass();
        this.notZero = constraintAnnotation.notZero();
        this.mustContainTilde = constraintAnnotation.mustContainTilde();
        this.mustContainAlphanumeric = constraintAnnotation.mustContainAlphanumeric();
        this.notContainWhitespace = constraintAnnotation.notContainWhitespace();
        this.notEqualALLString = constraintAnnotation.notEqualALLString();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        if (notNull && value == null) {
            return fieldTypeNotFilled(context);
        }

        if (value == null) {
            return true; // Skip further checks if value is null and not required
        }

        return switch (fieldType) {
            case STRING -> validateString(value, context);
            case NUMBER -> validateNumber(value, context);
            case ISO_DATE_TIME -> validateIsoDateTime(value, context);
            case BOOLEAN -> validateBoolean(value, context);
            case ENUM -> validateEnumField(value, context);
            case EMAIL -> validateEmailField(value, context);
            case LOCAL_DATE -> validateLocalDate(value, context);
            case JSON_NODE -> validateJsonNode(value, context);
            case LIST -> validateList(value,context);
            default -> fieldTypeNotFilled(context);
        };
    }

    private boolean validateEmailField(Object value, ConstraintValidatorContext context) {
        boolean response = true;

        if (!(value instanceof String str)) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
            return false;
        }

        if (notEmpty && str.isEmpty()) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_CANNOT_EMPTY.name()).addConstraintViolation();
            return false;
        }

        if (notBlank && str.isBlank()) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_CANNOT_EMPTY.name()).addConstraintViolation();
            return false;
        }

        if (!str.matches("^[^@\\s]+@[^@\\s.]+\\.[^@\\s]+$")) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_EMAIL.name()).addConstraintViolation();
            return false;
        }

        return response;
    }

    private boolean validateJsonNode(Object value, ConstraintValidatorContext context){
        boolean response = true;
        if (value instanceof JsonNode json){
            if (notEmpty && json.isEmpty()){
                context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_CANNOT_EMPTY.name())
                        .addConstraintViolation();
                response = false;
            }
        }else{
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name())
                    .addConstraintViolation();
            response = false;
        }

        return response;
    }

    private boolean validateLocalDate(Object value, ConstraintValidatorContext context) {
        boolean response = true;

        if (value != null) {
            if (value instanceof LocalDate) {
                return true; // Already a valid LocalDate, no need to validate further
            }

            if (!(value instanceof String dateTimeStr)) {
                context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_ENUM.name())
                        .addConstraintViolation();
                return false;
            }

            try {
                DateTimeFormatter.ISO_LOCAL_DATE.parse(dateTimeStr);
            } catch (DateTimeParseException e) {
                context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_ENUM.name())
                        .addConstraintViolation();
                response = false;
            }
        }

        return response;
    }

    private boolean validateEnumField(Object value, ConstraintValidatorContext context) {

        if (Boolean.FALSE.equals(EnumAction.isValidValue(this.enumClass, value.toString()))) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_ENUM.name()).addConstraintViolation();
            return false;
        }
        return true;
    }

    private boolean validateString(Object value, ConstraintValidatorContext context) {
        boolean response = true;

        if (!(value instanceof String)) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
            return false;
        }

        String str = (String) value;

        if (notEmpty && str.isEmpty()) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_CANNOT_EMPTY.name()).addConstraintViolation();
            return false;
        }

        if (notBlank && str.isBlank()) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_CANNOT_EMPTY.name()).addConstraintViolation();
            return false;
        }

        if (notZero && str.equalsIgnoreCase("0")) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
            response = false;
        }

        if (mustContainTilde && !str.contains("~")) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
            response = false;
        }

        if (notContainWhitespace && str.chars().anyMatch(Character::isWhitespace)){
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_CODE_CONTAIN_WHITESPACE.name()).addConstraintViolation();
            response = false;
        }

        if (notEqualALLString && str.equalsIgnoreCase("all")){
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_MUST_NOT_FILLED_ALL.name()).addConstraintViolation();
            response = false;
        }

        if (mustContainAlphanumeric && !str.matches(".*[a-zA-Z0-9].*")) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_MUST_CONTAIN_ALPHANUMERIC.name()).addConstraintViolation();
            response = false;
        }


        return response;
    }

    private boolean validateNumber(Object value, ConstraintValidatorContext context) {
        boolean response = true;

        if (!(value instanceof Number)) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
            response = false;
        }

        return response;
    }

    private boolean validateIsoDateTime(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values should be handled by @NotNull if required
        }

        if (value instanceof OffsetDateTime) {
            return true; // Already a valid OffsetDateTime
        }

        if (!(value instanceof String dateTimeStr)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name())
                    .addConstraintViolation();
            return false;
        }

        try {
            OffsetDateTime.parse(dateTimeStr, DateTimeFormatter.ISO_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name())
                    .addConstraintViolation();
            return false;
        }
    }

    private boolean validateBoolean(Object value, ConstraintValidatorContext context) {
        boolean response = true;
        if (!(value instanceof Boolean)) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
            response = false;
        }
        return response;
    }

    private boolean fieldTypeNotFilled(ConstraintValidatorContext context) {
        context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_FIELD_CANNOT_NULL.name()).addConstraintViolation();
        return false;
    }

    private boolean validateList(Object value, ConstraintValidatorContext context) {
        boolean response = true;
        if (!(value instanceof List<?>)) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_TYPE.name()).addConstraintViolation();
           return false;
        }
        List<?> list = (List) value;

        if (notEmpty && list.isEmpty()) {
            context.buildConstraintViolationWithTemplate(ResponseCodeMessageEnum.FAILED_INVALID_CANNOT_EMPTY.name()).addConstraintViolation();
            return false;
        }

        return response;
    }



}