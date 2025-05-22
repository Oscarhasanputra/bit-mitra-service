package com.bit.microservices.mitra.model.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ModuleCodeEnum {
    COUNTRY("008001","country"),
    CITY("008002","city"),
    CURRENCY("008003","currency"),

    BANK("008004","bank")

    ;
    private final String code;
    private final String path;

    ModuleCodeEnum(String code, String path) {
        this.code = code;
        this.path = path;
    }

    public static ModuleCodeEnum getModuleFromPath(String apiPath) {
        return Arrays.stream(values())
                .filter(module -> apiPath.matches(".*/" + module.path.toLowerCase() + "(/.*)?$"))
                .findFirst().orElse(null);
    }
}
