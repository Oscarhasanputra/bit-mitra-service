package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.entity.MsAccount;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MsAccountRepository extends RevisionRepository<MsAccount, String, Integer>, BaseJpaRepository<MsAccount, String>, QuerydslPredicateExecutor<MsAccount> {
    Optional<MsAccount> findByIdAndIsDeleted(String id, Boolean isDeleted);
    Optional<MsAccount> findByCodeAndIsDeleted(String code, Boolean isDeleted);
}
