package com.bit.microservices.mitra.command.account;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.DeleteRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface DeleteMsAccountCommand extends Command<List<BaseResponseDTO>, List<DeleteRequestDTO>> {
    @Override
    default boolean isNeedValidateHeader() {
        return false;
    }
}
