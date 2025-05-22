package com.bit.microservices.mitra.model.request;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class GetSingleRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2677175903924050893L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true,notZero = true)
    private String id;

}

