package com.bit.microservices.mitra.repository.impl;

import com.bit.microservices.mitra.model.constant.country.CountrySearchField;
import com.bit.microservices.mitra.model.entity.QMsCity;
import com.bit.microservices.mitra.model.entity.QMsCountry;
import com.bit.microservices.mitra.model.response.city.CityListDTO;
import com.bit.microservices.mitra.model.response.country.CountryListDTO;
import com.bit.microservices.mitra.redis.FilterByBooleanExpression;
import com.bit.microservices.mitra.repository.QMsCityRepository;
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
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class QMsCityRepositoryImpl implements QMsCityRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<CityListDTO> findAll(Predicate predicate, Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMsCity qMsCity = QMsCity.msCity;
        JPAQuery<CityListDTO> queryResult = query

                .select(Projections.constructor(
                        CityListDTO.class,
                        qMsCity.id,
                        qMsCity.code,
                        qMsCity.name,
                        qMsCity.provinceCode,
                        qMsCity.provinceName,
                        qMsCity.isActive,
                        qMsCity.remarks,
                        qMsCity.isDeleted,
                        qMsCity.deletedReason,
                        qMsCity.createdDate,
                        qMsCity.createdBy,
                        qMsCity.modifiedDate,
                        qMsCity.modifiedBy
                )).from(qMsCity).where(predicate);

        Long total = query
                .select(qMsCity.id.count())
                .from(qMsCity)
                .where(predicate)
                .fetchOne();

        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

        List<CityListDTO> result;
        if (pageable.getSort().isEmpty() || pageable.getSort().isUnsorted()) {
            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .fetch();
        } else {
            // Sort
            List<OrderSpecifier<?>> orderSpecifiers = FilterByBooleanExpression.getOrderSpecifiers(pageable.getSort(),qMsCity, CountrySearchField.class);

            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .fetch();
        }

        return new PageImpl<>(result, pageable, Objects.isNull(total) ? 0L : total);

    }
}
