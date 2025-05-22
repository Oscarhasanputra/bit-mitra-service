package com.bit.microservices.mitra.model.dto.city;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CityByCountryDTO implements Serializable {

    private List<CityAPIResponseDTO> cityList;

    private String countryCode;
}
