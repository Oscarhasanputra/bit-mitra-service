package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.response.account.MsAccountListDTO;
import com.bit.microservices.mitra.model.response.account.MsAccountViewDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QMsAccountRepository {

    MsAccountViewDTO getSingleMsAccountDTO(String id);

    Page<MsAccountListDTO> findAll(Predicate predicate, Pageable pageable);
}
