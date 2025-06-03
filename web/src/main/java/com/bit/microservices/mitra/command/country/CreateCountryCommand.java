package com.bit.microservices.mitra.command.country;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;

import java.util.List;

public interface CreateCountryCommand extends Command<List<BaseResponseDTO>, List<CountryAPIResponseDTO>> {
    @Override
    default boolean isNeedValidateHeader() {
        return false;
    }
}
