package com.bit.microservices.mitra.config;

import com.bit.microservices.mitra.http.HttpService;
import com.redis.testcontainers.RedisContainer;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
@Import(DatabaseInitializer.class)
public class TestContainerConfiguration {
    @Bean
    @ServiceConnection(name = "postgres")
    public PostgreSQLContainer<?> postgresContainer() {
        return AbstractMitraTest.POSTGRES_CONTAINER;
    }

    @Bean
    @ServiceConnection(name = "redis")
    public RedisContainer redisContainer() {
        return AbstractMitraTest.REDIS_CONTAINER;
    }

    @Bean
    public HttpService httpService(){
        return Mockito.mock(HttpService.class);
    }
}
