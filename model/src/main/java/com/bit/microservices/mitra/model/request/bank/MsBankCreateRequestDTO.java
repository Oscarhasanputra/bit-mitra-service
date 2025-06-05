package com.bit.microservices.mitra.model.request.bank;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.BaseResponseGetter;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.FilterUnknownFields;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.utils.UppercaseDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class MsBankCreateRequestDTO implements Serializable, BaseResponseGetter<BaseResponseDTO>, FilterUnknownFields {
    @Serial
    private static final long serialVersionUID = -9213354627868051078L;


    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String code;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String name;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String swiftCode;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true,notZero = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String biCode;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String country;

    @ValidationRequestField(fieldType = FieldType.BOOLEAN, notNull = true, notEmpty = true, notBlank = true)
    private Boolean active;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true)
    private String remarks;

    @Override
    public BaseResponseDTO buildResponseObject() {
        return new BaseResponseDTO();
    }

    @Override
    public Object getResponseId() {
        return this.code;
    }

    @JsonIgnore
    @Hidden
    private Map<String,Object> map = new HashMap<>();

    @Override
    public Map<String, Object> getUnknownFields() {
        return this.map;
    }
}
