package com.bit.microservices.mitra.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ValidationUtils {
    private ValidationUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isValidUserIdFormat(String value) {
        if (value == null) {
            return false;
        }

        // Split
        String[] parts = value.split("~", 2);

        // Check if we have exactly 2 parts
        if (parts.length != 2) {
            return false;
        }

        // Validate code part (first part)
        String code = parts[0].trim();
        if (code.isEmpty()) {
            return false;
        }

        // Validate UUID part (second part)
        String uuidStr = parts[1].trim();
        try {
            UUID.fromString(uuidStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
