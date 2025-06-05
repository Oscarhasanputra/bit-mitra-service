package com.bit.microservices.mitra.ms_bank;

import com.bit.microservices.mitra.config.AbstractMitraTest;
import com.bit.microservices.mitra.config.TestContainerConfiguration;
import com.bit.microservices.mitra.model.entity.MsBank;
import com.bit.microservices.mitra.model.entity.QMsBank;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.redis.MsBankRedisRepository;
import com.bit.microservices.mitra.repository.MsBankRepository;
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
import java.util.function.Predicate;

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
public class MsBankScenarioTest extends AbstractMitraTest {

    @Autowired
    private PostgreSQLContainer<?> postgresContainer;

    @Autowired
    private RedisContainer redisContainer;

    @Autowired
    private MsBankRepository msBankRepository;

    @Autowired
    private MsBankRedisRepository msBankRedisRepository;


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
        assertNotNull(msBankRepository);
        assertNotNull(msBankRedisRepository);
    }

    @Test
    @Order(value = 1)
    public void testBankA(){
        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4060080040197L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 2)
    public void testBankB(){
        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4060080040197L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 3)
    public void testBankC(){
        String requestBody = """
                [
                        {
                                "id": "BCA~uuid",
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4060080040297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 4)
    public void testBankD(){
        String requestBody = """
                [
                        {
                                "id": "BCA~uuid",
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                                
                """;


        Long number = 4060080040297L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/update",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 5)
    public void testBankE(){
        String requestBody = """
                [
                        {
                                "id": "BCA~uuid",
                                "deletedReason": "bank tidak jadi dibuat"
                        }
                ]
                                
                """;


        Long number = 4060080040397L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/delete",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 6)
    public void testBankF(){
        String requestBody = """
                [
                        {
                                "id": "BCA~uuid",
                                "deletedReason": "bank tidak jadi dibuat"
                        }
                ]
                """;


        Long number = 4060080040397L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/delete",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 7)
    public void testBankG(){
        String requestBody = """
                {
                                "id": "SERANG~uuid"
                 }      
                """;


        Long number = 4060080040497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 8)
    public void testBankH(){
        String requestBody = """
                 {
                                "id": "SERANG~uuid"
                  }
                """;


        Long number = 4060080040497L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 9)
    public void testBankI(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                } 
                """;


        Long number = 4060080040597L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get-list",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-FLOW-ID"))
                .body("responseCode",equalTo(number));
    }
    @Test
    @Order(value = 10)
    public void testBankJ(){
        String requestBody = """
                {
                        "requestType": "LIST",
                        "size": 15,
                        "page": 1,
                        "sortBy": {},
                        "filterBy": {}
                }
                """;


        Long number = 4060080040597L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get-list",406)
                .body("status",equalTo(status))
                .body("responseMessage",equalTo("Failed missing required header : X-VALIDATE-ONLY"))
                .body("responseCode",equalTo(number));
    }

    @Test
    @Order(value = 11)
    public void createBankSuccess1(){
        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 2000080040100L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }
    @Test
    @Order(value = 13)
    public void createBankFailed2(){
        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4090080040105L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data already exists")));

    }

    @Test
    @Order(value = 14)
    public void createBankFailed3(){
        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "MANDIRI",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "PERMATA",
                                "name": "PERMATA",
                                "swiftCode": "BBBAIDJA",
                                "biCode": "013",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number = 4090080040105L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data already exists")));

    }
    @Test
    @Order(value = 15)
    public void createBankFailed4(){
        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "MANDIRI",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "008",
                                "zipCode": "008",
                                "designPathFile": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "PERMATA",
                                "name": "PERMATA",
                                "swiftCode": "BBBAIDJA",
                                "biCode": "013",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 4040080040107L;
        Long number2 = 4040080040107L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("MANDIRI")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(number1),
                        equalTo(number2)
                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed unexpected field detected: zipCode"),
                        equalTo("Failed unexpected field detected: designPathFile")
                ));

    }

    @Test
    @Order(value = 16)
    public void createBankFailed5(){
        String requestBody = """
                [
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "MANDIRI",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "PERMATA",
                                "name": "PERMATA",
                                "swiftCode": "BBBAIDJA",
                                "biCode": "013",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 4060080040199L;
        Long number2 = 4060080040199L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BII"),
                        containsString("MANDIRI")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(number1),
                        equalTo(number2)
                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed custom : Swift Code Already Exists In Other Code"),
                        equalTo("Failed custom : BI Code Already Exists In Other Code")
                ));

    }
    @Test
    @Order(value = 17)
    public void createBankFailed6(){
        String requestBody = """
                [
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "BII",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "PERMATA",
                                "name": "PERMATA",
                                "swiftCode": "BBBAIDJA",
                                "biCode": "013",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 4000080040184L;
//        Long number2 = 4000080040199L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BII")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed concurrency detected for this request")
                ));

    }

    @Test
    @Order(value = 18)
    public void createBankSuccess7(){
        String requestBody = """
                [
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "MANDIRI",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "PERMATA",
                                "name": "PERMATA",
                                "swiftCode": "BBBAIDJA",
                                "biCode": "013",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 2000080040100L;
//        Long number2 = 4000080040199L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result",hasSize(equalTo(3)))
                .body("result.id",hasItems(
                        containsString("BII")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }
    @Test
    @Order(value = 19)
    public void createBankFailed8(){
        String requestBody = """
                [
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "MANDIRI",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "PERMATA",
                                "name": "PERMATA",
                                "swiftCode": "BBBAIDJA",
                                "biCode": "013",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 4090080040105L;
//        Long number2 = 4000080040199L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BII")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data already exists")));

    }

    @Test
    @Order(value = 20)
    public void deleteBankSuccess9(){
        MsBank msBank = this.msBankRepository.findByCodeAndIsDeleted("BCA",false).orElse(null);
        if(Objects.isNull(msBank)){
            assertTrue(false);
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "deletedReason": "bank tidak jadi dibuat"
                        }
                ]
                                
                """;


        String requestBodyFormatted = requestBody.formatted(msBank.getId());

        Long number1 = 2000080040300L;
//        Long number2 = 4000080040199L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/delete",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo(msBank.getId())
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }
    @Test
    @Order(value = 21)
    public void deleteBankSuccess10(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BII","MANDIRI");
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "deletedReason": "bank tidak jadi dibuat"
                        },
                        {
                                "id": "%s",
                                "deletedReason": "bank tidak jadi dibuat"
                        }
                ]          
                """;


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 2000080040300L;
//        Long number2 = 4000080040199L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/delete",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(idBanks.toArray()))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }

    @Test
    @Order(value = 22)
    public void deleteBankFailed11(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA","PERMATA");
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "deletedReason": "bank tidak jadi dibuat"
                        },
                        {
                                "id": "%s",
                                "deletedReason": "bank tidak jadi dibuat"
                        }
                ]          
                """;


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 4040080040304L;
//        Long number2 = 4000080040199L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/delete",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));

    }
    @Test
    @Order(value = 23)
    public void updateBankFailed12(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA");
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        String requestBodyFormatted =requestBody.formatted(idBanks.toArray());

        Long number1 = 4040080040204L;
//        Long number2 = 4000080040199L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/update",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));

    }

    @Test
    @Order(value = 24)
    public void createBankSuccess13(){

        String requestBody = """
                [
                        {
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                                
                """;


        Long number1 = 2000080040100L;
//        Long number2 = 4000080040199L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }

    @Test
    @Order(value = 25)
    public void updateBankSuccess14(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA","PERMATA").and(QMsBank.msBank.isDeleted.eq(false));
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> dataList = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            dataList.add(msBank.getId());
            dataList.add(msBank.getCode());
            dataList.add(msBank.getName());
            dataList.add(msBank.getSwiftCode());
            dataList.add(msBank.getBiCode());
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "code": "%s",
                                "name": "%s",
                                "swiftCode": "%s",
                                "biCode": "%s",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "id": "%s",
                                "code": "%s",
                                "name": "%s",
                                "swiftCode": "%s",
                                "biCode": "%s",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        String requestBodyFormatted = requestBody.formatted(dataList.toArray());

        Long number1 = 2000080040200L;
//        Long number2 = 4000080040199L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/update",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA"),
                        containsString("PERMATA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }

    @Test
    @Order(value = 26)
    public void updateBankFailed15(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA","BII");
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "code": "BCA",
                                "name": "BCA",
                                "swiftCode": "CENAIDJAXXX",
                                "biCode": "014",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "id": "%s",
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 4040080040204L;
//        Long number2 = 4000080040199L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/update",400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA"),
                        containsString("BII")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data not found")));

    }


    @Test
    @Order(value = 26)
    public void createBankSuccess16(){

        String requestBody = """
                [
                        {
                                "code": "BII",
                                "name": "BII",
                                "swiftCode": "IBBKIDAJ",
                                "biCode": "016",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        },
                        {
                                "code": "MANDIRI",
                                "name": "MANDIRI",
                                "swiftCode": "BMRIIDJA",
                                "biCode": "008",
                                "country": "INDONESIA",
                                "active": true,
                                "remarks": ""
                        }
                ]
                """;


        Long number1 = 2000080040100L;
//        Long number2 = 4000080040199L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/create",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BII"),
                        containsString("MANDIRI")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }

    @Test
    @Order(value = 27)
    public void activateBankSuccess17(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA").and(QMsBank.msBank.isDeleted.eq(false));
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

        String requestBody = """
                [
                        {
                                "id": "%s",
                                "active": false
                        }
                ]
                               
                """;


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 2000080040600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }

    @Test
    @Order(value = 28)
    public void activateBankSuccess18(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA","BII").and(QMsBank.msBank.isDeleted.eq(false));
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

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


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 2000080040600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA"),
                        containsString("BII")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }

    @Test
    @Order(value = 29)
    public void activateBankSuccess19(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA","BII").and(QMsBank.msBank.isDeleted.eq(false));
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

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


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 2000080040600L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/activate",200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        containsString("BCA"),
                        containsString("BII")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number1)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }


    @Test
    @Order(value = 30)
    public void getBankSuccess20(){
        BooleanExpression predicate = QMsBank.msBank.code.in("BCA").and(QMsBank.msBank.isDeleted.eq(false));
        Iterator<MsBank> msBankList = this.msBankRepository.findAll(predicate).iterator();
        List<String> idBanks = new ArrayList<>();
        while(msBankList.hasNext()){
            MsBank msBank = msBankList.next();
            idBanks.add(msBank.getId());
        }

        String requestBody = """
                        {
                                "id": "%s"
                        }
                """;


        String requestBodyFormatted = requestBody.formatted(idBanks.toArray());

        Long number1 = 2000080040400L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");


        callRestAssuredValidatable(headers,requestBodyFormatted,"/mitra/bank/v1/0/get",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("responseMessage",equalTo(status));


        MsBankViewDTO msBankViewDTO =  msBankRedisRepository.load(idBanks.get(0));
        assertNotNull(msBankViewDTO);

    }

    @Test
    @Order(value = 31)
    public void getBankFailed21(){

        String requestBody = """
                        {
                                "id": "SERANG~uuid"
                        }
                               
                                
                """;



        Long number1 = 4040080040404L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))

                .body("responseMessage",equalTo("Failed data not found"));

    }

    @Test
    @Order(value = 32)
    public void getListBankSuccess22(){

        String requestBody = """
                    {
                            "requestType": "LIST",
                            "size": 15,
                            "page": 1,
                            "sortBy": {},
                            "filterBy": {}
                    }
                               
                                
                """;



        Long number1 = 2000080040500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("result.content",hasSize(equalTo(7)))
                .body("responseMessage",equalTo(status));

    }

    @Test
    @Order(value = 33)
    public void getListBankSuccess23(){

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
                                           "BCA",
                                           "BII"
                                   ],
                                   "name": [
                                           "BCA"
                                   ],
                                   "active": true,
                                   "deleted": false
                           }
                   }    
                """;



        Long number1 = 2000080040500L;
        String status = "Success";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get-list",200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("result.content",hasSize(equalTo(1)))
                .body("responseMessage",equalTo(status));

    }

    @Test
    @Order(value = 34)
    public void getListBankFailed24(){

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
                                           "BCA",
                                           "BII"
                                   ],
                                   "name": [
                                           "BCA"
                                   ],
                                   "active": true,
                                   "deleted": false
                           }
                   }    
                """;



        Long number1 = 4000080040518L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("result.content",hasSize(equalTo(0)))
                .body("responseMessage",equalTo("Failed filtering by field is not allowed: createdBy"));

    }
    @Test
    @Order(value = 35)
    public void getListBankFailed25(){

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
                                           "BCA",
                                           "BII"
                                   ],
                                   "name": true,
                                   "active": true,
                                   "deleted": false
                           }
                   }    
                """;



        Long number1 = 4000080040592L;
        String status = "Failed";
        Map<String, String> headers = new HashMap<>();

        headers.put(MOCK_TOKEN,MOCK_TOKEN_VALUE);
        headers.put("X-FLOW-ID","008:004:1:20250120143045100");
        headers.put("X-VALIDATE-ONLY","false");

        callRestAssuredValidatable(headers,requestBody,"/mitra/bank/v1/0/get-list",400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number1))
                .body("result.content",hasSize(equalTo(0)))
                .body("responseMessage",equalTo("Failed mismatch in filtering field : name"));

    }
}
