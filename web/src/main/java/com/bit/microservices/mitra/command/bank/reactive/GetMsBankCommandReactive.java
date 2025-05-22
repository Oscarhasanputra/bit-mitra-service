package com.bit.microservices.mitra.command.bank.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.GetSingleRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.model.IdRequestDto;

public interface GetMsBankCommandReactive extends CommandReactive<ViewMainResponseDTO<MsBankViewDTO>, GetSingleRequestDTO> {
}
