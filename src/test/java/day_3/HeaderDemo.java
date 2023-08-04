package day_3;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeaderDemo {

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://www.google.com/";
    }


    @Test(priority = 1)
    public void testHeaders() {
        given()
                .when()
                .get()
                .then()
                .header("content-type", "text/html; charset=ISO-8859-1")
                .and()
                .header("content-encoding", "gzip")
                .log().all();
    }


    @Test(priority = 2)
    public void getHeaders() {
        Response res = given().when().get();

        String headerValue = res.getHeader("Content-Type");
        System.out.println("headerValue = " + headerValue);

        Headers headers = res.getHeaders();

        System.out.println("=====HEADERS=====");
        for (Header hd : headers) {
            System.out.println(hd.getName() + ":  " + hd.getValue());
        }
    }
}
