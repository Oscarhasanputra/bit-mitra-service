package com.bit.microservices.mitra.redis;

import com.bit.microservices.mitra.model.constant.response_code.EnumColumnFilterBy;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilterByBooleanExpression {
    private FilterByBooleanExpression() {

    }

    public static BooleanBuilder constructPredicate(SearchRequestDTO request, Path<?> entityPath, Class classQuery, Class<? extends EnumColumnFilterBy> enumClass) {
        BooleanBuilder querySearch = new BooleanBuilder();
        request.getFilterBy().forEach((k, v) -> {

            EnumColumnFilterBy searchField = EnumColumnFilterBy.fromRequestName(enumClass, k);

            Path<?> entityPathUpdate = entityPath;

            if (!Objects.isNull(searchField.getPath())) {
                entityPathUpdate = searchField.getPath();
            }
            String columnName = searchField.getNameColumn();
            switch (searchField.getType()) {
                case DATE -> {
                    if (!Objects.isNull(v.getRangeDateTime().getStartDateTime()) && !Objects.isNull(v.getRangeDateTime().getEndDateTime())) {
                        querySearch.and(Expressions
                                .dateTimePath(LocalDate.class, entityPathUpdate, columnName)
                                .between(
                                        v.getRangeDate().getStartDate(),
                                        v.getRangeDate().getEndDate()
                                ));
                    } else if (!Objects.isNull(v.getRangeDateTime().getStartDateTime())) {
                        querySearch.and((Expressions
                                .dateTimePath(LocalDate.class, entityPathUpdate, columnName)
                                .goe(v.getRangeDate().getStartDate())));
                    } else {
                        querySearch.and((Expressions
                                .dateTimePath(LocalDate.class, entityPathUpdate, columnName)
                                .loe(v.getRangeDate().getEndDate())));
                    }
                }
                case DATETIME -> {
                    if (!Objects.isNull(v.getRangeDateTime().getStartDateTime()) && !Objects.isNull(v.getRangeDateTime().getEndDateTime())) {
                        querySearch.and((Expressions
                                .dateTimePath(LocalDateTime.class, entityPathUpdate, columnName)
                                .between(
                                        v.getRangeDateTime().getStartDateTime().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime(),
                                        v.getRangeDateTime().getEndDateTime().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
                                )));
                    } else if (!Objects.isNull(v.getRangeDateTime().getStartDateTime())) {
                        querySearch.and(Expressions
                                .dateTimePath(LocalDateTime.class, entityPathUpdate, columnName)
                                .goe(v.getRangeDateTime().getStartDateTime().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()));
                    } else {
                        querySearch.and(Expressions
                                .dateTimePath(LocalDateTime.class, entityPathUpdate, columnName)
                                .loe(v.getRangeDateTime().getEndDateTime().atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()));
                    }
                }
                case NUMBER -> {
                    if (!Objects.isNull(v.getNumber().getMin()) && !Objects.isNull(v.getNumber().getMax())) {
                        querySearch.and(Expressions
                                .numberPath(BigDecimal.class, entityPathUpdate, columnName)
                                .between(
                                        v.getNumber().getMin(),
                                        v.getNumber().getMax()
                                ));
                    } else if (!Objects.isNull(v.getNumber().getMin())) {
                        querySearch.and(Expressions
                                .numberPath(BigDecimal.class, entityPathUpdate, columnName)
                                .goe(v.getNumber().getMin()));
                    } else {
                        querySearch.and(Expressions
                                .numberPath(BigDecimal.class, entityPathUpdate, columnName)
                                .loe(v.getNumber().getMax()));
                    }
                }
                case STRING -> {
                    if (!v.getValues().isEmpty()) {
                        BooleanBuilder stringPredicate = new BooleanBuilder();
                        for (String value : v.getValues()) {
                            stringPredicate.or(Expressions.stringPath(entityPathUpdate, columnName).like("%"+value+"%"));
                        }
                        querySearch.and(stringPredicate);
                    } else {
                        break;
                    }
                }
                case ENUM -> {
                    if (!v.getValues().isEmpty()) {
                        List<Enum<?>> listEnum = new ArrayList<>();

                        @SuppressWarnings("unchecked")
                        Class<? extends Enum<?>> classEnum = (Class<? extends Enum<?>>) searchField.getEnumClass();

                        for (String value : v.getValues()) {
                            try {
                                @SuppressWarnings("unchecked")
                                Enum<?> enumValue = Enum.valueOf((Class<Enum>) classEnum.asSubclass(Enum.class), value);
                                listEnum.add(enumValue);
                            } catch (IllegalArgumentException e) {
                                break;
                            }
                        }

                        if (!listEnum.isEmpty()) {
                            @SuppressWarnings("unchecked")
                            Predicate pre = Expressions.enumPath((Class<Enum>) classEnum.asSubclass(Enum.class), entityPathUpdate, columnName).in(listEnum);
                            querySearch.and(pre);
                        }
                    } else {
                        break;
                    }
                }
                case BOOLEAN -> {
                    if (v.getValue() instanceof Boolean value) {
                        querySearch.and(Expressions.booleanPath(entityPathUpdate, columnName).eq(value));
                    } else {
                        break;
                    }
                }
                case CHILD_PATH_BOOLEAN -> {
                    if (v.getValue() instanceof Boolean value) {
                        if (Boolean.TRUE.equals(value)) {
                            querySearch.and(Expressions.setPath(searchField.getChildPath(), classQuery, entityPathUpdate.getMetadata()).isNotEmpty());
                        } else {
                            querySearch.and(Expressions.setPath(searchField.getChildPath(), classQuery, entityPathUpdate.getMetadata()).isEmpty());
                        }
                    } else {
                        break;
                    }
                }
                default -> {
                    break;
                }
            }
        });
        return querySearch;
    }


    public static List<OrderSpecifier<?>> getOrderSpecifiers(Sort sort, Path<?> entityPath, Class<? extends EnumColumnFilterBy> enumClass) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort) {
            EnumColumnFilterBy searchField = EnumColumnFilterBy.fromRequestName(enumClass, order.getProperty());
            Path<?> entityPathUpdate = entityPath;
            String columnName = searchField.getNameColumn();
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            if (!Objects.isNull(searchField.getPath())) {
                entityPathUpdate = searchField.getPath();
            }
            switch (searchField.getType()) {
                case DATE -> orderSpecifiers.add(new OrderSpecifier<>(direction,
                        Expressions.dateTimePath(LocalDate.class, entityPathUpdate, columnName)));
                case DATETIME -> orderSpecifiers.add(new OrderSpecifier<>(direction,
                        Expressions.dateTimePath(LocalDateTime.class, entityPathUpdate, columnName)));
                case NUMBER -> orderSpecifiers.add(new OrderSpecifier<>(direction,
                        Expressions.numberPath(BigDecimal.class, entityPathUpdate, columnName)));
                case STRING -> orderSpecifiers.add(new OrderSpecifier<>(direction,
                        Expressions.stringPath(entityPathUpdate, columnName)));
                case ENUM -> {
                    @SuppressWarnings("unchecked")
                    Class<? extends Enum<?>> classEnum = (Class<? extends Enum<?>>) searchField.getEnumClass();

                    @SuppressWarnings("unchecked")
                    Path<Enum> path = Expressions.enumPath(classEnum.asSubclass(Enum.class), entityPathUpdate, columnName);

                    orderSpecifiers.add(new OrderSpecifier<>(direction, path));
                }
                case BOOLEAN -> orderSpecifiers.add(new OrderSpecifier<>(direction,
                        Expressions.booleanPath(entityPathUpdate, columnName)));
                default -> {
                    break;
                }
            }
        }
        return orderSpecifiers;
    }
}
