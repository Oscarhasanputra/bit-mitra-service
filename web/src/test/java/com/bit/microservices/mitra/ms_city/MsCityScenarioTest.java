package com.bit.microservices.mitra.ms_city;

import com.bit.microservices.mitra.config.AbstractMitraTest;
import com.bit.microservices.mitra.config.TestContainerConfiguration;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.model.dto.city.CityAPIResponseDTO;
import com.bit.microservices.mitra.model.entity.MsCity;
import com.bit.microservices.mitra.repository.MsCityRepository;
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

import java.util.*;

import static org.hamcrest.Matchers.*;
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
public class MsCityScenarioTest extends AbstractMitraTest {
    @Autowired
    private PostgreSQLContainer<?> postgresContainer;

    @Autowired
    private RedisContainer redisContainer;


    @MockitoBean
    private HttpService httpService;

    @Autowired
    private MsCityRepository msCityRepository;

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
        assertNotNull(msCityRepository);
    }

    @Test
    @Order(value = 0)
    public void testCityA(){
        String requestBody = """
                {
                  "countryId": "ID~uuid"
                }
                """;



        Long number = 4060080021497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_sync-from-google",406)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 1)
    public void testCityB(){
        String requestBody = """
                {
                  "countryId": "ID~uuid"
                }
                """;



        Long number = 4060080021497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_sync-from-google",406)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 2)
    public void testCityC(){
        String requestBody = """
                [
                	{
                		"id": "JAKARTA-BARAT~uuid",
                		"code": "JAKARTA-BARAT",
                		"name": "JAKARTA BARAT",
                		"provinceCode": "JK",
                		"provinceName": "JAKARTA",
                		"active": true,
                		"remarks": ""
                	},
                	{
                		"id": "JAKARTA-TIMUR~uuid",
                		"code": "JAKARTA-TIMUR",
                		"name": "JAKARTA TIMUR",
                		"provinceCode": "JK",
                		"provinceName": "JAKARTA",
                		"active": true,
                		"remarks": ""
                	}
                ]
                """;



        Long number = 4060080022097L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_update_province_code",406)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 3)
    public void testCityD(){
        String requestBody = """
                [
                  {
                    "id": "string",
                    "code": "string",
                    "name": "string",
                    "provinceCode": "string",
                    "provinceName": "string",
                    "active": true,
                    "remarks": "string"
                  }
                ]
                """;



        Long number = 4060080022097L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_update_province_code",406)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 4)
    public void syncCitySuccess1(){
        String requestBody = """
                {
                  "countryId": "ID~uuid"
                }
                """;

        List<CityAPIResponseDTO> cityAPIResponseDTOList = new ArrayList<>();
        CityAPIResponseDTO cityResponseAPI = new CityAPIResponseDTO("JKT","JAKARTA");

        CityAPIResponseDTO cityResponseAPI2 = new CityAPIResponseDTO("SBY","SURABAYA");
        cityAPIResponseDTOList.add(cityResponseAPI);
        cityAPIResponseDTOList.add(cityResponseAPI2);
        Mockito.when( this.httpService.getListCityByCountryCode(Mockito.any(String.class))).thenReturn(Mono.just(cityAPIResponseDTOList));

        Long number = 2000080021400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_sync-from-google",200)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 5)
    public void syncCityFailed2(){
        String requestBody = """
                {
                  "countryId": "ID~uuid"
                }
                """;

        Mockito.when( this.httpService.getListCityByCountryCode(Mockito.any(String.class))).thenReturn(Mono.error(new Exception("Connection Timeout")));

        Long number = 5000080021402L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_sync-from-google",400)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed connection, data disconnected (timeout)"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 6)
    public void syncCitySuccess3(){
        String requestBody = """
                {
                  "countryId": "ID~uuid"
                }
                """;

        List<CityAPIResponseDTO> cityAPIResponseDTOList = new ArrayList<>();
        CityAPIResponseDTO cityResponseAPI = new CityAPIResponseDTO("JKT","JAKARTA");
        cityAPIResponseDTOList.add(cityResponseAPI);
        Mockito.when( this.httpService.getListCityByCountryCode(Mockito.any(String.class))).thenReturn(Mono.just(cityAPIResponseDTOList));

        Long number = 2000080021400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_sync-from-google",200)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number));
    }


    @Test
    @Order(value = 7)
    public void syncCityFailed4(){
        String requestBody = """
                {
                  "countryId": "ID~uuid"
                }
                """;

        Mockito.when( this.httpService.getListCityByCountryCode(Mockito.any(String.class))).thenReturn(Mono.error(new Exception("Connection Timeout")));

        Long number = 5000080021402L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_sync-from-google",400)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed connection, data disconnected (timeout)"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 8)
    public void getListCitySuccess5(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;

        Long number = 2000080020500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/get-list",200)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("result.content",hasSize(equalTo(2)));
    }

    @Test
    @Order(value = 9)
    public void getListCitySuccess6(){
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
                                    "active": true,
                                    "deleted": false
                        }
                }
                """;

        Long number = 2000080020500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/get-list",200)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("result.content",hasSize(equalTo(1)));
    }


    @Test
    @Order(value = 10)
    public void getListCityFailed7(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {
                                    "code": "ASC",
                                    "name": "ASC",
                                    "active": "ASC",
                                    "createdBy": "ASC"
                                  },
                        "filterBy": {
                                    "code": [
                                            "JKT",
                                            "BAKAUHENI"
                                        ],
                                    "name": [
                                            "CILEGON"
                                        ],
                                    "active": false,
                                    "deleted": false
                        }
                }
                """;

        Long number = 4000080020518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/get-list",400)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: createdBy"))
                .body("responseCode",equalTo(number))
                .body("result.content",hasSize(equalTo(0)));
    }

    @Test
    @Order(value = 11)
    public void getListCityFailed8(){
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
                                    "code": [
                                            "JKT",
                                            "BAKAUHENI"
                                        ],
                                    "name": [
                                            "CILEGON"
                                        ],
                                    "active": false,
                                    "deleted": false
                        }
                }
                """;

        Long number = 4000080020518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/get-list",400)
                .body(  "status",equalTo(status))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: zipcode"))
                .body("responseCode",equalTo(number))
                .body("result.content",hasSize(equalTo(0)));
    }

    @Test
    @Order(value = 12)
    public void updateProvinceCodeSuccess9(){
        Iterator< MsCity> msCityIterator = this.msCityRepository.findAll().iterator();

        List<String> paramsList = new ArrayList<>();
        while(msCityIterator.hasNext()){
            MsCity msCity = msCityIterator.next();
            paramsList.add(msCity.getId());
            paramsList.add(msCity.getCode());
            paramsList.add(msCity.getName());
        }
        String requestBody = """
                [
                        {
                                "id": "%s",
                                "code": "%s",
                                "name": "%s",
                                "provinceCode": "JK",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "id": "%s",
                                "code": "%s",
                                "name": "%s",
                                "provinceCode": "JK",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;

        String requestBodyFormatted = requestBody.formatted(paramsList.toArray());
        Long number = 2000080022000L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/city/v1/0/_update_province_code",200)
                .body(  "status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("JAKARTA"),
                        containsString("SURABAYA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));
    }


    @Test
    @Order(value = 13)
    public void updateProvinceCodeFailed10(){

        String requestBody = """
                [
                        {
                                "id": "JAKARTA-BARAT~uuid",
                                "code": "JAKARTA-BARAT",
                                "name": "JAKARTA BARAT",
                                "provinceCode": "JK",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "id": "JAKARTA-TIMUR~uuid",
                                "code": "JAKARTA-TIMUR",
                                "name": "JAKARTA TIMUR",
                                "provinceCode": "JK",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;

        Long number = 4040080022004L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","005:011:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/city/v1/0/_update_province_code",400)
                .body(  "status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("JAKARTA-BARAT"),
                        containsString("JAKARTA-TIMUR")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));
    }


}
