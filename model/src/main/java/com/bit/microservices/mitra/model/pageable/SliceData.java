package com.bit.microservices.mitra.model.pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.function.Function;


public interface SliceData<T> extends Streamable<T> {
    int getNumber();

    int getSize();

    int getNumberOfElements();

    @JsonIgnore
    @Override
    default boolean isEmpty() {
        return Streamable.super.isEmpty();
    }
}
