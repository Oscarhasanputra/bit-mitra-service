package com.bit.microservices.mitra.model.request.mscowner;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.FilterUnknownFields;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class MscOwnerUpdateRequestDTO extends MscOwnerCreateRequestDTO implements Serializable, FilterUnknownFields {

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true,notZero = true)
    private String id;

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
