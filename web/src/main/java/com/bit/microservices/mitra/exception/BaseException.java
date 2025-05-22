package com.bit.microservices.mitra.exception;

import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseException extends RuntimeException implements Serializable {

    protected final ResponseCodeMessageEnum responseCodeMessageEnum;

    protected final ModuleCodeEnum moduleCodeEnum;

    protected final CrudCodeEnum crudCodeEnum;
    protected final String message;


    public BaseException(ModuleCodeEnum moduleCodeEnum,CrudCodeEnum crudCodeEnum,ResponseCodeMessageEnum responseCodeMessageEnum, String message){
        super(message);
        this.responseCodeMessageEnum = responseCodeMessageEnum;
        this.moduleCodeEnum=moduleCodeEnum;
        this.crudCodeEnum = crudCodeEnum;
        this.message = message;
    }
}
