package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsCountry;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MsCountryRepository extends RevisionRepository<MsCountry, String, Integer>, BaseJpaRepository<MsCountry, String>, QuerydslPredicateExecutor<MsCountry> {
    Optional<MsCountry> findByCodeAndIsDeleted(String code, Boolean isDeleted);

}
