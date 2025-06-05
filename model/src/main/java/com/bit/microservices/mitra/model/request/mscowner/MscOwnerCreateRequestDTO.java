package com.bit.microservices.mitra.model.request.mscowner;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.constant.FilterUnknownFields;
import com.bit.microservices.mitra.model.constant.mscowner.IdentityTypeEnum;
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
public class MscOwnerCreateRequestDTO implements Serializable, FilterUnknownFields {


    @Serial
    private static final long serialVersionUID = 2226919463448929772L;


    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String name;

    @ValidationRequestField(fieldType = FieldType.ENUM, notNull = true, notEmpty = true, notBlank = true,enumClass = IdentityTypeEnum.class)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String identityType;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainNumeric = true)
    private String identityNo;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true,notZero = true)
    private String identityIssuerId;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notZero = true,notEqualALLString = true)
    @JsonDeserialize(using = UppercaseDeserializer.class)
    private String identityIssuerCode;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustValidMobile = true)
    private String mobileNumber;


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
