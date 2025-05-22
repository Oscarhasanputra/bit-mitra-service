package com.bit.microservices.mitra.redis;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BaseRedisCustomRepository<T> {

    List<T> load(Set<String> key);
    T load(String key);

    void save(String key, T data);

    Set<String> getKey(String key);

    void delete(String key);
    void delete(Collection<String> keys);
}
