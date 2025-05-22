package com.bit.microservices.mitra.command.bank.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.ActivateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface ActivateMsBankCommandReactive extends CommandReactive<List<BaseResponseDTO>, List<ActivateRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<ActivateRequestDTO> request) {
        return true;
    }
}
