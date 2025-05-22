package com.bit.microservices.mitra.command.bank.reactive.impl;

import com.bit.microservices.mitra.command.bank.reactive.GetMsBankCommandReactive;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.request.GetSingleRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.MainResponseDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.mitra.redis.MsBankRedisRepository;
import com.bit.microservices.mitra.repository.QMsBankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@Slf4j
public class GetMsBankCommandReactiveImpl implements GetMsBankCommandReactive {

    @Autowired
    private MsBankRedisRepository msBankRedisRepository;

    @Autowired
    private QMsBankRepository qMsBankRepository;


    @Override
    public Mono<ViewMainResponseDTO<MsBankViewDTO>> execute(GetSingleRequestDTO request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return Mono.fromCallable(()->{
            MsBankViewDTO msBankViewDTO = this.msBankRedisRepository.load(request.getId());
            if(Objects.isNull(msBankViewDTO)){
                msBankViewDTO = this.qMsBankRepository.getSingleMsBankDTO(request.getId());
                if(!Objects.isNull(msBankViewDTO)){
                    this.msBankRedisRepository.save(msBankViewDTO.getId(),msBankViewDTO);
                }
            }

            String responseMessage = ResponseCodeMessageEnum.SUCCESS.getMessage();
            String messageCode =  ResponseCodeMessageEnum.SUCCESS.getCode();
            if(Objects.isNull(msBankViewDTO)){

                throw new BadRequestException(module,crud, ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST, "");
            }

            String responseCode = HttpStatus.OK.value()+ ModuleCodeEnum.BANK.getCode()+CrudCodeEnum.GET_CODE.getCode()+messageCode;

            return new ViewMainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,new BigDecimal(responseCode),responseMessage, msBankViewDTO);
        });
    }
}
