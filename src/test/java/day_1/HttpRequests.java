package day_1;

import com.relevantcodes.extentreports.LogStatus;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HttpRequests extends ExtentReport{

//    private static final Logger logger = LogManager.getLogger(HttpRequests.class);


    int id;

    @BeforeMethod
    public void setup() {
        baseURI = "https://reqres.in";
        test = extentReports.startTest("Get HttpRequests Test");
        requestWriter = new StringWriter();
        requestCapture = new PrintStream(new WriterOutputStream(requestWriter));
    }


    @Test(priority = 1)
    public void getUsers() {
       Response response = given().filter(new RequestLoggingFilter(requestCapture))
                .when()
                .get("api/users?page=2");

                response.then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().body();

        System.out.println();
        System.out.println();
        System.out.println("LOGGER------------------------------------------");

//        logger.debug("Request and response details: " + response.getBody());

    }


    @Test(priority = 2)
    public void createUser() {

        HashMap<String, String> data = new HashMap<>();
        data.put("name", "javid");
        data.put("position", "SDET");

        id = given().filter(new RequestLoggingFilter(requestCapture))
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

        given().filter(new RequestLoggingFilter(requestCapture))
                .contentType("application/json")
                .body(data)
                .when()
                .put("api/users/" + id)
                .then()
                .statusCode(200).log().all();
    }

    @Test(priority = 4)
    public void deleteUser() {
        given().filter(new RequestLoggingFilter(requestCapture))
                .when()
                .delete("/api/users/" + id)
                .then()
                .statusCode(204)
                .log().all();
    }

    @AfterMethod
    public void tearDown() {
        requestCapture.flush();
        System.out.println("Request: "+requestWriter.toString());
        test.log(LogStatus.INFO, "Request : "+ requestWriter.toString());
        extentReports.endTest(test);
    }
}
