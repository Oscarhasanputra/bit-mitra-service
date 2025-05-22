package com.bit.microservices.mitra.redis.impl;

import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.redis.AbstractBaseRedisCustomRepository;
import com.bit.microservices.mitra.redis.MsBankRedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class MsBankRedisRepositoryImpl extends AbstractBaseRedisCustomRepository<MsBankViewDTO> implements MsBankRedisRepository {

    private ObjectMapper objectMapper;


    private ValueOperations<String, String> valueOperations;

    public MsBankRedisRepositoryImpl(RedisTemplate<String,String> redisTemplate, ObjectMapper objectMapper){
        super(redisTemplate,objectMapper,"mitra:bank");
        this.objectMapper = objectMapper;
        this.valueOperations = redisTemplate.opsForValue();
    }
}
