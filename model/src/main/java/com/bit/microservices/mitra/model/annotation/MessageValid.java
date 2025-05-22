package com.bit.microservices.mitra.model.annotation;

import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.FIELD, ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageValid {

    String httpStatus();
    ResponseCodeMessageEnum message();
}
