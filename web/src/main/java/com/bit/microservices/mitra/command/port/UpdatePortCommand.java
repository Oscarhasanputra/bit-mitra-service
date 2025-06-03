package com.bit.microservices.mitra.command.port;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.port.UpdatePortRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdatePortCommand extends Command<List<BaseResponseDTO>, List<UpdatePortRequestDTO>> {
}
