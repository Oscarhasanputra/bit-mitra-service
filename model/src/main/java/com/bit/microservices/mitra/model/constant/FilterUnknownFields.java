package com.bit.microservices.mitra.model.constant;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;

import java.util.Map;

public interface FilterUnknownFields {

    @JsonAnySetter
    default void setUnknownField(String key, Object value) {
        getUnknownFields().put(key, value);
    }

    @JsonIgnore
    @Hidden
    Map<String, Object> getUnknownFields(); // Implementing classes must provide a storage

    default boolean hasUnknownFields() {
        return !getUnknownFields().isEmpty();
    }
}
