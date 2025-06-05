package com.bit.microservices.mitra.command.account;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.account.MsAccountCreateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface CreateMsAccountCommand extends Command<List<BaseResponseDTO>,List<MsAccountCreateRequestDTO>> {

    @Override
    default boolean isNeedValidate(List<MsAccountCreateRequestDTO> request) {
        return true;
    }
}
