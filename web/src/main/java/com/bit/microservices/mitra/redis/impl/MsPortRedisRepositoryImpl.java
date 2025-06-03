package com.bit.microservices.mitra.redis.impl;

import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.redis.AbstractBaseRedisCustomRepository;
import com.bit.microservices.mitra.redis.MsPortRedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MsPortRedisRepositoryImpl extends AbstractBaseRedisCustomRepository<MsPortViewDTO> implements MsPortRedisRepository {

    private ObjectMapper objectMapper;


    private ValueOperations<String, String> valueOperations;

    public MsPortRedisRepositoryImpl(RedisTemplate<String,String> redisTemplate, ObjectMapper objectMapper){
        super(redisTemplate,objectMapper,"mitra:port");
        this.objectMapper = objectMapper;
        this.valueOperations = redisTemplate.opsForValue();
    }
}
