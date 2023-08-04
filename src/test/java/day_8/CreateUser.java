package day_8;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateUser {


    @Test
    public void test_createUser(ITestContext iTestContext) {
        //iTestContext is like a global variable you can refer in other classes
        //when there iTestContext, it means classes are chained, so we can't run CreateUser, GetUser, UpdateUser, DeleteUser
        //separately, only via xml file. right-click on folder (day_8) > create TestNG XML > run tests from that testng.xml

        int id;

        Faker faker = new Faker();
        JSONObject data = new JSONObject();

        data.put("name", faker.name().fullName());
        data.put("gender", "Male");
        data.put("email", faker.internet().emailAddress());
        data.put("status", "inactive");

        String bearerToken = "d88a9820015da1a01cb5eaa8f2c5351d3363a1cbd9cc36eeee97796d8ee389c9";

        Response response = given().baseUri("https://gorest.co.in/").body(data.toString()).header("Authorization", "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .when().post("public/v2/users");

        id = response.jsonPath().getInt("id");

        System.out.println(id);

        //iTestContext is like a global variable you can refer while running testng xml files
        iTestContext.getSuite().setAttribute("user_id", id); //for suite level
//        iTestContext.setAttribute("user_id", id); //for test level
    }
}
