package com.bit.microservices.mitra.model.dto.city;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CityAPIResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 464390383339772475L;

    private String id;

    private String name;
}
