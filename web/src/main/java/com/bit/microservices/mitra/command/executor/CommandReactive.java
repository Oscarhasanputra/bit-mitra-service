package com.bit.microservices.mitra.command.executor;

import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import reactor.core.publisher.Mono;

public interface CommandReactive<T, R> {
    Mono<T> execute(R request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO);

    default boolean isNeedValidate(R request) {
        return false;
    }
    default boolean isNeedValidateHeader(){
        return true;
    }
}
