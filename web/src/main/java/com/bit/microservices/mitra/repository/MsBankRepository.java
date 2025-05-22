package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsBank;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MsBankRepository extends RevisionRepository<MsBank, String, Integer>, BaseJpaRepository<MsBank, String>, QuerydslPredicateExecutor<MsBank> {
    Optional<MsBank> findByCodeAndIsDeleted(String code, Boolean isDeleted);


    Optional<MsBank> findByIdAndIsDeleted(String id, Boolean isDeleted);


}
