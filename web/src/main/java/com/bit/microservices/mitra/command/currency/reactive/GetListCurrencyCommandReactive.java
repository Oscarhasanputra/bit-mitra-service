package com.bit.microservices.mitra.command.currency.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.currency.CurrencyListDTO;

public interface GetListCurrencyCommandReactive extends CommandReactive<CustomPageImpl<CurrencyListDTO>, SearchRequestDTO> {
}
