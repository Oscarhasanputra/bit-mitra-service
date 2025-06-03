package com.bit.microservices.mitra.command.port.reactive.impl;

import com.bit.microservices.mitra.command.bank.UpdateMsBankCommand;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.command.port.UpdatePortCommand;
import com.bit.microservices.mitra.command.port.reactive.UpdatePortCommandReactive;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.entity.MsPort;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.port.UpdatePortRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.redis.MsBankRedisRepository;
import com.bit.microservices.mitra.redis.MsPortRedisRepository;
import com.bit.microservices.mitra.repository.MsPortRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class UpdatePortCommandReactiveImpl implements UpdatePortCommandReactive {

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private MsPortRepository msPortRepository;

    @Autowired
    private MsPortRedisRepository msPortRedisRepository;
    @Override
    public Mono<List<BaseResponseDTO>> execute(List<UpdatePortRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return Mono.just(requests)
                .flatMap((requestDto)-> ReactiveSecurityContextHolderData.assignContextData(this.commandExecutor.executeAsReactive(UpdatePortCommand.class,requestDto, module, crud,mandatoryHeaderRequestDTO)))
                .flatMap((responseList)->{
                    return Mono.fromCallable(()->{
                                for (BaseResponseDTO resultResponseDTO : responseList) {
                                    String id = resultResponseDTO.getId();

                                    MsPortViewDTO msPortViewDTO = this.msPortRedisRepository.load(id);
                                    if(!Objects.isNull(msPortViewDTO)){
                                        this.msPortRedisRepository.delete(id);
                                    }
                                }

                                return responseList;
                            }).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
                            .onErrorResume((err)->{
                                return Mono.just(responseList);
                            });
                });
    }
}
