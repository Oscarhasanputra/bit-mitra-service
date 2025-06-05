package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.entity.MsAccount;
import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.model.entity.MscOwner;
import com.bit.microservices.mitra.model.request.account.MsAccountCreateRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerUpdateRequestDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MsAccountMapper extends BaseMapper {

    MsAccountMapper INSTANCE = Mappers.getMapper(MsAccountMapper.class);

    @Mappings({
            @Mapping(target="id",ignore = true)
    })
    MsAccount toEntity(MsAccountCreateRequestDTO msAccountCreateRequestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MsAccount updateMsAccount(MsAccountUpdateRequestDTO request, @MappingTarget MsAccount msAccount);

}
