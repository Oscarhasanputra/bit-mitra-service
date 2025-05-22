package com.bit.microservices.mitra.command.city;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.annotation.RequestID;
import com.bit.microservices.mitra.model.request.city.UpdateProvinceCodeRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface UpdateProvinceCodeCityCommand extends Command<List<BaseResponseDTO>, List<UpdateProvinceCodeRequestDTO>> {
    @Override
    default boolean isNeedValidate(List<UpdateProvinceCodeRequestDTO> request) {
        return true;
    }
}
