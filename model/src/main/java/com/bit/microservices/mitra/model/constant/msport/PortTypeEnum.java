package com.bit.microservices.mitra.model.constant.msport;

import lombok.Getter;

@Getter
public enum PortTypeEnum {

    SEAPORT("SEAPORT"),

    AIRPORT("AIRPORT"),
    INLANDPORT("INLANDPORT")

    ;

    public final String fieldType;


    PortTypeEnum(String fieldType){
        this.fieldType = fieldType;
    }
}
