package com.bit.microservices.mitra.model.request.port;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.BaseResponseGetter;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.FilterUnknownFields;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class PortUpdateRequestDTO extends PortCreateRequestDTO implements Serializable, FilterUnknownFields, BaseResponseGetter<BaseResponseDTO> {


    @Serial
    private static final long serialVersionUID = 9177457925047561947L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true,mustContainTilde = true)
    private String id;

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
    @Hidden
    public Map<String, Object> getUnknownFields() {
        return this.map;

    }
}
