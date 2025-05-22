package com.bit.microservices.mitra.command.bank.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;

public interface GetListMsBankCommandReactive extends CommandReactive<CustomPageImpl<MsBankViewDTO>, SearchRequestDTO> {
}
