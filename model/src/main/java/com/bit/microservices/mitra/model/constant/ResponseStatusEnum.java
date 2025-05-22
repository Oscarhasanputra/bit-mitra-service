package com.bit.microservices.mitra.model.constant;

public enum ResponseStatusEnum {
    SUCCESS("20100101001","Success",0),
    PARTIAL_SUCCESS("20100101001","Success",1),

    FAILED("40600101001","Failed",2);


    public final String responseCode;

    public final String responseMessage;

    public final Integer code;

    ResponseStatusEnum(String responseCode, String responseMessage, Integer code){
        this.responseMessage=responseMessage;
        this.responseCode=responseCode;
        this.code = code;
    }
}
