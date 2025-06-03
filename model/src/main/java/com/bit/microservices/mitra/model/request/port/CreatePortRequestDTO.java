package com.bit.microservices.mitra.model.request.port;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.BaseResponseGetter;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.FilterUnknownFields;
import com.bit.microservices.mitra.model.constant.msport.PortTypeEnum;
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
public class CreatePortRequestDTO implements Serializable, FilterUnknownFields, BaseResponseGetter<BaseResponseDTO> {
    @Serial
    private static final long serialVersionUID = 7895123197025136746L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String code;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String name;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    private String address;

    @ValidationRequestField(fieldType = FieldType.ENUM, notNull = true, notEmpty = true, notBlank = true,enumClass = PortTypeEnum.class)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String type;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String city;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String country;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true)
    private String pic;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true,mustValidMobile = true)
    private String phoneNumber;


    @ValidationRequestField(fieldType = FieldType.BOOLEAN, notNull = true)
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

    @Hidden
    @JsonIgnore
    private Map<String,Object> map = new HashMap<>();


    @Override
    @Hidden
    @JsonIgnore
    public Map<String, Object> getUnknownFields() {
        return this.map;
    }
}
