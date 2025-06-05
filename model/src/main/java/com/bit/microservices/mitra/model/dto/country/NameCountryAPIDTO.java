package com.bit.microservices.mitra.model.dto.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameCountryAPIDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -866123653887359140L;

    private String common;
    private String official;
}
