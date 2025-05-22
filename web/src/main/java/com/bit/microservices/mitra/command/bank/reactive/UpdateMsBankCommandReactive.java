package com.bit.microservices.mitra.command.bank.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.bank.MsBankUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdateMsBankCommandReactive extends CommandReactive<List<BaseResponseDTO>,List<MsBankUpdateRequestDTO>> {
}
