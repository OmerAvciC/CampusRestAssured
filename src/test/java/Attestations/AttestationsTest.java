package Attestations;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class AttestationsTest {

    Attestations attestations = new Attestations();

    Cookies cookies;

    String name;

    String attesID;

    @BeforeClass
    void Login(){

       baseURI = "https://demo.mersys.io/";

       attestations.setUsername("richfield.edu");
       attestations.setPassword("Richfield2020!");
       attestations.setRememberMe("true");

       cookies =
               given()
                       .contentType(ContentType.JSON)
                       .body(attestations)
                       .log().uri()
                       .log().cookies()
                       .log().body()

                       .when()
                       .post("auth/login")

                       .then()
                       .log().cookies()
                       .statusCode(200)
                       .extract().response().getDetailedCookies()
               ;

    }

    @Test
    public void createAttestation(){

        name = attestations.RandomString();


        attesID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
//                        .log().all()
                        .log().cookies()
                        .body("{ \"id\": null, \"name\": \""+name+"\",\"translateName\": []}")

                        .log().body()
                        .log().uri()

                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .log().cookies()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().jsonPath().getString("id")

                ;
        System.out.println("attesID = " + attesID);
    }





    @Test(dependsOnMethods = "createAttestation")
    public void updateAttestations(){

        name = attestations.RandomString();

        Attes02 attes02 = new Attes02();
        attes02.setId(attesID);
        attes02.setName(name);

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
//                        .log().all()
                        .body(attes02)

                        .log().body()
                        .log().uri()

                        .when()
                        .put("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)


        ;

    }

    @Test(dependsOnMethods = "updateAttestations")
    public void deleteAttestations(){


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("attesID",attesID) //Id yi aldım sadece
                .when()
                .delete("school-service/api/attestation/{attesID}")

                .then()
                .log().body()
                .log().cookies()
                .statusCode(204)

        ;
    }

//    @Test(dependsOnMethods = "deleteAttestations")
//    public void negativeUpdate(){
//
//        Attes02 attes02 = new Attes02();
//        attes02.setId(attesID);
//        attes02.setName(name);
//
//        given()
//                .cookies(cookies)
//                .contentType(ContentType.JSON)
////                        .log().all()
//                .body(attes02)
//
//                .log().body()
//                .log().uri()
//
//                .when()
//                .put("school-service/api/attestation")
//
//                .then()
//                .log().body()
//                .statusCode(400)
//
//                ;
//
//    }

}
