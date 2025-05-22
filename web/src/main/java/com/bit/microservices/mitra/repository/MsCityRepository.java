package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.model.entity.MsCountry;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MsCityRepository extends RevisionRepository<MsCity, String, Integer>, BaseJpaRepository<MsCity, String>, QuerydslPredicateExecutor<MsCity> {
}
