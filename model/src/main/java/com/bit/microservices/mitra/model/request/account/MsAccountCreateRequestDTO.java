package com.bit.microservices.mitra.model.request.account;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.BaseResponseGetter;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.FilterUnknownFields;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerCreateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.utils.UppercaseDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class MsAccountCreateRequestDTO implements Serializable, BaseResponseGetter<BaseResponseDTO>, FilterUnknownFields {

    @Serial
    private static final long serialVersionUID = -1956766001371244676L;


    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String code;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String name;

    @ValidationRequestField(fieldType = FieldType.BOOLEAN, notNull = true, notEmpty = true)
    private Boolean active;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true)
    private String remarks;

    @Valid
    @ValidationRequestField(fieldType = FieldType.LIST,notNull = true)
    List<MscOwnerCreateRequestDTO> create;
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
    @JsonIgnore
    @Hidden
    @Override
    public Map<String, Object> getUnknownFields() {
        return this.map;
    }
}
