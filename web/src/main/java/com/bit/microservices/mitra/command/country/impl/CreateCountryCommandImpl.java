package com.bit.microservices.mitra.command.country.impl;

import com.bit.microservices.mitra.command.country.CreateCountryCommand;
import com.bit.microservices.mitra.mapper.MsCountryMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.mitra.model.entity.MsCountry;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CreateCountryCommandImpl implements CreateCountryCommand {


    @Autowired
    private MsCountryRepository msCountryRepository;

    @Autowired
    private MsCountryMapper msCountryMapper;

    @Override
    public BaseGetResponseDTO execute(List<CountryAPIResponseDTO> countryList, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        countryList.stream().forEach((country)->{
            MsCountry msCountry = this.msCountryRepository.findByCodeAndIsDeleted(country.getCode(),false).orElse(null);
            if(!Objects.isNull(msCountry)){
                MsCountry dataUpdate = this.msCountryMapper.updateEntityFromAPIDTO(country,msCountry);
                this.msCountryRepository.merge(dataUpdate);
            }else{
                MsCountry dataSaved = this.msCountryMapper.APIDtoToEntity(country);
                this.msCountryRepository.persist(dataSaved);
            }

            log.info("Country : {}",country);
        });
        ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.SUCCESS;

        String responseCode = responseCodeMessageEnum.getHttpStatus()+module.getCode()+crud.getCode()+responseCodeMessageEnum.getCode();

        BaseGetResponseDTO responseDTO =  new BaseGetResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,new BigDecimal(responseCode),responseCodeMessageEnum.getMessage(),new HashMap<>());

        return responseDTO;
    }
}
