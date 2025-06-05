package com.bit.microservices.mitra.command.city;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.city.CityByCountryDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CreateCityCommand extends Command<BaseGetResponseDTO, CityByCountryDTO> {
    @Override
    default boolean isNeedValidateHeader() {
        return false;
    }
}
