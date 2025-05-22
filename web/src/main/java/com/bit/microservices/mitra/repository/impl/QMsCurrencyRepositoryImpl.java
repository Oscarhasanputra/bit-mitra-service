package com.bit.microservices.mitra.repository.impl;

import com.bit.microservices.mitra.model.constant.country.CountrySearchField;
import com.bit.microservices.mitra.model.entity.QMsCountry;
import com.bit.microservices.mitra.model.entity.QMsCurrency;
import com.bit.microservices.mitra.model.response.country.CountryListDTO;
import com.bit.microservices.mitra.model.response.currency.CurrencyListDTO;
import com.bit.microservices.mitra.redis.FilterByBooleanExpression;
import com.bit.microservices.mitra.repository.QMsCurrencyRepository;
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
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class QMsCurrencyRepositoryImpl implements QMsCurrencyRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Page<CurrencyListDTO> findAll(Predicate predicate, Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMsCurrency qMsCurrency  = QMsCurrency.msCurrency;
        JPAQuery<CurrencyListDTO> queryResult = query

                .select(Projections.constructor(
                        CurrencyListDTO.class,
                        qMsCurrency.id,
                        qMsCurrency.code,
                        qMsCurrency.name,
                        qMsCurrency.isActive,
                        qMsCurrency.remarks,
                        qMsCurrency.isDeleted,
                        qMsCurrency.deletedReason,
                        qMsCurrency.createdDate,
                        qMsCurrency.createdBy,
                        qMsCurrency.modifiedDate,
                        qMsCurrency.modifiedBy
                )).from(qMsCurrency).where(predicate);

        Long total = query
                .select(qMsCurrency.id.count())
                .from(qMsCurrency)
                .where(predicate)
                .fetchOne();

        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

        List<CurrencyListDTO> result;
        if (pageable.getSort().isEmpty() || pageable.getSort().isUnsorted()) {
            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .fetch();
        } else {
            // Sort
            List<OrderSpecifier<?>> orderSpecifiers = FilterByBooleanExpression.getOrderSpecifiers(pageable.getSort(),qMsCurrency, CountrySearchField.class);

            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .fetch();
        }

        return new PageImpl<>(result, pageable, Objects.isNull(total) ? 0L : total);
    }
}
