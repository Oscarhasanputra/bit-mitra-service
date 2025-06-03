package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.response.country.CountryListDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMsPortRepository {

    Page<MsPortViewDTO> findAll(Predicate predicate, Pageable pageable);

    MsPortViewDTO getSingleMsPort(String id);
}
