package com.bit.microservices.mitra.model.request.city;

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
public class UpdateProvinceCodeRequestDTO implements Serializable, BaseResponseGetter<BaseResponseDTO>, FilterUnknownFields {
    @Serial
    private static final long serialVersionUID = -1805837125875568513L;


    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true,notZero = true)
    private String id;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notZero = true,notContainWhitespace = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String code;


    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    private String name;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String provinceCode;


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
        return this.id;
    }

    @JsonIgnore
    @Hidden
    private Map<String,Object> map = new HashMap<>();
    @Override
    @JsonIgnore
    @Hidden
    public Map<String, Object> getUnknownFields() {
        return this.map;
    }
}
