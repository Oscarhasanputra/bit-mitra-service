package com.bit.microservices.mitra.command.country.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface SyncCountryCommandReactive extends CommandReactive<List<BaseResponseDTO>,Void> {
    @Override
    default boolean isNeedValidate(Void request) {
        return false;
    }
}
