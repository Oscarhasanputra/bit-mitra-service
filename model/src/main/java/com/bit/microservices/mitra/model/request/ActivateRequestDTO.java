package com.bit.microservices.mitra.model.request;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class ActivateRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1243581609139601077L;

    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true,notZero = true)
    private String id;

    @ValidationRequestField(fieldType = FieldType.BOOLEAN, notNull = true, notEmpty = true)
    private Boolean active;

}
