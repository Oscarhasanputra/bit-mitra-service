package com.bit.microservices.mitra.model.constant.currency;

import com.bit.microservices.mitra.model.constant.EnumAction;
import com.bit.microservices.mitra.model.constant.FilterColumnRequestType;
import com.bit.microservices.mitra.model.constant.response_code.EnumColumnFilterBy;
import com.querydsl.core.types.Path;
import lombok.Getter;

import java.io.Serializable;

@Getter
public enum CurrencySearchField implements EnumColumnFilterBy {

    CODE(null, null, "code", "code", FilterColumnRequestType.STRING, null),
    NAME(null, null, "name", "name", FilterColumnRequestType.STRING, null),
    ;

    private final Class<?> childPath;

    private final Path<?> path;

    private final String nameColumn;

    private final String requestName;

    private final FilterColumnRequestType type;

    private final Class<? extends EnumAction> enumClass;

    CurrencySearchField(Class<? extends Serializable> childPath, Path<?> path, String nameColumn, String requestName, FilterColumnRequestType type, Class<? extends EnumAction> enumClass) {
        this.childPath = childPath;
        this.path = path;
        this.nameColumn = nameColumn;
        this.requestName = requestName;
        this.type = type;
        this.enumClass = enumClass;
    }
}
