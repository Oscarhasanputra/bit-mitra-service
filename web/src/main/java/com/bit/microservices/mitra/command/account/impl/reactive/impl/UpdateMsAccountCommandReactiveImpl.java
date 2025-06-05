package com.bit.microservices.mitra.command.account.impl.reactive.impl;

import com.bit.microservices.mitra.command.account.UpdateMsAccountCommand;
import com.bit.microservices.mitra.command.account.impl.reactive.UpdateMsAccountCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.account.MsAccountViewDTO;
import com.bit.microservices.mitra.redis.MsAccountRedisRepository;
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
public class UpdateMsAccountCommandReactiveImpl implements UpdateMsAccountCommandReactive {

    @Autowired
    private CommandExecutor commandExecutor;

    @Autowired
    private MsAccountRedisRepository msAccountRedisRepository;

    @Override
    public Mono<List<BaseResponseDTO>> execute(List<MsAccountUpdateRequestDTO> request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return this.commandExecutor.executeAsReactive(UpdateMsAccountCommand.class,request,module,crud,mandatoryHeaderRequestDTO)
                .flatMap((responseList)->{
                    return Mono.fromCallable(()->{
                                for (BaseResponseDTO resultResponseDTO : responseList) {
                                    String id = resultResponseDTO.getId();

                                    MsAccountViewDTO msAccountViewDTO = this.msAccountRedisRepository.load(id);
                                    if(!Objects.isNull(msAccountViewDTO)){
                                        this.msAccountRedisRepository.delete(id);
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
