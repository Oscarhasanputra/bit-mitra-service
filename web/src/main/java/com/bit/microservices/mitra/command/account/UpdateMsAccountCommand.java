package com.bit.microservices.mitra.command.account;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdateMsAccountCommand extends Command<List<BaseResponseDTO>,List<MsAccountUpdateRequestDTO>> {
    @Override
    default boolean isNeedValidateHeader() {
        return false;
    }
}
