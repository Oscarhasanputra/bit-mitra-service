package com.bit.microservices.mitra.model.constant;

public interface BaseResponseGetter<T extends BaseResponseSetter<?>> {
    T buildResponseObject();

     Object getResponseId();
}
