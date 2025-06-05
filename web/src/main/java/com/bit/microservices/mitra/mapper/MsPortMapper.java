package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.entity.MsPort;
import com.bit.microservices.mitra.model.request.port.PortCreateRequestDTO;
import com.bit.microservices.mitra.model.request.port.PortUpdateRequestDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsPortMapper extends BaseMapper {
    MsPortMapper INSTANCE = Mappers.getMapper(MsPortMapper.class);

    @Mappings({
            @Mapping(source = "active",target = "isActive")
    })
    MsPort toEntity(PortCreateRequestDTO requestDTO);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(source = "active",target="isActive")
    })
    MsPort updateMsPort(PortUpdateRequestDTO request, @MappingTarget MsPort msModule);
}
