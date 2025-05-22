package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsCountry;
import com.bit.microservices.mitra.model.entity.MsCurrency;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MsCurrencyRepository extends RevisionRepository<MsCurrency, String, Integer>, BaseJpaRepository<MsCurrency, String>, QuerydslPredicateExecutor<MsCurrency> {
    Optional<MsCurrency> findByCode(String code);

}
