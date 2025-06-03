package com.bit.microservices.mitra.command.bank.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.DeleteRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface DeleteMsBankCommandReactive extends CommandReactive<List<BaseResponseDTO>, List<DeleteRequestDTO>> {
}
