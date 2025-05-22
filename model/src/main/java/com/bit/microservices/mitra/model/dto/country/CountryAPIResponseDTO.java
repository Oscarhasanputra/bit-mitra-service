package com.bit.microservices.mitra.model.dto.country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CountryAPIResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1875781477875268500L;


    @JsonProperty("cca2")
    private String code;

    private NameCountryAPIDTO name;




}
