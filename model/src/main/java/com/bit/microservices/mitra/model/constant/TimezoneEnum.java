package com.bit.microservices.mitra.model.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TimezoneEnum implements EnumAction {
    WIB("WIB"),
    WITA("WITA"),
    WIT("WIT"),
    NONE("NONE");

    private final String name;

    TimezoneEnum(String name) {
        this.name = name;
    }

    @JsonValue
    public String getValue() {
        return this.name;
    }

    @JsonCreator
    public static TimezoneEnum fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Invalid timezone: " + value);
        }
        return switch (value.toUpperCase()) {
            case "WIB" -> WIB;
            case "WITA" -> WITA;
            case "WIT" -> WIT;
            case "NONE" -> NONE;
            default -> throw new IllegalArgumentException("Invalid timezone: " + value);
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
