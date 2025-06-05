package com.bit.microservices.mitra.command.bank;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.bank.MsBankUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdateMsBankCommand extends Command<List<BaseResponseDTO>,List<MsBankUpdateRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<MsBankUpdateRequestDTO> request) {
        return true;
    }
}
