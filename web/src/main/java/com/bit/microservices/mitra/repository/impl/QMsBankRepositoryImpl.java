package com.bit.microservices.mitra.repository.impl;

import com.bit.microservices.mitra.model.constant.country.CountrySearchField;
import com.bit.microservices.mitra.model.entity.QMsBank;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.redis.FilterByBooleanExpression;
import com.bit.microservices.mitra.repository.QMsBankRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@Slf4j
public class QMsBankRepositoryImpl implements QMsBankRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public MsBankViewDTO getSingleMsBankDTO(String id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QMsBank qMsBank = QMsBank.msBank;

        MsBankViewDTO msBankViewDTO  =queryFactory.select(
                        Projections.constructor(MsBankViewDTO.class,
                                qMsBank.id,
                                qMsBank.code,
                                qMsBank.name,
                                qMsBank.swiftCode,
                                qMsBank.biCode,
                                qMsBank.country,
                                qMsBank.isActive,
                                qMsBank.remarks,
                                qMsBank.isDeleted,
                                qMsBank.deletedReason,
                                qMsBank.createdBy,
                                qMsBank.createdDate,
                                qMsBank.modifiedBy,
                                qMsBank.modifiedDate
                        ))

                .from(qMsBank).where(qMsBank.id.eq(id)).fetchFirst();
        return msBankViewDTO;
    }

    @Override
    public Page<MsBankViewDTO> findAll(Predicate predicate, Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMsBank qMsBank = QMsBank.msBank;
        JPAQuery<MsBankViewDTO> queryResult = query

                .select(Projections.constructor(
                        MsBankViewDTO.class,
                        qMsBank.id,
                        qMsBank.code,
                        qMsBank.name,
                        qMsBank.swiftCode,
                        qMsBank.biCode,
                        qMsBank.country,
                        qMsBank.isActive,
                        qMsBank.remarks,
                        qMsBank.isDeleted,
                        qMsBank.deletedReason,
                        qMsBank.createdBy,
                        qMsBank.createdDate,
                        qMsBank.modifiedBy,
                        qMsBank.modifiedDate
                )).from(qMsBank).where(predicate);

        Long total = query
                .select(qMsBank.id.count())
                .from(qMsBank)
                .where(predicate)
                .fetchOne();

        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

        List<MsBankViewDTO> result;
        if (pageable.getSort().isEmpty() || pageable.getSort().isUnsorted()) {
            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .fetch();
        } else {
            // Sort
            List<OrderSpecifier<?>> orderSpecifiers = FilterByBooleanExpression.getOrderSpecifiers(pageable.getSort(),qMsBank, CountrySearchField.class);

            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .fetch();
        }

        return new PageImpl<>(result, pageable, Objects.isNull(total) ? 0L : total);

    }
}
