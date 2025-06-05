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
public class MsBankUpdateRequestDTO extends MsBankCreateRequestDTO implements Serializable, BaseResponseGetter<BaseResponseDTO>, FilterUnknownFields {

    @Serial
    private static final long serialVersionUID = 8214213129148569947L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true,mustContainTilde = true)
    private String id;


    @Override
    public Object getResponseId() {
        return this.id;
    }

    @JsonIgnore
    @Hidden
    private Map<String,Object> map = new HashMap<>();

    @Override
    public Map<String, Object> getUnknownFields() {
        return this.map;
    }
}
