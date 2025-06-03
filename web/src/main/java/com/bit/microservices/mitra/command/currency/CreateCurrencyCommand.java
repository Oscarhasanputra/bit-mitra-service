package com.bit.microservices.mitra.command.currency;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface CreateCurrencyCommand extends Command<List<BaseResponseDTO>,List<CurrencyAPIDTO>>{
}
