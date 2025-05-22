package com.bit.microservices.mitra.command.bank;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.request.bank.MsBankCreateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface CreateMsBankCommand extends Command<List<BaseResponseDTO>,List<MsBankCreateRequestDTO>> {
}
