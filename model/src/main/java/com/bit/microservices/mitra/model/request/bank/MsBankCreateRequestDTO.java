package com.bit.microservices.mitra.model.request.bank;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.utils.UppercaseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MsBankCreateRequestDTO implements Serializable {
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
}
