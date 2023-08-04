package day_3;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class CookiesDemo {

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "https://www.google.com/";
    }


    @Test(priority = 1)
    public void testCookie() {
        given()
                .when()
                .get()
                .then()
                .cookie("NID", "511%3DSxsErUPTnrSpyfS6GTmxFBc1BF1vNZRSkSaOzJWbIpp40d6sWhkHmzEHPq_0PgFXpQFQakq1eHia0B5t4pjyX5BwU6Ke2OJQTarNRMV-KzPNPAjLC_-ER6w_v7WlzR4R_mMGYeY1mYi_O5o0oXJXcSC0hbVDiu6HaJfdIKhFaVI")
                .log().all();
    }


    @Test(priority = 2)
    public void getCookieInfo() {
        Response rs = given()
                .when()
                .get();

        Map<String, String> cookies_values = rs.getCookies();

        for (String k : cookies_values.keySet()) {
            String cooke_value = rs.getCookie(k);
            System.out.println(k + ": " + cooke_value);
        }
    }
}
