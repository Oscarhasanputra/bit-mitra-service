package com.bit.microservices.mitra.ms_country;

import com.bit.microservices.mitra.config.AbstractMitraTest;
import com.bit.microservices.mitra.config.TestContainerConfiguration;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.mitra.model.dto.country.NameCountryAPIDTO;
import com.redis.testcontainers.RedisContainer;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(
        properties = {"spring.main.web-application-type=reactive", "spring.config.location=classpath:bootstrap.yml"},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(TestContainerConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class MsCountryScenarioTest extends AbstractMitraTest {

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
    public void testCountryA(){
        String requestBody = """
                """;



        Long number = 4060080011497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/_sync-from-google",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 2)
    public void testCountryB(){
        String requestBody = """
                """;



        Long number = 4060080011497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/_sync-from-google",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 3)
    public void syncCountrySuccess1(){
        String requestBody = """
                """;

        List<CountryAPIResponseDTO> countryList  = new ArrayList<>();
        CountryAPIResponseDTO country1 = new CountryAPIResponseDTO();
        country1.setCode("ID");
        country1.setName(new NameCountryAPIDTO("Indonesia","Indonesia"));


        CountryAPIResponseDTO country2 = new CountryAPIResponseDTO();
        country2.setCode("CN");
        country2.setName(new NameCountryAPIDTO("China","China"));
        countryList.add(country1);
        countryList.add(country2);
        Mockito.when(httpService.getListCountry()).thenReturn(Mono.just(countryList));

        Long number = 2000080011400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/_sync-from-google",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo(status));
    }


    @Test
    @Order(value = 4)
    public void syncCountryFailed2(){
        String requestBody = """
                """;

        Mockito.when(httpService.getListCountry()).thenReturn(Mono.error(new Exception("Error Test")));

        Long number = 5000080011402L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/_sync-from-google",400)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed connection, data disconnected (timeout)"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 5)
    public void syncCountrySuccess3(){
        String requestBody = """
                """;

        List<CountryAPIResponseDTO> countryList  = new ArrayList<>();
        Mockito.when(httpService.getListCountry()).thenReturn(Mono.just(countryList));

        Long number = 2000080011400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/_sync-from-google",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("responseMessage",equalTo(status));
    }


    @Test
    @Order(value = 6)
    public void syncCountryFailed4(){
        String requestBody = """
                """;

        Mockito.when(httpService.getListCountry()).thenReturn(Mono.error(new Exception("Error Test")));

        Long number = 5000080011402L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/_sync-from-google",400)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed connection, data disconnected (timeout)"))
                .body("responseCode",equalTo(number));
    }



    @Test
    @Order(value = 7)
    public void getListCountrySuccess5(){
        String requestBody = """
                 {
                      "requestType": "LIST",
                      "size": 10,
                      "page": 1,
                      "sortBy": {},
                      "filterBy": {}
                }
                """;

        Long number1 = 2000080010500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo(status))
                .body("result.content",hasSize(equalTo(2)));
    }

    @Test
    @Order(value = 8)
    public void getListCountryFailed6(){
        String requestBody = """
                {
                      "requestType": "LIST",
                      "size": 10,
                      "page": 1,
                      "sortBy": {
                        "actived":"desc"
                      },
                      "filterBy": {
                        "actived":["PHOENIX"]
                      }
               }
                """;


        Long number1 = 4000080010518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: actived"))
                .body("result.content",hasSize(equalTo(0)));
    }

    @Test
    @Order(value = 9)
    public void getListCountrySuccess7(){
        String requestBody = """
               {
                      "requestType": "LIST",
                      "size": 10,
                      "page": 1,
                      "sortBy": {
                        "active":"DESC"
                      },
                      "filterBy": {
                        "code":["ID"]
                      }
               }
                """;

        Long number1 = 2000080010500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo(status))
                .body("result.content",hasSize(equalTo(1)))
                .body("result.content.code.flatten()",hasItems(
                        equalTo("ID")
                ));
    }

    @Test
    @Order(value = 10)
    public void getListCountryFailed8(){
        String requestBody = """
                {
                      "requestType": "LIST",
                      "size": 10,
                      "page": 1,
                      "sortBy": {
                        "active":"DESC"
                      },
                      "filterBy": {
                        "active":["PHOENIX"]
                      }
               }
                """;


        Long number1 = 4000080010592L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        headers.put("X-FLOW-ID", "004:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","FALSE");

        callRestAssuredValidatable(headers,requestBody,"/mitra/country/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo("Failed mismatch in filtering field : active"))
                .body("result.content",hasSize(equalTo(0)));
    }

}
