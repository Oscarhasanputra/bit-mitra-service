package com.bit.microservices.mitra.ms_currency;

import com.bit.microservices.mitra.config.AbstractMitraTest;
import com.bit.microservices.mitra.config.TestContainerConfiguration;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIResponseDTO;
import com.redis.testcontainers.RedisContainer;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;
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

    @MockitoBean
    private HttpService httpService;

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
    public void testCurrencyA(){
        String requestBody = """
                """;


        Long number = 4060080031497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 2)
    public void testCurrencyB(){
        String requestBody = """
                """;



        Long number = 4060080031497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 3)
    public void testCurrencyC(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;



        Long number = 4060080030597L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/get-list",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 4)
    public void testCurrencyD(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;



        Long number = 4060080030597L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/get-list",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 5)
    public void syncCurrencySuccess1(){
        String requestBody = """
                """;

        Map<String,String> mapData = Map.of("IDR","RUPIAH");
        CurrencyAPIResponseDTO currencyAPIResponseDTO = new CurrencyAPIResponseDTO(mapData);

        Mockito.when(httpService.getCurrencyList()).thenReturn(Mono.just(currencyAPIResponseDTO));

        Long number = 2000080031400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",200)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 6)
    public void syncCurrencyFailed2(){
        String requestBody = """
                """;

        Mockito.when(httpService.getCurrencyList()).thenReturn(Mono.error(new Exception("Connection Timeout")));

        Long number = 5000080031402L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",400)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed connection, data disconnected (timeout)"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 7)
    public void syncCurrencySuccess3(){
        String requestBody = """
                """;

        Map<String,String> mapData = Map.of("IDR","RUPIAH","JPY","YEN","USD","US DOLLAR");

        CurrencyAPIResponseDTO currencyAPIResponseDTO = new CurrencyAPIResponseDTO(mapData);

        Mockito.when(httpService.getCurrencyList()).thenReturn(Mono.just(currencyAPIResponseDTO));

        Long number = 2000080031400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",200)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 8)
    public void syncCurrencySuccess4(){
        String requestBody = """
                """;

        Map<String,String> mapData = Map.of("IDR","RUPIAH");
        CurrencyAPIResponseDTO currencyAPIResponseDTO = new CurrencyAPIResponseDTO(mapData);

        Mockito.when(httpService.getCurrencyList()).thenReturn(Mono.just(currencyAPIResponseDTO));

        Long number = 2000080031400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",200)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 9)
    public void syncCurrencyFailed5(){
        String requestBody = """
                """;

        Mockito.when(httpService.getCurrencyList()).thenReturn(Mono.error(new Exception("Connection Timeout")));

        Long number = 5000080031402L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/_sync-from-google",400)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed connection, data disconnected (timeout)"))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 10)
    public void getListCurrencySuccess6(){
        String requestBody = """
                 {
                      "requestType": "LIST",
                      "size": 10,
                      "page": 1,
                      "sortBy": {},
                      "filterBy": {}
                }
                """;

        Long number1 = 2000080030500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo(status))
                .body("result.content",hasSize(equalTo(3)));
    }

    @Test
    @Order(value = 11)
    public void getListCurrencyFailed7(){
        String requestBody = """
                 {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {
                                    "code": "ASC",
                                    "name": "ASC",
                                    "active": "ASC",
                                    "zipcode": "ASC"
                                  },
                        "filterBy": {
                                    "name": [
                                            "JAKARTA",
                                            "BAKAUHENI"
                                        ],
                                    "active": true,
                                    "deleted": false
                        }
                }
                """;


        Long number1 = 4000080030518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: zipcode"))
                .body("result.content",hasSize(equalTo(0)));
    }

    @Test
    @Order(value = 12)
    public void getListCurrencySuccess8(){
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
                                            "IDR",
                                            "USR"
                                        ],
                                    "active": true,
                                    "deleted": false
                        }
                }
                """;

        Long number1 = 2000080030500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo(status))
                .body("result.content",hasSize(equalTo(1)))
                .body("result.content.code.flatten()",hasItems(
                        equalTo("IDR")
                ));
    }

    @Test
    @Order(value = 13)
    public void getListCurrencyFailed9(){
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
                                    "name": [
                                            "JAKARTA",
                                            "BAKAUHENI"
                                        ],
                                    "active": ["TRUE"],
                                    "deleted": false
                        }
                }
                """;


        Long number1 = 4000080030592L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/currency/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo("Failed mismatch in filtering field : active"))
                .body("result.content",hasSize(equalTo(0)));
    }

}
