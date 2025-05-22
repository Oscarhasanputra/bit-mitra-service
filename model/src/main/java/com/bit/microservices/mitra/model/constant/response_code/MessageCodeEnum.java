package com.bit.microservices.mitra.model.constant.response_code;

public enum MessageCodeEnum {

    SUCCESS("00","Success"),

    NOT_EXIST("10","Data Not Exist"),
    ALREADY_EXIST("11","Data Already Exist"),

    CONFLICT("12","Conflict"),
    INVALID_REQUEST("13","Conflict"),

    UNKNOWN("98","Unknown Error"),
    CODE_NOT_EXIST("99","Code Not Exist on handler")
        ;
    public final String code;
    public final String label;

    MessageCodeEnum(String code, String label){
        this.label =label;
        this.code=code;
    }
}
