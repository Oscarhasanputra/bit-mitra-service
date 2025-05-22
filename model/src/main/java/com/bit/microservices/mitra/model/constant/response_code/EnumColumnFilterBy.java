package com.bit.microservices.mitra.model.constant.response_code;


import com.bit.microservices.mitra.model.constant.EnumAction;
import com.bit.microservices.mitra.model.constant.FilterColumnRequestType;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.querydsl.core.types.Path;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;

public interface EnumColumnFilterBy {

    Class<?> getChildPath();

    Path<?> getPath();

    String getNameColumn();

    String getRequestName();

    FilterColumnRequestType getType();

    Class<? extends EnumAction> getEnumClass();

    static <E extends Enum<E> & EnumColumnFilterBy> List<String> getAllRequestNames(Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(EnumColumnFilterBy::getRequestName)
                .toList();
    }

    static EnumColumnFilterBy fromRequestName(Class<? extends EnumColumnFilterBy> enumClass, String requestName) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(field -> field.getRequestName().equals(requestName))
                .findFirst()
                .orElse(null);
    }

    static <E extends Enum<E> & EnumColumnFilterBy> Map<String, String> getInvalidFilterColumnType(
            Class<E> enumClass, Map<String, SearchRequestDTO.FilterByClass> filterBy, Map<String, String> sortBy) {
        Map<String, String> invalidColumn = new HashMap<>();

        if (!sortBy.isEmpty()) {
            invalidColumn = sortBy.entrySet().stream().map(entry -> {
                EnumColumnFilterBy searchField = fromRequestName(enumClass, entry.getKey());

                if (Objects.isNull(searchField)) {
                    return Map.entry(entry.getKey(), "INVALID FILTER COLUMN");
                }

                try {
                    Sort.Direction.valueOf(entry.getValue());
                } catch (Exception e) {
                    return Map.entry(entry.getKey(), "INVALID VALUE COLUMN");
                }

                return null;
            }).filter(Objects::nonNull).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }

        if (!invalidColumn.isEmpty()) {
            return invalidColumn;
        }


        return filterBy.entrySet().stream()
                .map(entry -> {
                    String key = entry.getKey();
                    SearchRequestDTO.FilterByClass value = entry.getValue();

                    if (!getAllRequestNames(enumClass).contains(key)) {
                        return Map.entry(key, "INVALID FILTER COLUMN");
                    }
                    if(value.getIsInvalidFormat()){
                        Map.entry(key, "INVALID VALUE COLUMN");
                    }
                    EnumColumnFilterBy searchField = fromRequestName(enumClass, key);

                    boolean isInvalid = switch (searchField.getType()) {
                        case DATE -> {
                            if (Objects.isNull(value.getRangeDate())) {
                                yield true;
                            } else {
                                if(!Objects.isNull(value.getRangeDate().getStartDate())
                                        && !Objects.isNull(value.getRangeDate().getEndDate())){
                                    yield value.getRangeDate().getStartDate().isAfter(value.getRangeDate().getEndDate());//start date lebih besar dari end date
                                }else{

                                    yield Objects.isNull(value.getRangeDate().getStartDate())
                                            && Objects.isNull(value.getRangeDate().getEndDate());
                                }
                            }
                        }
                        case DATETIME -> {
                            if (Objects.isNull(value.getRangeDateTime())) {
                                yield true;
                            } else {
                                if(!Objects.isNull(value.getRangeDateTime().getStartDateTime())
                                        && !Objects.isNull(value.getRangeDateTime().getEndDateTime())){
                                    yield value.getRangeDateTime().getStartDateTime().isAfter(value.getRangeDateTime().getEndDateTime()); //start date lebih besar dari enddate
                                }else{
                                    yield Objects.isNull(value.getRangeDateTime().getStartDateTime())
                                            && Objects.isNull(value.getRangeDateTime().getEndDateTime());
                                }
                            }
                        }
                        case NUMBER -> {
                            if (Objects.isNull(value.getNumber())) {
                                yield true;
                            } else {
                                if(!Objects.isNull(value.getNumber().getMax()) && !Objects.isNull(value.getNumber().getMin())){
                                    yield value.getNumber().getMax().compareTo(value.getNumber().getMin())<0; //max tidak boleh lebih kecil dari min;
                                }else{
                                    yield Objects.isNull(value.getNumber().getMax()) && Objects.isNull(value.getNumber().getMin());
                                }
                            }
                        }
                        case ENUM -> {
                            if (Objects.isNull(value.getValues())) {
                                yield true;
                            } else {
                                if (!value.getValues().isEmpty()) {
                                    yield value.getValues().stream()
                                            .anyMatch(valueEnum ->
                                                    !EnumAction.isValidValue(searchField.getEnumClass(), valueEnum));
                                } else {
                                    yield false;
                                }
                            }
                        }
                        case STRING -> Objects.isNull(value.getValues());
                        case BOOLEAN, CHILD_PATH_BOOLEAN ->
                                Objects.isNull(value.getValue()) || !(value.getValue() instanceof Boolean);
                        default -> true; // Unknown type
                    };

                    return isInvalid ? Map.entry(key, "INVALID VALUE COLUMN") : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}