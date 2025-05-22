package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.entity.MsCountry;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsCountryMapper extends BaseMapper {
    MsCountryMapper INSTANCE = Mappers.getMapper(MsCountryMapper.class);

    @Mappings({
            @Mapping(target="name",source = "name.common"),
            @Mapping(target="code",source = "code")
    })
    MsCountry APIDtoToEntity(CountryAPIResponseDTO countryAPIResponseDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "name",source = "name.common"),
            @Mapping(target= "code",source = "code")
    })
    MsCountry updateEntityFromAPIDTO(CountryAPIResponseDTO request, @MappingTarget MsCountry msCountry);
}
