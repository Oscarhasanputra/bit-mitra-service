package com.bit.microservices.mitra.ms_currency;

import com.bit.microservices.mitra.config.AbstractMitraTest;
import com.bit.microservices.mitra.config.TestContainerConfiguration;
import com.redis.testcontainers.RedisContainer;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(
        properties = {"spring.main.web-application-type=reactive", "spring.config.location=classpath:bootstrap.yml"},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestContainerConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class MsCurrencyScenarioTest extends AbstractMitraTest {

    @Autowired
    private PostgreSQLContainer<?> postgresContainer;

    @Autowired
    private RedisContainer redisContainer;

    @LocalServerPort
    private Integer serverPort;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    @Order(value = 0)
    void testConnectionToDatabase() {

        assertTrue(postgresContainer.isRunning());
        assertTrue(redisContainer.isRunning());
    }
}
