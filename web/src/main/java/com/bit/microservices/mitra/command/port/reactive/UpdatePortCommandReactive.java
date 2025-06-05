package com.bit.microservices.mitra.command.port.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.port.PortUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdatePortCommandReactive extends CommandReactive<List<BaseResponseDTO>, List<PortUpdateRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<PortUpdateRequestDTO> request) {
        return true;
    }
}
