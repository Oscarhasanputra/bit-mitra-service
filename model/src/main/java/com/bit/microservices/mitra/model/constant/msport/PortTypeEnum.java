package com.bit.microservices.mitra.model.constant.msport;

import com.bit.microservices.mitra.model.constant.EnumAction;
import com.bit.microservices.mitra.model.constant.RoleTypeEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

@Getter
public enum PortTypeEnum implements EnumAction {

    SEAPORT("SEAPORT"),

    AIRPORT("AIRPORT"),
    INLANDPORT("INLANDPORT")

    ;

    public final String fieldType;


    PortTypeEnum(String fieldType){
        this.fieldType = fieldType;
    }


    private static HashMap<String, RoleTypeEnum> map= new LinkedHashMap();

    static {
        for(RoleTypeEnum status: RoleTypeEnum.values()){
            map.put(status.type,status);
        }
    }

    public static RoleTypeEnum getValue(String label) throws Exception{
        RoleTypeEnum status =  map.get(label);
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
