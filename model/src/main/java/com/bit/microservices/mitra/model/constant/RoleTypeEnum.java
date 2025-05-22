package com.bit.microservices.mitra.model.constant;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public enum RoleTypeEnum implements EnumAction {
    INTERNAL("INTERNAL"),
    EXTERNAL("EXTERNAL");
    public final String type;

    private static HashMap<String, RoleTypeEnum> map= new LinkedHashMap();

    static {
        for(RoleTypeEnum status: RoleTypeEnum.values()){
            map.put(status.type,status);
        }
    }

    RoleTypeEnum(String type){
        this.type= type;
    }

    public static RoleTypeEnum getValue(String label) throws Exception{
        RoleTypeEnum status =  map.get(label);
        System.out.println("get value");
        System.out.println(map);
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
