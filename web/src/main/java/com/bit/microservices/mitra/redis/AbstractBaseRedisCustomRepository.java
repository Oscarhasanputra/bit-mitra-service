package com.bit.microservices.mitra.redis;


import com.bit.microservices.mitra.exception.ExceptionPrinter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractBaseRedisCustomRepository<S> implements BaseRedisCustomRepository<S>{

    private static final long ttl = 3;

    private ValueOperations<String, String> valueOperations;

    private RedisTemplate<String,String> template;

    private ObjectMapper mapper;

    private String prefix;

    private Class<S> genericClass;

    public AbstractBaseRedisCustomRepository(RedisTemplate<String,String> redisTemplate, ObjectMapper objectMapper,String prefix){
        this.valueOperations = redisTemplate.opsForValue();
        this.mapper= objectMapper;
        this.template = redisTemplate;
        Type superClass = getClass().getGenericSuperclass();
        this.prefix = prefix;
        this.genericClass = (Class<S>) ((ParameterizedType) superClass).getActualTypeArguments()[0];

    }
    public AbstractBaseRedisCustomRepository(RedisTemplate<String,String> redisTemplate, ObjectMapper objectMapper){
        this.valueOperations = redisTemplate.opsForValue();
        this.mapper= objectMapper;
        this.template = redisTemplate;
        Type superClass = getClass().getGenericSuperclass();
        this.prefix = "";
        this.genericClass = (Class<S>) ((ParameterizedType) superClass).getActualTypeArguments()[0];

    }
    @Override
    public List<S> load(Set<String> keys) {
        List<S> result = new ArrayList<>();

        for(String key : keys) {
            S dto = this.load(this.prefix+key);
            result.add(dto);
        }

        return result;
    }

    @Override
    public S load(String key) {
        try {
            String result = this.valueOperations.get(this.prefix+key);
            if (!Objects.isNull(result)) {
                return mapper.readValue(result, this.genericClass);
            }

        } catch (JsonProcessingException ex) {
            ExceptionPrinter print = new ExceptionPrinter(ex);
            log.error(print.getMessage());
        }
        return null;
    }

    @Override
    public void save(String key, S data) {
        try {
            this.valueOperations.set(this.prefix+key, mapper.writeValueAsString(data));
            this.template.expire(this.prefix+key, ttl, TimeUnit.DAYS);
        } catch (JsonProcessingException ex) {
            ExceptionPrinter print = new ExceptionPrinter(ex);
            log.error(print.getMessage());
        }
    }

    @Override
    public Set<String> getKey(String key) {
        return this.template.keys(this.prefix+key);
    }

    @Override
    public void delete(String key) {
        this.template.delete(this.prefix+key);
    }

    @Override
    public void delete(Collection<String> keys) {
        this.template.delete(this.prefix+keys);
    }
}
