package day_7;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/*basic, digest and preemptive auth are similar. all need username/password.
 * bearer token, auth 1.0/2.0, api key are different*/
public class Authentications {


    @Test
    void testBasicAuth() {
        given().auth().basic("postman", "password")
                .when().get("http://postman-echo.com/basic-auth")
                .then().assertThat().statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();
    }

    @Test
    void testDigestAuth() {
        given().auth().digest("postman", "password")
                .when().get("http://postman-echo.com/basic-auth")
                .then().assertThat().statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();
    }

    @Test
    void testPreemptiveAuth() {
        given().auth().preemptive().basic("postman", "password")
                .when().get("http://postman-echo.com/basic-auth")
                .then().assertThat().statusCode(200)
                .body("authenticated", equalTo(true))
                .log().all();
    }

    //go to github generate token from settings and go to postman > Authorization > bearer token > add new token there
    @Test
    void testBearerTokenAuth() {

        String bearerToken = "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"; //token from github

        given().header("Authorization", "Bearer " + bearerToken)
                .when().get("http://api.github.com/user/repos")
                .then().statusCode(200)
                .log().all();
    }

    //developer gives instructions about oauth1
    @Test
    void testOauth1Auth() {
        //in postman variables below ("consumerKey","consumerSecret","accessToken","tokenSecret") should be in params not in headers
        given().auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret")
                .when().get("http://api.github.com/user/repos")
                .then().statusCode(200)
                .log().all();
    }

    @Test
    void testOauth2Auth() {
        //in postman variables below ("consumerKey","consumerSecret","accessToken","tokenSecret") should be in params not in headers
        given().auth().oauth2("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") //token from github, but to generate oauth2 you need to do some actions that I don't know
                .when().get("http://api.github.com/user/repos")
                .then().statusCode(200)
                .log().all();
    }

    @Test
    void testApiKeyAuth() {
        //api key is usually put in query param. we need to check api documentation for that each time, it might be in header too.
//        given()
//                .queryParam("appid","624097c3e04fa67f0cadccd07e5f29ef") //appid is api key
//                .when().get("http://api.openweathermap.org/data/2.5/forecast?q=Baku")
//                .then().statusCode(200)
//                .log().body();
//    }

        //SECOND WAY
        given().pathParam("path", "data/2.5/forecast")
                .queryParam("q", "Baku")
                .queryParam("appid", "624097c3e04fa67f0cadccd07e5f29ef") //appid is api key
                .when().get("http://api.openweathermap.org/{path}")
                .then().statusCode(200)
                .log().body();
    }
}
