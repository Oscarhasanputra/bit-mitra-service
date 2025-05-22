package com.bit.microservices.mitra.model.request.city;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.utils.UppercaseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UpdateProvinceCodeRequestDTO implements Serializable {
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


    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    private String provinceName;

    @ValidationRequestField(fieldType = FieldType.BOOLEAN, notNull = true, notEmpty = true, notBlank = true)
    private Boolean active;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true)
    private String remarks;
}
