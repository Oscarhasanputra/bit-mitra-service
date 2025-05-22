package com.bit.microservices.mitra.model.request.city;

import com.bit.microservices.mitra.model.annotation.ValidationRequestField;
import com.bit.microservices.mitra.model.constant.FieldType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CountryIDRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8893205344513380243L;

    @Schema(description = "Country Code", example = "ID~uuid", requiredMode = Schema.RequiredMode.REQUIRED)
    @ValidationRequestField(fieldType = FieldType.STRING, notNull = true, notEmpty = true, notBlank = true,mustContainTilde = true)
    private String countryId;
}
