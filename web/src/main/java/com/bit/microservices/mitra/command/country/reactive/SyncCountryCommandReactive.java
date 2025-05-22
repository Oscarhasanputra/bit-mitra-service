package com.bit.microservices.mitra.command.country.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;

public interface SyncCountryCommandReactive extends CommandReactive<BaseGetResponseDTO,Void> {
    @Override
    default boolean isNeedValidate(Void request) {
        return false;
    }
}
