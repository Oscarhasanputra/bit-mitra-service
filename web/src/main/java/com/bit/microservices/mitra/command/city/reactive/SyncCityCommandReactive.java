package com.bit.microservices.mitra.command.city.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface SyncCityCommandReactive extends CommandReactive<BaseGetResponseDTO, CountryIDRequestDTO> {
    @Override
    default boolean isNeedValidate(CountryIDRequestDTO request) {
        return false;
    }
}
