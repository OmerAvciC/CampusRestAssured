package GradeLevel;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GradeLevelTest {

    Cookies cookies;

    @BeforeClass
    void Login(){

        baseURI = "https://demo.mersys.io/";

        Map<String,String> credential = new HashMap<>();

        credential.put("username","richfield.edu");
        credential.put("password","Richfield2020!");
        credential.put("rememberMe","true");

        cookies =

                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post( "auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
                ;

    }

    String GradeLevelName;
    String GradeLevelShortName;
    String GradeLevelOrder = "1";
    String GradeLevelID;


    @Test
    public void createGradeLevel(){

        GradeLevelName = GradeLevel.getRandomName();
        GradeLevelShortName = GradeLevel.getRandomShortName();

        GradeLevel gradeLevel = new GradeLevel();
        gradeLevel.setName(GradeLevelName);
        gradeLevel.setShortName(GradeLevelShortName);
        gradeLevel.setOrder(GradeLevelOrder);

        GradeLevelID=
            given()
                    .cookies(cookies)
                    .contentType(ContentType.JSON)
                    .body(gradeLevel)

                    .when()
                    .post("school-service/api/grade-levels")

                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().jsonPath().getString("id")
                ;

    }

    @Test(dependsOnMethods = "createGradeLevel")
    public void updateGradeLevel(){

        GradeLevelName = GradeLevel.getRandomName();
        GradeLevelShortName = GradeLevel.getRandomShortName();


        GradeLevel gradeLevel = new GradeLevel();
        gradeLevel.setId(GradeLevelID);
        gradeLevel.setName(GradeLevelName);
        gradeLevel.setShortName(GradeLevelShortName);
        gradeLevel.setOrder(GradeLevelOrder);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gradeLevel)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(GradeLevelName))

                ;

    }

    @Test
    public void deleteGradeLevel(){

        given()
                .cookies(cookies)
                .pathParam("GradeLevelID",GradeLevelID)

                .when()
                .delete("school-service/api/grade-levels/{GradeLevelID}")

                .then()
                .log().body()
                .statusCode(500)

                ;
    }
}
