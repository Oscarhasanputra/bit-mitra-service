package com.bit.microservices.mitra.model.pageable;

public interface PageData<T> extends SliceData<T> {


    int getTotalPages();

    long getTotalElements();

}
