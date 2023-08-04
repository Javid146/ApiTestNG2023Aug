package day_4;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ParsingJsonResponseData {

    Response res;
    JSONObject jo;

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "http://localhost:3000"; //on ubuntu CMD: json-server cucular.json
        res = given().contentType(ContentType.JSON)
                .when().get("/store");

        jo = new JSONObject(res.asString());  //convert response to json object type
    }

    @Test(priority = 1)
    public void testJsonResponse() {

//        Assert.assertEquals(res.getStatusCode(),200);
//        Assert.assertEquals(res.header("Content-Type"),"application/json; charset=utf-8");
//        System.out.println("res.header() = " + res.header("Content-Type"));
//        System.out.println("res.getHeader() = " + res.getHeader("Content-Type"));

        //use json finder website to get correct json path from json file
        String title = res.jsonPath().get("books[0].title");
        Assert.assertEquals(title, "Sayings of the Century");

//        System.out.println("res.asString() = " + res.asString());

        boolean status = false;

        for (int i = 0; i < jo.getJSONArray("books").length(); i++) {
            String bookTitle = jo.getJSONArray("books").getJSONObject(i).get("title").toString();
            if (bookTitle.equals("Sayings of the Century")) {
                status = true;
                break;
            }
        }
        Assert.assertTrue(status);
    }


    @Test
    public void testJsonResponsePrice() {
        int totalPrice = 326;
        double expected = 0;

        for (int i = 0; i < jo.getJSONArray("books").length(); i++) {
            String price = jo.getJSONArray("books").getJSONObject(i).get("price").toString();
            expected += Double.parseDouble(price);
        }
        Assert.assertEquals(totalPrice, expected);


    }


}

