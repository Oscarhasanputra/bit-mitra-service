package com.bit.microservices.mitra.mapper;

import com.bit.microservices.mitra.model.entity.MsAccount;
import com.bit.microservices.mitra.model.entity.MscOwner;
import com.bit.microservices.mitra.model.request.account.MsAccountCreateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerCreateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerUpdateRequestDTO;
import com.bit.microservices.model.BaseMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MscOwnerMapper extends BaseMapper {

    MscOwnerMapper INSTANCE = Mappers.getMapper(MscOwnerMapper.class);

    @Mappings({
            @Mapping(target="id",ignore = true)
    })
    MscOwner toEntity(MscOwnerCreateRequestDTO requestDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MscOwner updateMscOwner(MscOwnerUpdateRequestDTO request, @MappingTarget MscOwner mscOwner);

}
