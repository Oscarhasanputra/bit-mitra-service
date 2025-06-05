package com.bit.microservices.mitra.model.constant;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CrudCodeEnum {
    UPSERT_CODE("00", "upsert", ApiGroupType.ATOMIC),
    CREATE_CODE("01", "create", ApiGroupType.ATOMIC),
    UPDATE_CODE("02", "update", ApiGroupType.ATOMIC),
    DELETE_CODE("03", "delete", ApiGroupType.ATOMIC),
    GET_CODE("04", "get", ApiGroupType.NON_ATOMIC),
    GETLIST_CODE("05", "get-list", ApiGroupType.NON_ATOMIC_PAGE),
    ACTIVATE_CODE("06", "activate", ApiGroupType.ATOMIC),
    SYNC_CODE("14","_sync-from-google",ApiGroupType.NON_ATOMIC_PAGE),
    UPDATE_PROVINCE_CODE("20","_update_province_code",ApiGroupType.ATOMIC);

    private final String code;
    private final String path;
    private final ApiGroupType group;

    CrudCodeEnum(String code, String path, ApiGroupType group) {
        this.code = code;
        this.path = path;
        this.group = group;
    }

    public static CrudCodeEnum getCrudCodeEnumFromPath(String apiPath){
        return Arrays.stream(values())
                .filter(apiType -> apiPath.matches(".*/" + apiType.path.toLowerCase() + "(/.*)?$"))
                .findFirst().orElse(null);
    }

    public enum ApiGroupType {
        ATOMIC, NON_ATOMIC, NON_ATOMIC_PAGE
    }
}
