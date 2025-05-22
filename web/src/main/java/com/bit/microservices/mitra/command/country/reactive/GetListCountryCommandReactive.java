package com.bit.microservices.mitra.command.country.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.country.CountryListDTO;

public interface GetListCountryCommandReactive extends CommandReactive<CustomPageImpl<CountryListDTO>, SearchRequestDTO> {
}
