package com.bit.microservices.mitra.repository.impl;

import com.bit.microservices.mitra.model.constant.msport.MsPortSearchField;
import com.bit.microservices.mitra.model.entity.QMsPort;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.repository.QMsPortRepository;
import com.bit.microservices.mitra.utils.FilterByBooleanExpression;
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
public class QMsPortRepositoryImpl implements QMsPortRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Page<MsPortViewDTO> findAll(Predicate predicate, Pageable pageable) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QMsPort qMsPort = QMsPort.msPort;

        // Main Query
        JPAQuery<MsPortViewDTO> queryResult = query
                .select(
                        Projections.constructor(MsPortViewDTO.class,
                                qMsPort.id,
                                qMsPort.code,
                                qMsPort.name,
                                qMsPort.address,
                                qMsPort.type,
                                qMsPort.cityId,
                                qMsPort.cityCode,
                                qMsPort.countryId,
                                qMsPort.countryCode,
                                qMsPort.pic,
                                qMsPort.phoneNumber,
                                qMsPort.isActive,
                                qMsPort.remarks,
                                qMsPort.isDeleted,
                                qMsPort.deletedReason,
                                qMsPort.createdBy,
                                qMsPort.createdDate,
                                qMsPort.modifiedBy,
                                qMsPort.modifiedDate
                        )
                        )
                .from(qMsPort)
                .where(predicate);

        Long total = query
                .select(qMsPort.id.count())
                .from(qMsPort)
                .where(predicate)
                .fetchOne();

        long offset = pageable.getOffset();
        long pageSize = pageable.getPageSize();

        List<MsPortViewDTO> result;
        if (pageable.getSort().isEmpty() || pageable.getSort().isUnsorted()) {
            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .fetch();
        } else {
            // Sort
            List<OrderSpecifier<?>> orderSpecifiers = FilterByBooleanExpression.getOrderSpecifiers(pageable.getSort(), qMsPort, MsPortSearchField.class);

            result = queryResult
                    .offset(offset)
                    .limit(pageSize)
                    .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                    .fetch();
        }

        return new PageImpl<>(result, pageable, Objects.isNull(total) ? 0L : total);
    }

    @Override
    public MsPortViewDTO getSingleMsPort(String id) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMsPort qMsPort = QMsPort.msPort;
        MsPortViewDTO msPortViewDTO=queryFactory.select(
                        Projections.constructor(MsPortViewDTO.class,
                                qMsPort.id,
                                qMsPort.code,
                                qMsPort.name,
                                qMsPort.address,
                                qMsPort.type,
                                qMsPort.cityId,
                                qMsPort.cityCode,
                                qMsPort.countryId,
                                qMsPort.countryCode,
                                qMsPort.pic,
                                qMsPort.phoneNumber,
                                qMsPort.isActive,
                                qMsPort.remarks,
                                qMsPort.isDeleted,
                                qMsPort.deletedReason,
                                qMsPort.createdBy,
                                qMsPort.createdDate,
                                qMsPort.modifiedBy,
                                qMsPort.modifiedDate
                        ))

                .from(qMsPort).where(qMsPort.id.eq(id)).fetchFirst();
        return msPortViewDTO;
    }
}
