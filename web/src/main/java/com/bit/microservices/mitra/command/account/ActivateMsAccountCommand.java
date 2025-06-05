package com.bit.microservices.mitra.command.account;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.ActivateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface ActivateMsAccountCommand extends Command<List<BaseResponseDTO>,List<ActivateRequestDTO>> {
    @Override
    default boolean isNeedValidateHeader() {
        return false;
    }
}
