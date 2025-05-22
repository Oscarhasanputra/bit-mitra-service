package com.bit.microservices.mitra.command.currency.reactive.impl;

import com.bit.microservices.mitra.command.currency.CreateCurrencyCommand;
import com.bit.microservices.mitra.command.currency.reactive.SyncCurrencyCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class SyncCurrencyCommandReactiveImpl implements SyncCurrencyCommandReactive {

    @Autowired
    private HttpService httpService;

    @Autowired
    private CommandExecutor commandExecutor;
    @Override
    public Mono<BaseGetResponseDTO> execute(Void request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return this.httpService.getCurrencyList().flatMap((currencyAPIResponseDTO)->{
            return ReactiveSecurityContextHolderData.assignContextData(
                    this.commandExecutor.executeAsReactive(CreateCurrencyCommand.class,currencyAPIResponseDTO.getCurrencyList(),module,crud,mandatoryHeaderRequestDTO)
            );
        }).onErrorResume((exception)->{
            return Mono.error(new BadRequestException(module,crud, ResponseCodeMessageEnum.FAILED_CUSTOM,exception.getMessage()));
        })
                ;
    }
}
