package com.bit.microservices.mitra.http;

import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIResponseDTO;
import reactor.core.publisher.Mono;

import java.util.List;

public interface HttpService {

    public Mono<List<CountryAPIResponseDTO>> getListCountry();

    public Mono<List<CityAPIResponseDTO>> getListCityByCountryCode(String countryCode);

    public Mono<CurrencyAPIResponseDTO> getCurrencyList();
}
