package day_8;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateUser {

    @Test
    public void test_getUser(ITestContext iTestContext) {

        //int id= (Integer) iTestContext.getAttribute("user_id"); //id will be only in test level
        int id= (Integer) iTestContext.getSuite().getAttribute("user_id"); //id will be in suite level in suite.xml file
        //which means in suite xml there are many test suites and id will be seen by all of them.
        // Otherwise without getSuite() (just with iTestContext.getAttribute("user_id")) tests will fail

        Faker faker = new Faker();
        JSONObject data = new JSONObject();

        data.put("name", faker.name().fullName());
        data.put("gender","female");
        data.put("email",faker.internet().emailAddress());
        data.put("status","active");

        String bearerToken = "d88a9820015da1a01cb5eaa8f2c5351d3363a1cbd9cc36eeee97796d8ee389c9";

        given().header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .body(data.toString())
                .pathParam("id", id)
                .when()
                .put("https://gorest.co.in/public/v2/users/{id}")
                .then().statusCode(200).log().body();
    }
}
