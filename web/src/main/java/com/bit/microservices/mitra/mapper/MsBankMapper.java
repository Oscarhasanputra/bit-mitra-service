package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.entity.MsBank;
import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.model.request.bank.MsBankCreateRequestDTO;
import com.bit.microservices.mitra.model.request.bank.MsBankUpdateRequestDTO;
import com.bit.microservices.mitra.model.request.city.UpdateProvinceCodeRequestDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsBankMapper extends BaseMapper {

    MsBankMapper INSTANCE = Mappers.getMapper(MsBankMapper.class);

    MsBank toEntity(MsBankCreateRequestDTO requestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "active",target="isActive")
    })
    MsBank updateMsCity(MsBankUpdateRequestDTO request, @MappingTarget MsBank msBank);
}
