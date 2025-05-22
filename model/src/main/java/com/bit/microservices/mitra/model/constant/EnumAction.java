package com.bit.microservices.mitra.model.constant;

public interface EnumAction {
    Boolean valueIsExist(String value);
    static <E extends Enum<E> & EnumAction> Boolean isValidValue(Class<? extends EnumAction> enumC, String value) {

        return enumC.getEnumConstants()[0].valueIsExist(value);

    }
}