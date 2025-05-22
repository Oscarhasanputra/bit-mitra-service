package com.bit.microservices.mitra.command.currency.impl;

import com.bit.microservices.mitra.command.currency.CreateCurrencyCommand;
import com.bit.microservices.mitra.mapper.MsCurrencyMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.entity.MsCurrency;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsCurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CreateCurrencyCommandImpl implements CreateCurrencyCommand {
    @Autowired
    private MsCurrencyRepository msCurrencyRepository;

    @Autowired
    private MsCurrencyMapper msCurrencyMapper;


    @Override
    @Transactional
    public BaseGetResponseDTO execute(List<CurrencyAPIDTO> currencies, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        for (CurrencyAPIDTO currency : currencies) {
                MsCurrency msCurrencyExists = this.msCurrencyRepository.findByCode(currency.getCode()).orElse(null);

                if(!Objects.isNull(msCurrencyExists)){
                    msCurrencyExists.setName(currency.getName());
                    this.msCurrencyRepository.merge(msCurrencyExists);
                }else{
                    MsCurrency msCurrencySaved= this.msCurrencyMapper.toEntity(currency);
                    this.msCurrencyRepository.persist(msCurrencySaved);
                }

        }

        ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.SUCCESS;

        String responseCode = responseCodeMessageEnum.getHttpStatus()+module.getCode()+crud.getCode()+responseCodeMessageEnum.getCode();

        BaseGetResponseDTO responseDTO =  new BaseGetResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,new BigDecimal(responseCode),responseCodeMessageEnum.getMessage(),new HashMap<>());

        return responseDTO;
    }
}
