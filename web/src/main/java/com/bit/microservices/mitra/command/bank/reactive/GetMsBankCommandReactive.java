package com.bit.microservices.mitra.command.bank.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;

public interface GetMsBankCommandReactive extends CommandReactive<ViewMainResponseDTO<MsBankViewDTO>, IDRequestDTO> {
}
