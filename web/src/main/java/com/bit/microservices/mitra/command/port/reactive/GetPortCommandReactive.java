package com.bit.microservices.mitra.command.port.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;

public interface GetPortCommandReactive extends CommandReactive<ViewMainResponseDTO<MsPortViewDTO>, IDRequestDTO> {
}
