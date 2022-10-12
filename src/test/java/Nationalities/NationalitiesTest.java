package Nationalities;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class NationalitiesTest {

    Nationalities nationalities = new Nationalities();

    Cookies cookies;

    String name;

    String nationalitiesID;


    @BeforeClass
    void Login(){

        baseURI = "https://demo.mersys.io/";

        nationalities.setUsername("richfield.edu");
        nationalities.setPassword("Richfield2020!");
        nationalities.setRememberMe("true");

        cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(nationalities)
                        .log().uri()
                        .log().body()
                        .log().cookies()

                        .when()
                        .post("auth/login")

                        .then()
                        .log().cookies()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
                ;

    }

    @Test(priority = 1)
    public void createNationalities(){

        name = nationalities.getRandomName();

        nationalitiesID =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .log().all()
                        .log().cookies()
                        .body("{ \"id\": null, \"name\": \""+name+"\",\"translateName\": []}")
                        .log().body()
                        .log().uri()

                        .when()
                        .post("school-service/api/nationality")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().jsonPath().getString("id")
                ;
        System.out.println("nationalitiesID = " + nationalitiesID);

    }

    @Test(priority = 2)
    public void updateNationalities(){

        name = nationalities.getRandomName();

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body("{\"id\": \"" + nationalitiesID + "\", \"name\": \""  + name + "\",\"translateName\": []}")
                .log().body()
                .log().uri()

                .when()
                .put("school-service/api/nationality")

                .then()
                .log().body()
                .log().cookies()
                .statusCode(200)
                .contentType(ContentType.JSON)

                ;

    }

    @Test(priority = 3)
    public void negativeUpdate(){

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(name)
                .when()
                .post("school-service/api/nationality")

                .then()
                .log().body()
                .statusCode(400)
                .contentType(ContentType.JSON)
                ;
    }

    @Test(priority = 4)
    public void deleteNationalities(){

        given()

                .cookies(cookies)
                .pathParam("nationalitiesID",nationalitiesID)

                .when()
                .delete("school-service/api/nationality/{nationalitiesID}")

                .then()
                .log().body()
                .statusCode(200)
                ;

    }


}
