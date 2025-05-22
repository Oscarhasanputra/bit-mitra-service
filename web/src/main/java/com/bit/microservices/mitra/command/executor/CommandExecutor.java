package com.bit.microservices.mitra.command.executor;

import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import reactor.core.publisher.Mono;

public interface CommandExecutor {
    <T, R> T execute(Class<? extends Command<T, R>> commandClass, R request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO);

    default <T, R> Mono<T> executeAsReactive(Class<? extends Command<T, R>> commandClass, R request, ModuleCodeEnum module, CrudCodeEnum crud,MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        return Mono.create(sink -> {
            try {
                T results = this.execute(commandClass, request, module, crud,mandatoryHeaderRequestDTO);
                sink.success(results);
            } catch (Exception e) {
                sink.error(e);
            }
        });
    }

    <T, R> Mono<T> executeReactive(Class<? extends CommandReactive<T, R>> commandClass, R request, ModuleCodeEnum module, CrudCodeEnum crud,MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO);
}
