package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsBank;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.city.CityListDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMsBankRepository {

    MsBankViewDTO getSingleMsBankDTO(String id);

    Page<MsBankViewDTO> findAll(Predicate predicate, Pageable pageable);
}
