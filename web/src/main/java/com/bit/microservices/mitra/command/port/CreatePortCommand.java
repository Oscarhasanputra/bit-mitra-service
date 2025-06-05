package com.bit.microservices.mitra.command.port;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.port.PortCreateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface CreatePortCommand extends Command<List<BaseResponseDTO>, List<PortCreateRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<PortCreateRequestDTO> request) {
        return true;
    }
}
