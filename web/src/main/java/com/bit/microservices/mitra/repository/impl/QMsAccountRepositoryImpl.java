package com.bit.microservices.mitra.repository.impl;

import com.bit.microservices.mitra.model.constant.account.MsAccountSearchField;
import com.bit.microservices.mitra.model.entity.QMsAccount;
import com.bit.microservices.mitra.model.entity.QMsBank;
import com.bit.microservices.mitra.model.entity.QMscOwner;
import com.bit.microservices.mitra.model.response.account.MsAccountListDTO;
import com.bit.microservices.mitra.model.response.account.MsAccountViewDTO;
import com.bit.microservices.mitra.model.response.account.MscOwnerViewDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.repository.QMsAccountRepository;
import com.bit.microservices.mitra.utils.FilterByBooleanExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class QMsAccountRepositoryImpl implements QMsAccountRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public MsAccountViewDTO getSingleMsAccountDTO(String id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QMsAccount qMsAccount = QMsAccount.msAccount;

        MsAccountViewDTO msAccountViewDTO =queryFactory.select(
                        Projections.constructor(MsAccountViewDTO.class,
                                qMsAccount.id,
                                qMsAccount.code,
                                qMsAccount.name,
                                qMsAccount.isActive,
                                qMsAccount.remarks,
                                qMsAccount.isDeleted,
                                qMsAccount.deletedReason,
                                qMsAccount.createdBy,
                                qMsAccount.createdDate,
                                qMsAccount.modifiedBy,
                                qMsAccount.modifiedDate
                        ))

                .from(qMsAccount).where(qMsAccount.id.eq(id)).fetchFirst();

        QMscOwner qMscOwner = QMscOwner.mscOwner;

        List<MscOwnerViewDTO> mscOwnerViewDTOList = queryFactory.select(
                        Projections.constructor(MscOwnerViewDTO.class,
                                qMscOwner.id,
                                qMscOwner.name,
                                qMscOwner.identityType,
                                qMscOwner.identityNo,
                                qMscOwner.identityIssuerId,
                                qMscOwner.identityIssuerCode,
                                qMscOwner.mobileNumber
                        ))

                .from(qMscOwner).where(qMscOwner.msAccountId.eq(id)).fetch();
        msAccountViewDTO.setOwners(mscOwnerViewDTOList);
        return msAccountViewDTO;
    }

    @Override
    public Page<MsAccountListDTO> findAll(Predicate predicate, Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMsAccount qMsAccount = QMsAccount.msAccount;
        JPAQuery<MsAccountListDTO> queryResult = query

                .select(Projections.constructor(
                        MsAccountListDTO.class,
                        qMsAccount.id,
                        qMsAccount.code,
                        qMsAccount.name,
                        qMsAccount.isActive,
                        qMsAccount.remarks,
                        qMsAccount.isDeleted,
                        qMsAccount.deletedReason,
                        qMsAccount.createdBy,
                        qMsAccount.createdDate,
                        qMsAccount.modifiedBy,
                        qMsAccount.modifiedDate
                )).from(qMsAccount).where(predicate);

        Long total = query
                .select(qMsAccount.id.count())
                .from(qMsAccount)
                .where(predicate)
                .fetchOne();

        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

        List<MsAccountListDTO> result;
        if (pageable.getSort().isEmpty() || pageable.getSort().isUnsorted()) {
            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .fetch();
        } else {
            // Sort
            List<OrderSpecifier<?>> orderSpecifiers = FilterByBooleanExpression.getOrderSpecifiers(pageable.getSort(),qMsAccount, MsAccountSearchField.class);

            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .fetch();
        }

        return new PageImpl<>(result, pageable, Objects.isNull(total) ? 0L : total);

    }
}
