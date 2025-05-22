package com.bit.microservices.mitra.model.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ResponseCodeMessageEnum {
    SUCCESS("200", "00", "Success", false),
    SUCCESS_CUSTOM("200", "01", "Success custom: %s", true),
    FAILED_CONNECTION_TIMEOUT("500", "02", "Failed connection, data disconnected (timeout)", false),
    FAILED_DATABASE_OFFLINE("500", "03", "Failed database offline", false),
    FAILED_DATA_NOT_EXIST("404", "04", "Failed data not found", false),
    FAILED_DATA_ALREADY_EXIST("409", "05", "Failed data already exists", false),
    FAILED_DATA_LENGTH_EXCEEDED("422", "06", "Failed data length exceeds field limit: %s", true),
    FAILED_FIELD_NOT_EXIST("404", "07", "Failed unexpected field detected: %s", true),
    FAILED_FIELD_CANNOT_NULL("422", "08", "Failed field cannot be NULL: %s", true),
    FAILED_FIELD_UNIQUE("409", "09", "Failed field must be UNIQUE: %s", true),
    FAILED_INVALID_DATE("422", "10", "Failed data format is invalid (incorrect date format): %s", true),
    FAILED_INVALID_TYPE("422", "11", "Failed data format is invalid %s", true),
    FAILED_INVALID_EMAIL("422", "12", "Failed data format is invalid (incorrect email format): %s", true),
    FAILED_INVALID_CANNOT_EMPTY("422", "13", "Failed field cannot be empty : %s", true),
    FAILED_DETAIL_NOT_EXIST("404", "14", "Failed detail data not found: [%s]", true),
    FAILED_DETAIL_ALREADY_EXIST("409", "15", "Failed detail data already exists : [%s]", true),
    FAILED_FILE_EXCEEDED("422", "16", "Failed data format is invalid (uploaded file is too large) : %s", true),
    FAILED_INVALID_ENUM("422", "17", "Failed data format is invalid (not registered in Enum) : %s", true),
    FAILED_INVALID_FILTER_FIELD("400", "18", "Failed filtering by field is not allowed: %s", true),
    FAILED_INCORRECT_NUMBERING_FORMAT("400", "19", "Failed incorrect numbering format", false),
    FAILED_MUST_CONTAIN_ALPHANUMERIC("400", "83","Failed must contain alphanumeric : %s", true),
    FAILED_CONCURRENCY_DETECTED("400", "84","Failed concurrency detected for this request", false),
    FAILED_CODE_CONTAIN_WHITESPACE("400", "88","Failed must not contain spaces : %s ", true),
    FAILED_MUST_NOT_FILLED_ALL("400", "89","Failed must not filled ALL : %s ", true),
    FAILED_SOURCE_NOT_FOUND("404", "90", "Failed source data not found : %s", true),
    FAILED_PARENT_NOT_FOUND("400", "91","Failed parent data not found : %s", true),
    FAILED_FILTER_MISMATCH("400", "92","Failed mismatch in filtering field : %s", true),
    FAILED_CODE_EXIST("409", "93", "Failed Code Already Exists : %s record is not yet deleted!", true),
    FAILED_INCONSISTENT_CODE("400", "94", "Failed Inconsistent Code, Code Can Not Be Updated", false ),
    FAILED_CANNOT_ACTIVATE("400", "95", "Failed Can Not Update Field Active With This API", false),
    FAILED_DELETE_DELETED("400", "96", "Failed Can Not Delete Data That Is Deleted Already", false),
    FAILED_FLOW_ID("406", "97", "Failed missing required header : %s", true),
    FAILED_ROLLBACK("400", "98", "Failed due to atomic transaction", false),
    FAILED_CUSTOM("406", "99", "Failed custom : %s", true);

    private final String httpStatus;
    private final String code;
    private final String message;
    private final boolean hasCustomMessage;

    ResponseCodeMessageEnum(String httpStatus, String code, String message, boolean hasCustomMessage) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.hasCustomMessage = hasCustomMessage;
    }

    @JsonValue
    public String getMessage(String addCustomMessage) {
        return hasCustomMessage ? String.format(this.message, addCustomMessage) : this.message;
    }
}
