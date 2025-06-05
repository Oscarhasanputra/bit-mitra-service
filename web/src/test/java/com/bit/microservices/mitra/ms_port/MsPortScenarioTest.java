package com.bit.microservices.mitra.ms_port;

import com.bit.microservices.mitra.config.AbstractMitraTest;
import com.bit.microservices.mitra.config.TestContainerConfiguration;
import com.bit.microservices.mitra.model.entity.MsBank;
import com.bit.microservices.mitra.model.entity.MsPort;
import com.bit.microservices.mitra.model.entity.QMsBank;
import com.bit.microservices.mitra.model.entity.QMsPort;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.redis.MsPortRedisRepository;
import com.bit.microservices.mitra.repository.MsPortRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
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

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.everyItem;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Autowired
    private MsPortRepository msPortRepository;

    @Autowired
    private MsPortRedisRepository msPortRedisRepository;

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
        assertNotNull(msPortRedisRepository);
        assertNotNull(msPortRepository);
    }

    @Test
    @Order(value = 1)
    public void testPortA(){
        String requestBody = """
                [
                        {
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4060080050197L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 2)
    public void testPortB(){
        String requestBody = """
                [
                        {
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4060080050197L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 3)
    public void testPortC(){
        String requestBody = """
                [
                        {
                                "id": "MERAK~uuid",
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "",
                                "active": true,
                                "remarks": ""
                        }
                ]
                               
                """;


        Long number = 4060080050297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 4)
    public void testPortD(){
        String requestBody = """
                [
                        {
                                "id": "MERAK~uuid",
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "",
                                "active": true,
                                "remarks": ""
                        }
                ]
                                
                """;


        Long number = 4060080050297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 5)
    public void testPortE(){
        String requestBody = """
                [
                        {
                                "id": "MERAK~uuid",
                                "deletedReason": "port tidak jadi dibuat"
                        }
                ]      
                """;


        Long number = 4060080050397L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/delete",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 6)
    public void testPortF(){
        String requestBody = """
                [
                        {
                                "id": "MERAK~uuid",
                                "deletedReason": "port tidak jadi dibuat"
                        }
                ]
                      
                """;


        Long number = 4060080050397L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/delete",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 7)
    public void testPortG(){
        String requestBody = """
                 {
                                "id": "MERAK~uuid",
                                "deletedReason": "port tidak jadi dibuat"
                 }
                """;


        Long number = 4060080050497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 8)
    public void testPortH(){
        String requestBody = """
                {
                                "id": "MERAK~uuid",
                                "deletedReason": "port tidak jadi dibuat"
                 }
                """;


        Long number = 4060080050497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 9)
    public void testPortI(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;


        Long number = 4060080050597L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 10)
    public void testPortJ(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;


        Long number = 4060080050597L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 11)
    public void createPortSuccess1(){
        String requestBody = """
                [
                        {
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 2000080050100L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }
    @Test
    @Order(value = 12)
    public void createPortFailed2(){
        String requestBody = """
                [
                        {
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4090080050105L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");
        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data already exists")));
    }

    @Test
    @Order(value = 13)
    public void createPortFailed3(){
        String requestBody = """
                [
                        {
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BAKAUHENI",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4090080050105L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data already exists")));
    }

    @Test
    @Order(value = 14)
    public void createPortFailed4(){
        String requestBody = """
                [
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BAKAUHENI",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "zipcode": "",
                                "designPathFile": "",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4040080050107L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BAKAUHENI")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed unexpected field detected: zipcode"),
                        equalTo("Failed unexpected field detected: designPathFile")
                ));
    }


    @Test
    @Order(value = 15)
    public void createPortFailed5(){
        String requestBody = """
                [
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4000080050184L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed concurrency detected for this request")
                ));
    }
    @Test
    @Order(value = 16)
    public void createPortSuccess6(){
        String requestBody = """
                [
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BAKAUHENI",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 2000080050100L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK"),
                        containsString("BAKAUHENI"),
                        containsString("SOETA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value = 17)
    public void createPortFailed7(){
        String requestBody = """
                [
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 4090080050105L;
        Long number2 = 4000080050184L;
        Long number3 = 4090080050105L;


        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK"),
                        containsString("GILIMANUK"),
                        containsString("SOETA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(number1),
                        equalTo(number2),
                        equalTo(number3)
                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed data already exists"),
                        equalTo("Failed concurrency detected for this request"),
                        equalTo("Failed data already exists")

                ));
    }

    @Test
    @Order(value = 18)
    public void createPortFailed8(){
        String requestBody = """
                [
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BAKAUHENI",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4090080050105L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed data already exists")
                ));
    }

    @Test
    @Order(value = 19)
    public void deletePortSuccess9(){
        BooleanExpression predicate = QMsPort.msPort.code.in("MERAK");
        Iterator<MsPort> msPortIterator = this.msPortRepository.findAll(predicate).iterator();
        List<String> dataParams = new ArrayList<>();
        while(msPortIterator.hasNext()){
            MsPort msPort = msPortIterator.next();
            dataParams.add(msPort.getId());
        }
        String requestBody = """
                [
                        {
                                "id": "%s",
                                "deletedReason": "port tidak jadi dibuat"
                        }
                ]
                """;


        String requestBodyFormatted = requestBody.formatted(dataParams.toArray());
        Long number = 2000080050300L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/delete",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }
    @Test
    @Order(value = 20)
    public void deletePortSuccess10(){
        BooleanExpression predicate = QMsPort.msPort.code.in("GILIMANUK","BAKAUHENI");
        Iterator<MsPort> msPortIterator = this.msPortRepository.findAll(predicate).iterator();
        List<String> dataParams = new ArrayList<>();
        while(msPortIterator.hasNext()){
            MsPort msPort = msPortIterator.next();
            dataParams.add(msPort.getId());
        }
        String requestBody = """
                [
                        {
                                "id": "%s",
                                "deletedReason": "port tidak jadi dibuat"
                        },
                        {
                                "id": "%s",
                                "deletedReason": "port tidak jadi dibuat"
                        }
                ]
                               
                """;


        String requestBodyFormatted = requestBody.formatted(dataParams.toArray());
        Long number = 2000080050300L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/delete",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK"),
                        containsString("BAKAUHENI")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value = 21)
    public void deletePortFailed11(){
        BooleanExpression predicate = QMsPort.msPort.code.in("MERAK","SOETA");
        Iterator<MsPort> msPortIterator = this.msPortRepository.findAll(predicate).iterator();
        List<String> dataParams = new ArrayList<>();
        while(msPortIterator.hasNext()){
            MsPort msPort = msPortIterator.next();
            dataParams.add(msPort.getId());
        }
        String requestBody = """
                [
                        {
                                "id": "%s",
                                "deletedReason": "port tidak jadi dibuat"
                        },
                        {
                                "id": "%s",
                                "deletedReason": "port tidak jadi dibuat"
                        }
                ]
                               
                """;


        String requestBodyFormatted = requestBody.formatted(dataParams.toArray());
        Long number =  4040080050304L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/delete",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));
    }

    @Test
    @Order(value = 22)
    public void updatePortFailed12(){
        BooleanExpression predicate = QMsPort.msPort.code.in("MERAK");
        Iterator<MsPort> msPortIterator = this.msPortRepository.findAll(predicate).iterator();
        List<String> dataParams = new ArrayList<>();
        while(msPortIterator.hasNext()){
            MsPort msPort = msPortIterator.next();
            dataParams.add(msPort.getId());
        }
        String requestBody = """
                [
                        {
                                "id": "MERAK~uuid",
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                          
                """;


        String requestBodyFormatted = requestBody.formatted(dataParams.toArray());
        Long number =  4040080050204L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/update",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));
    }

    @Test
    @Order(value = 23)
    public void createPortSuccess13(){

        String requestBody = """
                [
                        {
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                   
                """;

        Long number =  2000080050100L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value = 24)
    public void updatePortSuccess14(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);

        BooleanExpression predicate2 = QMsPort.msPort.code.eq("SOETA").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort2 = this.msPortRepository.findOne(predicate2).orElse(null);

        assertNotNull(msPort1);
        assertNotNull(msPort2);

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "id": "%s",
                                "code": "SOETA",
                                "name": "BANDARA SOEKARNO HATTA",
                                "address": "TANGERANG BANTEN",
                                "type": "AIRPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "TANGERANG~uuid",
                                "cityCode": "TANGERANG",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId(),msPort2.getId());

        Long number =  2000080050200L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/update",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK"),
                        containsString("SOETA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value =25)
    public void updatePortFailed15(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);

        BooleanExpression predicate2 = QMsPort.msPort.code.eq("GILIMANUK");
        MsPort msPort2 = this.msPortRepository.findOne(predicate2).orElse(null);

        assertNotNull(msPort1);
        assertNotNull(msPort2);

        String requestBody = """
                [
                        {
                                "id": "MERAK~uuid",
                                "code": "MERAK",
                                "name": "PELABUHAN MERAK",
                                "address": "MERAK BANTEN",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "CILEGON~uuid",
                                "cityCode": "CILEGON",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "id": "GILIMANUK~uuid",
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId(),msPort2.getId());

        Long number =  4040080050204L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/update",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));
    }

    @Test
    @Order(value = 26)
    public void createPortSuccess16(){

        String requestBody = """
                [
                        {
                                "code": "GILIMANUK",
                                "name": "PELABUHAN GILIMANUK",
                                "address": "GILIMANUK BALI",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "GILIMANUK~uuid",
                                "cityCode": "GILIMANUK",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BAKAUHENI",
                                "name": "PELABUHAN BAKAUHENI",
                                "address": "BAKAUHENI LAMPUNG",
                                "type": "SEAPORT",
                                "countryId": "INDONESIA~uuid",
                                "countryCode": "INDONESIA",
                                "cityId": "BAKAUHENI~uuid",
                                "cityCode": "BAKAUHENI",
                                "pic": "",
                                "phoneNumber": "08123123123",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;

        Long number =  2000080050100L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("GILIMANUK"),
                        containsString("BAKAUHENI")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value =27)
    public void activatePortSuccess17(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);


        assertNotNull(msPort1);

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "active": false
                        }
                ]
                                
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId());

        Long number =  2000080050600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value =28)
    public void activatePortSuccess18(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);


        assertNotNull(msPort1);

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "active": true
                        }
                ]
                                
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId());

        Long number =  2000080050600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value =29)
    public void activatePortSuccess19(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);

        BooleanExpression predicate2 = QMsPort.msPort.code.eq("GILIMANUK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort2 = this.msPortRepository.findOne(predicate2).orElse(null);


        assertNotNull(msPort1);
        assertNotNull(msPort2);

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "active": false
                        },
                        {
                                "id": "%s",
                                "active": false
                        }
                ]
                                
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId(),msPort2.getId());

        Long number =  2000080050600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK"),
                        containsString("GILIMANUK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }


    @Test
    @Order(value =30)
    public void activatePortSuccess20(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);

        BooleanExpression predicate2 = QMsPort.msPort.code.eq("GILIMANUK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort2 = this.msPortRepository.findOne(predicate2).orElse(null);


        assertNotNull(msPort1);
        assertNotNull(msPort2);

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "active": true
                        },
                        {
                                "id": "%s",
                                "active": true
                        }
                ]
                                
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId(),msPort2.getId());

        Long number =  2000080050600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MERAK"),
                        containsString("GILIMANUK")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }

    @Test
    @Order(value =31)
    public void getPortSuccess21(){
        BooleanExpression predicate1 = QMsPort.msPort.code.eq("MERAK").and( QMsPort.msPort.isDeleted.eq(false));
        MsPort msPort1 = this.msPortRepository.findOne(predicate1).orElse(null);


        assertNotNull(msPort1);

        String requestBody = """
                  {
                    "id": "%s"
                  }    
                """;

        String requestBodyFormatted = requestBody.formatted(msPort1.getId());

        Long number =  2000080050400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/port/v1/0/get",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo(status))
                .body("result.id",equalTo(msPort1.getId()));

        MsPortViewDTO msPortViewDTO = this.msPortRedisRepository.load(msPort1.getId());
        assertNotNull(msPortViewDTO);
    }

    @Test
    @Order(value =32)
    public void getPortFailed22(){


        String requestBody = """
                 {
                   "id": "SERANG~uuid"
                 }
                """;

        Long number =  4040080050404L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo("Failed data not found"));

    }

    @Test
    @Order(value =33)
    public void getListPortSuccess23(){


        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;


        Long number =  2000080050500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo(status))
                .body("result.content",hasSize(7));
    }

    @Test
    @Order(value =34)
    public void getListPortSuccess24(){


        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {
                                "code": "ASC",
                                "name": "ASC",
                                "active": "ASC",
                                "deleted": "ASC"
                        },
                        "filterBy": {
                                "code": [
                                        "MERAK",
                                        "GILIMANUK"
                                ],
                                "name": [
                                        "MERAK"
                                ],
                                "active": true,
                                "deleted": false
                        }
                }
                """;


        Long number =  2000080050500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo(status))
                .body("result.content",hasSize(1));
    }

    @Test
    @Order(value =35)
    public void getListPortFailed25(){


        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {
                                "code": "ASC",
                                "name": "ASC",
                                "active": "ASC",
                                "deleted": "ASC",
                                "createdBy": "ASC"
                        },
                        "filterBy": {
                                "code": [
                                        "MERAK",
                                        "GILIMANUK"
                                ],
                                "name": [
                                        "MERAK"
                                ],
                                "active": false,
                                "deleted": false
                        }
                }
                """;


        Long number =  4000080050518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: createdBy"))
                .body("result.content",hasSize(0));
    }

    @Test
    @Order(value =36)
    public void getListPortFailed26(){


        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {
                                "code": "ASC",
                                "name": "ASC",
                                "active": "ASC",
                                "deleted": "ASC",
                                "zipCode": "ASC"
                        },
                        "filterBy": {
                                "code": [
                                        "MERAK",
                                        "GILIMANUK"
                                ],
                                "name": [
                                        "MERAK"
                                ],
                                "active": false,
                                "deleted": false
                        }
                }
                """;


        Long number =  4000080050518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: zipCode"))
                .body("result.content",hasSize(0));
    }

    @Test
    @Order(value =37)
    public void getListPortFailed27(){


        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {
                                "code": "ASC",
                                "name": "ASC",
                                "active": "ASC",
                                "deleted": "ASC"
                        },
                        "filterBy": {
                                "code": [
                                        "MERAK",
                                        "GILIMANUK"
                                ],
                                "name": [
                                        "MERAK"
                                ],
                                "active": [false],
                                "deleted": false
                        }
                }
                """;


        Long number =  4000080050592L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/port/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo("Failed mismatch in filtering field : active"))
                .body("result.content",hasSize(0));
    }


}

