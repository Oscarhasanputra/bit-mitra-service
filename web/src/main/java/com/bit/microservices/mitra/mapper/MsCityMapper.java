package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.model.request.city.UpdateProvinceCodeRequestDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface MsCityMapper extends BaseMapper {

    MsCityMapper INSTANCE = Mappers.getMapper(MsCityMapper.class);

    MsCity toEntity(CityAPIResponseDTO cityAPIResponseDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "active",target="isActive")
    })
    MsCity updateMsCity(UpdateProvinceCodeRequestDTO request, @MappingTarget MsCity msModule);

}
