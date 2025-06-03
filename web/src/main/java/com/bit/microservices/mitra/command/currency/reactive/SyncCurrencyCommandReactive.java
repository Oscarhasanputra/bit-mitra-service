package com.bit.microservices.mitra.command.currency.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface SyncCurrencyCommandReactive extends CommandReactive<List<BaseResponseDTO>,Void> {
}
