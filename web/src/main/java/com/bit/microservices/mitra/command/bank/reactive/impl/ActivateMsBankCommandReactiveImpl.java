package com.bit.microservices.mitra.command.bank.reactive.impl;

import com.bit.microservices.mitra.command.bank.ActivateMsBankCommand;
import com.bit.microservices.mitra.command.bank.UpdateMsBankCommand;
import com.bit.microservices.mitra.command.bank.reactive.ActivateMsBankCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.request.ActivateRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.redis.MsBankRedisRepository;
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
public class ActivateMsBankCommandReactiveImpl implements ActivateMsBankCommandReactive {
    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private MsBankRedisRepository msBankRedisRepository;
    @Override
    public Mono<List<BaseResponseDTO>> execute(List<ActivateRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return Mono.just(requests)
                .flatMap((requestDto)-> ReactiveSecurityContextHolderData.assignContextData(this.commandExecutor.executeAsReactive(ActivateMsBankCommand.class,requestDto, module, crud,mandatoryHeaderRequestDTO)))
                .flatMap((msRoleResponseList)->{
                    return Mono.fromCallable(()->{
                                for (BaseResponseDTO resultResponseDTO : msRoleResponseList) {
                                    String id = resultResponseDTO.getId();

                                    MsBankViewDTO msModuleRedis = this.msBankRedisRepository.load(id);
                                    if(!Objects.isNull(msModuleRedis)){
                                        this.msBankRedisRepository.delete(msModuleRedis.getId());
                                    }
                                }

                                return msRoleResponseList;
                            }).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
                            .onErrorResume((err)->{
                                return Mono.just(msRoleResponseList);
                            });
                });
    }
}
