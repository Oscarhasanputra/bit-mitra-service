package com.bit.microservices.mitra.ms_port;

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

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(
        properties = {"spring.main.web-application-type=reactive", "spring.config.location=classpath:bootstrap.yml"},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestContainerConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class MsPortScenarioTest extends AbstractMitraTest {
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

    @Test
    @Order(value = 1)
    public void testRoleUserA(){
        String requestBody = """
                [
                        {
                                "roleId": "STAFF-IT~uuid",
                                "roleCode": "STAFF-IT",
                                "delete": [],
                                "create": [
                                        {
                                                "userId": "1~uuid",
                                                "userCode": "1"
                                        }
                                ]
                        }
                ]

                """;



        Long number = 4060040170297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/user/role-user/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 2)
    public void testRoleUserB(){
        String requestBody = """
                [
                        {
                                "roleId": "STAFF-IT~uuid",
                                "roleCode": "STAFF-IT",
                                "delete": [],
                                "create": [
                                        {
                                                "userId": "2~uuid",
                                                "userCode": "2"
                                        }
                                ]
                        },
                        {
                                "roleId": "STAFF-ACC~uuid",
                                "roleCode": "STAFF-ACC",
                                "delete": [],
                                "create": [
                                        {
                                                "userId": "2~uuid",
                                                "userCode": "2"
                                        }
                                ]
                        }
                ]

                """;



        Long number = 4060040170297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/user/role-user/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 1)
    public void testRoleUserC(){
        String requestBody = """
                [
                        {
                                "roleId": "STAFF-IT~uuid",
                                "roleCode": "STAFF-IT",
                                "delete": [],
                                "create": [
                                        {
                                                "userId": "1~uuid",
                                                "userCode": "1"
                                        }
                                ]
                        }
                ]
                                
                """;



        Long number = 4060040170297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/user/role-user/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 2)
    public void testRoleUserD(){
        String requestBody = """
                [
                        {
                                "roleId": "STAFF-IT~uuid",
                                "roleCode": "STAFF-IT",
                                "delete": [],
                                "create": [
                                        {
                                                "userId": "2~uuid",
                                                "userCode": "2"
                                        }
                                ]
                        },
                        {
                                "roleId": "STAFF-ACC~uuid",
                                "roleCode": "STAFF-ACC",
                                "delete": [],
                                "create": [
                                        {
                                                "userId": "2~uuid",
                                                "userCode": "2"
                                        }
                                ]
                        }
                ]
                             
                """;



        Long number = 4060040170297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/user/role-user/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }
}
