package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.entity.MsCurrency;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsCurrencyMapper extends BaseMapper {

    MsCurrencyMapper INSTANCE = Mappers.getMapper(MsCurrencyMapper.class);

    MsCurrency toEntity(CurrencyAPIDTO currencyAPIDTO);
}
