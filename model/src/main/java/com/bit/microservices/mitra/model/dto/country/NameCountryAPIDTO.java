package com.bit.microservices.mitra.model.dto.country;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class NameCountryAPIDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -866123653887359140L;

    private String common;
    private String official;
}
