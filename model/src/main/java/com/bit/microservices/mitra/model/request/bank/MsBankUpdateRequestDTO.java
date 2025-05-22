package com.bit.microservices.mitra.model.request.bank;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import com.bit.microservices.mitra.model.utils.UppercaseDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class MsBankUpdateRequestDTO extends MsBankCreateRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8214213129148569947L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,notContainWhitespace = true,notZero = true,mustContainTilde = true)
    private String id;
}
