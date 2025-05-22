package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.response.city.CityListDTO;
import com.bit.microservices.mitra.model.response.country.CountryListDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMsCityRepository {



    Page<CityListDTO> findAll(Predicate predicate, Pageable pageable);
}
