package com.bit.microservices.mitra.command.port.reactive;

import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;

public interface GetListPortCommandReactive extends CommandReactive<CustomPageImpl<MsPortViewDTO>, SearchRequestDTO> {
}
