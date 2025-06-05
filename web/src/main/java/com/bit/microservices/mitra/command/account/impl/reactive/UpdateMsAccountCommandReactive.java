package com.bit.microservices.mitra.command.account.impl.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdateMsAccountCommandReactive extends CommandReactive<List<BaseResponseDTO>, List<MsAccountUpdateRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<MsAccountUpdateRequestDTO> request) {
        return true;
    }
}
