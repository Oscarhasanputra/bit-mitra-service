package com.bit.microservices.mitra.config;

import com.redis.testcontainers.RedisContainer;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

@Slf4j
public abstract class AbstractMitraTest {

    protected String MOCK_TOKEN = "tokenMock";
    protected String MOCK_TOKEN_VALUE = "userTester";
    // Static containers
    protected static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:14.13-alpine")
            .withDatabaseName("BIT_MITRA_TEST")
            .withReuse(true);

    protected static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:7.0-alpine"))
            .withExposedPorts(6379)
            .withReuse(true);

    // Static initialization block to start containers
    static {
        POSTGRES_CONTAINER.start();
        REDIS_CONTAINER.start();
    }

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        // Postgres properties
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);

        // Redis properties
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379));
    }

    protected JsonPath callRestAssured(Map<String, String> headers, String request, String endpoint, int expectedStatusCode) {
        return RestAssured.given()
                .log().all()
                .contentType("application/json")
                .headers(headers)
                .body(request)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract()
                .body()
                .jsonPath();
    }

    protected ValidatableResponse callRestAssuredValidatable(Map<String, String> headers, String request, String endpoint, int expectedStatusCode) {
        return RestAssured.given()
                .log().all()
                .contentType("application/json")
                .headers(headers)
                .body(request)
                .when()
                .post(endpoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode);
    }
}
