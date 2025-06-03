package com.bit.microservices.mitra.redis;

import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;

public interface MsPortRedisRepository extends BaseRedisCustomRepository<MsPortViewDTO> {
}
