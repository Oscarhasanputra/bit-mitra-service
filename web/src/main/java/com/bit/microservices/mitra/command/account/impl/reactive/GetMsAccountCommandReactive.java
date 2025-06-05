package com.bit.microservices.mitra.command.account.impl.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.account.MsAccountViewDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;

import java.util.List;

public interface GetMsAccountCommandReactive extends CommandReactive<ViewMainResponseDTO<MsAccountViewDTO>,IDRequestDTO> {

}
