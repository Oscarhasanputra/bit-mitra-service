package com.bit.microservices.mitra.command.port.reactive.impl;

import com.bit.microservices.mitra.command.port.reactive.GetPortCommandReactive;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.mitra.redis.MsPortRedisRepository;
import com.bit.microservices.mitra.repository.QMsPortRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Objects;

@Component
@Slf4j
public class GetPortCommandReactiveImpl implements GetPortCommandReactive {
    @Autowired
    private MsPortRedisRepository msPortRedisRepository;

    @Autowired
    private QMsPortRepository qMsPortRepository;


    @Override
    public Mono<ViewMainResponseDTO<MsPortViewDTO>> execute(IDRequestDTO request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
       return Mono.fromCallable(()->{
           MsPortViewDTO msPortViewDTO = this.msPortRedisRepository.load(request.getId());

           if(Objects.isNull(msPortViewDTO)){

               msPortViewDTO = this.qMsPortRepository.getSingleMsPort(request.getId());
               if(!Objects.isNull(msPortViewDTO)){
                   this.msPortRedisRepository.save(msPortViewDTO.getId(),msPortViewDTO);
               }
           }

           String responseMessage = ResponseCodeMessageEnum.SUCCESS.getMessage();
           String messageCode =  ResponseCodeMessageEnum.SUCCESS.getCode();
           if(Objects.isNull(msPortViewDTO)){

               throw new BadRequestException(module,crud, ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST, "");
           }

           String responseCode = HttpStatus.OK.value()+ module.getCode()+CrudCodeEnum.GET_CODE.getCode()+messageCode;


           return new ViewMainResponseDTO<>(ResponseCodeMessageEnum.SUCCESS.getMessage(), Integer.valueOf(ResponseCodeMessageEnum.SUCCESS.getCode()),new BigDecimal(responseCode),responseMessage, msPortViewDTO);

       });
    }
}
