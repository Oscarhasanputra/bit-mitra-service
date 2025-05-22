package com.bit.microservices.mitra.command.city.impl;

import com.bit.microservices.mitra.command.city.CreateCityCommand;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.mapper.MsCityMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.city.CityByCountryDTO;
import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.repository.MsCityRepository;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class CreateCityCommandImpl implements CreateCityCommand {



    @Autowired
    private MsCityRepository msCityRepository;

    @Autowired
    private MsCityMapper msCityMapper;



    @Override
    @Transactional
    public BaseGetResponseDTO execute(CityByCountryDTO cityByCountryDTO, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        cityByCountryDTO.getCityList().stream().forEach((city)->{
            MsCity cityDto = this.msCityMapper.toEntity(city);
            String cityName = city.getName().replaceAll("\\s+", "");
            String code = String.join("~", cityByCountryDTO.getCountryCode(),cityName);
            cityDto.setCode(code);
            this.msCityRepository.persist(cityDto);
        });
        ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.SUCCESS;

        String responseCode = responseCodeMessageEnum.getHttpStatus()+module.getCode()+crud.getCode()+responseCodeMessageEnum.getCode();

        BaseGetResponseDTO responseDTO =  new BaseGetResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,new BigDecimal(responseCode),responseCodeMessageEnum.getMessage(),new HashMap<>());

        return responseDTO;
    }
}
