package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.model.entity.MsCurrency;
import com.bit.microservices.mitra.model.entity.MsPort;
import com.bit.microservices.mitra.model.request.city.UpdateProvinceCodeRequestDTO;
import com.bit.microservices.mitra.model.request.port.CreatePortRequestDTO;
import com.bit.microservices.mitra.model.request.port.UpdatePortRequestDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsPortMapper extends BaseMapper {
    MsPortMapper INSTANCE = Mappers.getMapper(MsPortMapper.class);

    @Mappings({
            @Mapping(source = "active",target = "isActive")
    })
    MsPort toEntity(CreatePortRequestDTO requestDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "active",target="isActive")
    })
    MsPort updateMsPort(UpdatePortRequestDTO request, @MappingTarget MsPort msModule);
}
