package com.bit.microservices.mitra.model.utils;

import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;

import java.math.BigDecimal;

public class ConstructResponseCode {
    public static BigDecimal constructResponseCode(ModuleCodeEnum module, CrudCodeEnum crud, ResponseCodeMessageEnum responseCodeMessageEnum) {
        return new BigDecimal(
                responseCodeMessageEnum.getHttpStatus() +
                        module.getCode() +
                        crud.getCode() +
                        responseCodeMessageEnum.getCode()
        );
    }

    private ConstructResponseCode() {}
}