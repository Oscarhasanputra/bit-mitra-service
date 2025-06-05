package com.bit.microservices.mitra.redis.impl;

import com.bit.microservices.mitra.model.response.account.MsAccountViewDTO;
import com.bit.microservices.mitra.redis.AbstractBaseRedisCustomRepository;
import com.bit.microservices.mitra.redis.MsAccountRedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MsAccountRedisRepositoryImpl extends AbstractBaseRedisCustomRepository<MsAccountViewDTO> implements MsAccountRedisRepository {
    private ObjectMapper objectMapper;


    private ValueOperations<String, String> valueOperations;

    public MsAccountRedisRepositoryImpl(RedisTemplate<String,String> redisTemplate, ObjectMapper objectMapper){
        super(redisTemplate,objectMapper,"mitra:account");
        this.objectMapper = objectMapper;
        this.valueOperations = redisTemplate.opsForValue();
    }
}
