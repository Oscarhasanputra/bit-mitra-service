package com.bit.microservices.mitra.command.account.impl.reactive.impl;

import com.bit.microservices.mitra.command.account.impl.reactive.GetMsAccountCommandReactive;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.account.MsAccountViewDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.mitra.redis.MsAccountRedisRepository;
import com.bit.microservices.mitra.repository.QMsAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class GetMsAccountCommandReactiveImpl implements GetMsAccountCommandReactive {
    @Autowired
    private MsAccountRedisRepository msAccountRedisRepository;

    @Autowired
    private QMsAccountRepository qMsAccountRepository;

    @Override
    public Mono<ViewMainResponseDTO<MsAccountViewDTO>> execute(IDRequestDTO request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return Mono.fromCallable(()->{
            MsAccountViewDTO msAccountViewDTO = this.msAccountRedisRepository.load(request.getId());
            if(Objects.isNull(msAccountViewDTO)){
                msAccountViewDTO = this.qMsAccountRepository.getSingleMsAccountDTO(request.getId());
                if(!Objects.isNull(msAccountViewDTO)){
                    this.msAccountRedisRepository.save(msAccountViewDTO.getId(),msAccountViewDTO);
                }
            }

            String responseMessage = ResponseCodeMessageEnum.SUCCESS.getMessage();
            String messageCode =  ResponseCodeMessageEnum.SUCCESS.getCode();
            if(Objects.isNull(msAccountViewDTO)){

                throw new BadRequestException(module,crud, ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST, "");
            }

            String responseCode = HttpStatus.OK.value()+ module.getCode()+crud.getCode()+messageCode;

            return new ViewMainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,new BigDecimal(responseCode),responseMessage, msAccountViewDTO);
        });
    }
}
