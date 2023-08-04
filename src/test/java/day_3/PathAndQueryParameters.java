package day_3;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PathAndQueryParameters {

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void testQueryAndPathParams() {
        given()
                .pathParam("mypath", "users")
                .queryParam("page", 2)
                .queryParam("id", 2)
                .when()
                .get("/{mypath}")
                .then()
                .statusCode(200)
                .log().all();
    }

    @Test(priority = 1)
    public void testHeaders() {
        given()
                .pathParam("path", "users")
                .queryParam("page", 2)
                .when()
                .get("/{path}")
                .then()
                .log().body();
    }
}
