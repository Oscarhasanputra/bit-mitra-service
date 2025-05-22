package com.bit.microservices.mitra.ConfigAutoNumber;

import com.bit.microservices.mitra.AbstractConnectionContainer;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
@SpringBootTest(
        properties = {"spring.main.web-application-type=reactive", "spring.config.location=classpath:bootstrap.yml"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class ConfigAutoNumberCrudScenarioTest extends AbstractConnectionContainer {

    @Test
    public void testScenario(){

        try {
            testScenario0CreateConfigAutoNumberSuccess();
            testScenario1CreateConfigAutoNumberFailed();
            testScenario2CreateConfigAutoNumberFailed();
            testScenario3CreateConfigAutoNumberFailed();
            testScenario4CreateConfigAutoNumberSuccess();
            testScenario5CreateConfigAutoNumberFailed();
            testScenario6DeleteConfigAutoNumberSuccess();
            testScenario7DeleteConfigAutoNumberSuccess();
            testScenario8DeleteConfigAutoNumberFailed();
            testScenario9UpdateConfigAutoNumberFailed();
            testScenario10CreateConfigAutoNumberFailed();
            testScenario11UpdateConfigAutoNumberFailed();
            testScenario12UpdateConfigAutoNumberFailed();
            testScenario13CreateConfigAutoNumberFailed();
            testScenario14GetConfigAutoNumberSuccess();
            testScenario15GetConfigAutoNumberSuccess();
        }catch (Exception err){
            assert(false);
        }
    }


    public void testScenario0CreateConfigAutoNumberSuccess() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                                
                """;



        Long number = 2000060010100L;
        String status = "Success";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo(status)));

    }


    public void testScenario1CreateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                """;



        Long number = 4090060010105L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(equalTo(number)))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(equalTo("Failed data already exists")));

    }

    public void testScenario2CreateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF002",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranchh": false,
                                "branchId": "BTM~uuid",
                                "branchCode": "BTM",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF003",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BDG~uuid",
                                "branchCode": "BDG",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF004",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "CRB~uuid",
                                "branchCode": "CRB",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                """;



        Long number = 4090060010105L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF002")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(4040060010107L),
                        equalTo(4220060010108L)
                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed unexpected field detected: allBranchh"),
                        equalTo("Failed field cannot be NULL: allBranch")
                ));

    }


    public void testScenario3CreateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF002",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranchh": false,
                                "branchId": "BTM~uuid",
                                "branchCode": "BTM",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF003",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BDG~uuid",
                                "branchCode": "BDG",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF004",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "CRB~uuid",
                                "branchCode": "CRB",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                """;



        Long number = 4090060010105L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF002")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(4040060010107L),
                        equalTo(4220060010108L)
                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed unexpected field detected: allBranchh"),
                        equalTo("Failed field cannot be NULL: allBranch")
                ));

    }

    public void testScenario4CreateConfigAutoNumberSuccess() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF002",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BTM~uuid",
                                "branchCode": "BTM",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF003",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BDG~uuid",
                                "branchCode": "BDG",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF004",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "CRB~uuid",
                                "branchCode": "CRB",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                """;



        Long number = 2000060010100L;
        String status = "Success";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF002"),
                        equalTo("COF003"),
                        equalTo("COF004")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo(status)
                ));

    }

    public void testScenario5CreateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF002",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BTM~uuid",
                                "branchCode": "BTM",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF003",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BDG~uuid",
                                "branchCode": "BDG",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF004",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "CRB~uuid",
                                "branchCode": "CRB",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                """;



        Long number = 4090060010105L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF002"),
                        equalTo("COF003"),
                        equalTo("COF004")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo("Failed data already exists")
                ));

    }


    public void testScenario6DeleteConfigAutoNumberSuccess() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "deletedReason": "config cancelled"
                        }
                ]
                               
                """;



        Long number = 2000060010300L;
        String status = "Success";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/delete")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo(status)
                ));

    }

    public void testScenario7DeleteConfigAutoNumberSuccess() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF002",
                                "deletedReason": "config cancelled"
                        },
                        {
                                "id": "COF003",
                                "deletedReason": "config cancelled"
                        }
                ]
                               
                """;



        Long number = 2000060010300L;
        String status = "Success";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/delete")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF002"),

                        equalTo("COF003")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo(status)
                ));

    }

    public void testScenario8DeleteConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "deletedReason": "config cancelled"
                        },
                        {
                                "id": "COF004",
                                "deletedReason": "config cancelled"
                        }
                ]
                                
                               
                """;



        Long number = 4040060010304L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/delete")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo("Failed data not found")
                ));

    }


    public void testScenario9UpdateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                            
                               
                """;



        Long number = 4040060010204L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/update")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo("Failed data not found")
                ));

    }



    public void testScenario10CreateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                      
                """;



        Long number = 4090060010105L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo("Failed data already exists")
                ));

    }

    public void testScenario11UpdateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                       {
                                "id": "COF002",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BTM~uuid",
                                "branchCode": "BTM",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                                
                """;



        Long number = 4040060010204L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/update")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001"),

                        equalTo("COF002")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo("Failed data not found")
                ));

    }


    public void testScenario12UpdateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF001",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "JKT~uuid",
                                "branchCode": "JKT",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF004",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "CRB~uuid",
                                "branchCode": "CRB",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                      
                """;



        Long number = 4040060010204L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/update")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF001"),

                        equalTo("COF004")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",hasItems(
                        equalTo(4040060010204L),
                        equalTo(4090060010215L)

                ))
                .body("result.responseDetail.responseMessage.flatten()",hasItems(
                        equalTo("Failed data not found"),
                        equalTo("Failed detail data already exists : [Config for Event BPB Start Date 2025-04-01T05:27:16]")
                ));

    }


    public void testScenario13CreateConfigAutoNumberFailed() throws Exception {


        String requestBody = """
                [
                        {
                                "id": "COF002",
                                "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BTM~uuid",
                                "branchCode": "BTM",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        },
                        {
                                "id": "COF003",
                                 "startDate": "2025-04-01T05:27:16Z",
                                "endDate": "",
                                "allBranch": false,
                                "branchId": "BDG~uuid",
                                "branchCode": "BDG",
                                "transactionEvent": "BPB",
                                "fieldString1": "BPB",
                                "fieldString2": "",
                                "fieldString3": "",
                                "digitPenomoran": 3,
                                "hasRangeNumber": false,
                                "numberStartFrom": "",
                                "numberEndTo": "",
                                "formatCode": [
                                        "FS1",
                                        "B",
                                        "STR",
                                        "YEAR",
                                        "MONTH"
                                ],
                                "formatAutonumber": [
                                        "FS1",
                                        "/",
                                        "B",
                                        "/",
                                        "STR",
                                        "/",
                                        "YYYYROMAWI",
                                        "/",
                                        "MM",
                                        "/",
                                        "A"
                                ],
                                "remarks": ""
                        }
                ]
                """;



        Long number = 4090060010105L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/create")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("result.id",hasItems(
                        equalTo("COF002"),

                        equalTo("COF003")
                ))
                .body("result.statusDetail",everyItem(equalTo(status)))
                .body("result.responseDetail.responseCode.flatten()",everyItem(
                        equalTo(number)
                ))
                .body("result.responseDetail.responseMessage.flatten()",everyItem(
                        equalTo("Failed data already exists")
                ));

    }

    public void testScenario14GetConfigAutoNumberSuccess() throws Exception {


        String requestBody = """
                {
                  "id": "COF001"
                }
                """;



        Long number = 2000060010400L;
        String status = "Success";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/get")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number))
                .body("result.id",equalTo("COF001"));


    }

    public void testScenario15GetConfigAutoNumberSuccess() throws Exception {


        String requestBody = """
                  {
                               "id": "SERANG~uuid"
                   }
                """;



        Long number = 4040060010404L;
        String status = "Failed";
        RestAssured.given()
                .log().all()
                .contentType("application/json")
                .header(MOCK_TOKEN,MOCK_TOKEN_VALUE)
                .header("X-FLOW-ID","1")
                .body(requestBody)
                .when()
                .post("/autonumber/configautonumber/v1/0/get")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("status",equalTo(status))
                .body("responseCode",equalTo(number));


    }
}


