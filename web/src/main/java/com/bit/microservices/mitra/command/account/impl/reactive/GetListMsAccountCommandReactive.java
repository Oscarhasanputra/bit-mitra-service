package com.bit.microservices.mitra.command.account.impl.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.account.MsAccountListDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;

public interface GetListMsAccountCommandReactive extends CommandReactive<CustomPageImpl<MsAccountListDTO>, SearchRequestDTO> {
}
