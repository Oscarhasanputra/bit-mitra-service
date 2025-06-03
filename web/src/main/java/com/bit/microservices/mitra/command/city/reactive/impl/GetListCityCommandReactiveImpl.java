package com.bit.microservices.mitra.command.city.reactive.impl;

import com.bit.microservices.mitra.command.city.reactive.GetListCityCommandReactive;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.DatabaseValidationException;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.city.CitySearchField;
import com.bit.microservices.mitra.model.constant.response_code.EnumColumnFilterBy;
import com.bit.microservices.mitra.model.entity.QMsCity;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.city.CityListDTO;
import com.bit.microservices.mitra.utils.FilterByBooleanExpression;
import com.bit.microservices.mitra.repository.QMsCityRepository;
import com.bit.microservices.mitra.utils.PageableUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GetListCityCommandReactiveImpl implements GetListCityCommandReactive {

    @Autowired
    private QMsCityRepository qMsCityRepository;

    @Override
    public Mono<CustomPageImpl<CityListDTO>> execute(SearchRequestDTO request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return Mono.fromCallable(()->{
            Map<String, String> invalidFilterColumn = EnumColumnFilterBy.getInvalidFilterColumnType(CitySearchField.class, request.getFilterBy(), request.getSortBy());

            if (!invalidFilterColumn.isEmpty()) {
                Set<String> invalidColumn = invalidFilterColumn.entrySet().stream()
                        .filter(entry -> entry.getValue().equals("INVALID FILTER COLUMN"))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());


                if (!invalidColumn.isEmpty()) {
                    throw new BadRequestException(module, crud, ResponseCodeMessageEnum.FAILED_INVALID_FILTER_FIELD, String.join(", ", invalidColumn));
                }
                Set<String> invalidValue = invalidFilterColumn.entrySet().stream()
                        .filter(entry -> entry.getValue().equals("INVALID VALUE COLUMN"))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toSet());
                if (!invalidValue.isEmpty()) {
                    throw new BadRequestException(module, crud, ResponseCodeMessageEnum.FAILED_FILTER_MISMATCH, String.join(", ", invalidValue));
                }

            }

            CustomPageImpl<CityListDTO> response;
            QMsCity qMsCity = QMsCity.msCity;


            Pageable pageable = PageableUtils.pageableUtils(request);
            BooleanBuilder querySearch = FilterByBooleanExpression.constructPredicate(request,qMsCity,QMsCity.class,CitySearchField.class);
            System.out.println("running nich 1");
            try {
                Page<CityListDTO> page = this.qMsCityRepository.findAll(querySearch, pageable);
                System.out.println("running nich 2");
                response = new CustomPageImpl<>(page);
            } catch (JDBCConnectionException jdbcConnectionException) {
                throw new DatabaseValidationException(module, crud, ResponseCodeMessageEnum.FAILED_DATABASE_OFFLINE, "Connection Failed!");
            } catch (Exception e) {
                throw new DatabaseValidationException(module, crud, ResponseCodeMessageEnum.FAILED_CUSTOM, e.getMessage());
            }
            return response;
        });
    }
}
