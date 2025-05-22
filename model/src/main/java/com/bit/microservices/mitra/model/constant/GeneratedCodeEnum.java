package com.bit.microservices.mitra.model.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum GeneratedCodeEnum implements EnumAction {

    AUTO("OTOMATIS"),
    MANUAL("MANUAL"),

    AUTO_MANUAL("AUTO / MANUAL");
    public final String status;

    private static HashMap<String, GeneratedCodeEnum> map= new LinkedHashMap();

    static {
        for(GeneratedCodeEnum status: GeneratedCodeEnum.values()){
            map.put(status.status,status);
        }
    }

    GeneratedCodeEnum(String status){
        this.status= status;
    }

    public static GeneratedCodeEnum getValue(String label) throws Exception{
        GeneratedCodeEnum status =  map.get(label);
        if(Objects.isNull(status))
            throw new Exception("Enum Tidak Terdaftar");
        return status;
    }

    @Override
    public Boolean valueIsExist(String value) {
        try {
            getValue(value);
            return true;
        }catch (Exception err){
            return false;
        }
    }
}
