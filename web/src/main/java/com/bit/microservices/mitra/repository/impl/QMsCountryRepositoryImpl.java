package com.bit.microservices.mitra.repository.impl;

import com.bit.microservices.mitra.model.constant.country.CountrySearchField;
import com.bit.microservices.mitra.model.entity.QMsCountry;
import com.bit.microservices.mitra.model.response.country.CountryListDTO;
import com.bit.microservices.mitra.redis.FilterByBooleanExpression;
import com.bit.microservices.mitra.repository.QMsCountryRepository;
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
public class QMsCountryRepositoryImpl implements QMsCountryRepository {

    @PersistenceContext
    private EntityManager em;
    @Override
    public Page<CountryListDTO> findAll(Predicate predicate, Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMsCountry qMsCountry= QMsCountry.msCountry;
        JPAQuery<CountryListDTO> queryResult = query

                .select(Projections.constructor(
                        CountryListDTO.class,
                        qMsCountry.id,
                        qMsCountry.name,
                        qMsCountry.code,
                        qMsCountry.isActive,
                        qMsCountry.remarks,
                        qMsCountry.isDeleted,
                        qMsCountry.deletedReason,
                        qMsCountry.createdBy,
                        qMsCountry.createdDate,
                        qMsCountry.modifiedBy,
                        qMsCountry.modifiedDate
                )).from(qMsCountry).where(predicate);

        Long total = query
                .select(qMsCountry.id.count())
                .from(qMsCountry)
                .where(predicate)
                .fetchOne();

        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

        List<CountryListDTO> result;
        if (pageable.getSort().isEmpty() || pageable.getSort().isUnsorted()) {
            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .fetch();
        } else {
            // Sort
            List<OrderSpecifier<?>> orderSpecifiers = FilterByBooleanExpression.getOrderSpecifiers(pageable.getSort(),qMsCountry, CountrySearchField.class);

            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .fetch();
        }

        return new PageImpl<>(result, pageable, Objects.isNull(total) ? 0L : total);
    }
}
