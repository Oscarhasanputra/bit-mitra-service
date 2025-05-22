package com.bit.microservices.mitra.model.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum UserTypeEnum implements EnumAction {
    INTERNAL("INTERNAL"),
    EXTERNAL("EXTERNAL");

    private final String name;

    UserTypeEnum(String name) {
        this.name = name;
    }

    @JsonValue
    public String getValue() {
        return this.name;
    }

    @JsonCreator
    public static UserTypeEnum fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Invalid user type: " + value);
        }
        return switch (value.toUpperCase()) {
            case "INTERNAL" -> INTERNAL;
            case "EXTERNAL" -> EXTERNAL;
            default -> throw new IllegalArgumentException("Invalid user type: " + value);
        };
    }


    @Override
    public Boolean valueIsExist(String value) {
        try {
            fromValue(value);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
}
