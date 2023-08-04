package day_1;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class HttpRequests {
    int id;


    @BeforeMethod
    public void setup() {
        baseURI = "https://reqres.in";
    }


    @Test(priority = 1)
    public void getUsers() {
        given()
                .when()
                .get("api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }


    @Test(priority = 2)
    public void createUser() {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "javid");
        data.put("position", "SDET");

        id = given()
                .contentType("application/json")
                .body(data)
                .when()
                .post("api/users")
                .jsonPath().getInt("id");
    }


    @Test(priority = 3, dependsOnMethods = {"createUser"})
    public void updateUser() {
        HashMap<String, String> data = new HashMap<>();
        data.put("name", "cucu");
        data.put("position", "tax");

        given()
                .contentType("application/json")
                .body(data)
                .when()
                .put("api/users/" + id)
                .then()
                .statusCode(200).log().all();
    }

    @Test(priority = 4)
    public void deleteUser() {
        given()
                .when()
                .delete("/api/users/" + id)
                .then()
                .statusCode(204)
                .log().all();
    }
}
