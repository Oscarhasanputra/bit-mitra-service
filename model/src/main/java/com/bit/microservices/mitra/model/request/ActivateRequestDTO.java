package com.bit.microservices.mitra.model.request;

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
public class ActivateRequestDTO implements Serializable, FilterUnknownFields, BaseResponseGetter<BaseResponseDTO> {
    @Serial
    private static final long serialVersionUID = -1243581609139601077L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true,notZero = true)
    private String id;

    @ValidationRequestField(fieldType = FieldType.BOOLEAN, notNull = true, notEmpty = true)
    private Boolean active;

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
