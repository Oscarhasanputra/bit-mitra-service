package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsPort;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MsPortRepository extends RevisionRepository<MsPort, String, Integer>, BaseJpaRepository<MsPort, String>, QuerydslPredicateExecutor<MsPort> {
    Optional<MsPort> findByCodeAndIsDeleted(String code, Boolean isDeleted);

    Optional<MsPort> findByIdAndIsDeleted(String id, Boolean isDeleted);
}
