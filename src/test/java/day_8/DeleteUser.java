package day_8;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteUser {


    @Test
    public void test_deleteUser(ITestContext iTestContext) {

        //int id= (Integer) iTestContext.getAttribute("user_id"); //id will be only in test level
        int id = (Integer) iTestContext.getSuite().getAttribute("user_id"); //id will be in suite level in suite.xml file
        //which means in suite xml there are many test suites and id will be seen by all of them.
        // Otherwise without getSuite() (just with iTestContext.getAttribute("user_id")) tests will fail

        String bearerToken = "d88a9820015da1a01cb5eaa8f2c5351d3363a1cbd9cc36eeee97796d8ee389c9";

        given().header("Authorization", "Bearer " + bearerToken)
                .pathParam("id", id)
                .when()
                .delete("https://gorest.co.in/public/v2/users/{id}")
                .then().statusCode(204).log().body();
    }
}
