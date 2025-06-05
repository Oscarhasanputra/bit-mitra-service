package com.bit.microservices.mitra.command.account.impl.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.DeleteRequestDTO;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface DeleteMsAccountCommandReactive extends CommandReactive<List<BaseResponseDTO>,List<DeleteRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<DeleteRequestDTO> request) {
        return true;
    }
}
