package com.bit.microservices.mitra.command.city.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.city.CityListDTO;

public interface GetListCityCommandReactive extends CommandReactive<CustomPageImpl<CityListDTO>, SearchRequestDTO> {
    @Override
    default boolean isNeedValidate(SearchRequestDTO request) {
        return false;
    }
}
