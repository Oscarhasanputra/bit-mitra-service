package com.bit.microservices.mitra.model.constant.mscowner;

import com.bit.microservices.mitra.model.constant.EnumAction;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum IdentityTypeEnum implements EnumAction {

    KTP("KTP"),

    SIM("SIM"),
    PASSPORT("PASSPORT")

    ;

    public final String label;


    IdentityTypeEnum(String label){
        this.label = label;
    }


    private static HashMap<String, IdentityTypeEnum> map= new LinkedHashMap();

    static {
        for(IdentityTypeEnum status: IdentityTypeEnum.values()){
            map.put(status.label,status);
        }
    }

    public static IdentityTypeEnum getValue(String label) throws Exception{
        IdentityTypeEnum status =  map.get(label);
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
