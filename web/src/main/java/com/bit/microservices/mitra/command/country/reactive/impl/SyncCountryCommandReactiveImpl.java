package com.bit.microservices.mitra.command.country.reactive.impl;

import com.bit.microservices.mitra.command.country.CreateCountryCommand;
import com.bit.microservices.mitra.command.country.reactive.SyncCountryCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.filter.UserContextData;
import com.bit.microservices.mitra.filter.UserData;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.mapper.MsCountryMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.entity.MsCountry;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class SyncCountryCommandReactiveImpl implements SyncCountryCommandReactive {


    @Autowired
    private HttpService httpService;


    @Autowired
    private CommandExecutor commandExecutor;

    @Override
    public Mono<List<BaseResponseDTO>> execute(Void request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        return this.httpService.getListCountry().flatMap(
                countryList ->{
                    return ReactiveSecurityContextHolderData.assignContextData(
                        this.commandExecutor.executeAsReactive(CreateCountryCommand.class,countryList,module,crud,mandatoryHeaderRequestDTO)
                    );
                }
        ).onErrorResume((exception)->{
            if(exception instanceof BadRequestException){
                return Mono.error(exception);
            }else{
                return Mono.error(new BadRequestException(module,crud,ResponseCodeMessageEnum.FAILED_CUSTOM,exception.getMessage()));
            }
        })
                ;
    }
}
