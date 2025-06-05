package com.bit.microservices.mitra.repository;

import com.bit.microservices.mitra.model.constant.mscowner.IdentityTypeEnum;
import com.bit.microservices.mitra.model.entity.MscOwner;
import io.hypersistence.utils.spring.repository.BaseJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MscOwnerRepository extends RevisionRepository<MscOwner, String, Integer>, BaseJpaRepository<MscOwner, String>, QuerydslPredicateExecutor<MscOwner> {
    Optional<MscOwner> findByIdentityNoAndMsAccountId(String identityNo, String msAccountId);

    Optional<MscOwner> findByIdentityNoAndIdentityTypeAndIdentityIssuerIdAndMsAccountId(String identityNo, IdentityTypeEnum identityType, String identityIssuerId, String msAccountId);


}
