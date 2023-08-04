package day_2;

import io.restassured.response.Response;
import org.json.JSONTokener;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class WaysOfPostRequest {

    int id;

    /*first create json-server data.json from your ubuntu CMD. Then connect to http://localhost:3000  */
    @BeforeMethod
    public void setup() {
        baseURI = "http://localhost:3000";
    }

//    @Test(priority = 1)
//    public void testPostWithHasmap(){
//
//        String courseArr[] = {"C","C++"};
//
//        HashMap data = new HashMap();
//        data.put("name","Javid");
//        data.put("location","France");
//        data.put("phone", "123456");
//        data.put("courses", courseArr);
//
//
//        given()
//                .contentType("application/json")
//                .body(data)
//        .when()
//                .post("/students")
//        .then()
//                .statusCode(201)
//                .body("name",equalTo("Javid"))
//                .body("location",equalTo("France"))
//                .body("phone", equalTo("123456"))
//                .body("courses[0]",equalTo("C"))
//                .body("courses[1]",equalTo("C++"))
//                .header("Content-Type","application/json; charset=utf-8")
//                .log().all();
//    }


    @Test(priority = 2)
    public void testDelete() {

        given()
                .when()
                .delete("students/" + id)
                .then()
                .statusCode(200)
                .log().all();
    }


    @Test(priority = 1)
    public void testPostWithJsonLibrary() {

        String[] courseArr = {"C", "C++"};

        JSONObject data = new JSONObject();
        data.put("name", "Javid");
        data.put("location", "France");
        data.put("phone", "123456");
        data.put("courses", courseArr);


        Response response = given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("students");

        response.
                then()
                .statusCode(201)
                .body("name", equalTo("Javid"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("123456"))
                .body("courses[0]", equalTo("C"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();

        id = response.jsonPath().getInt("id");
        System.out.println("id = " + id);
    }


//    @Test(priority = 1)
//    public void testPostUsingPojo(){
//
//        String courseArr[] = {"JS","React"};
//
//        Pojo_PostRequest pojo_postRequest = new Pojo_PostRequest("Nicat","Baku", "2109628", courseArr);
//
//        given()
//                .contentType("application/json")
//                .body(pojo_postRequest)
//        .when()
//                .post("/students")
//        .then()
//                .statusCode(201)
//                .body("name",equalTo("Nicat"))
//                .body("location",equalTo("Baku"))
//                .body("phone", equalTo("2109628"))
//                .body("courses[0]",equalTo("JS"))
//                .body("courses[1]",equalTo("React"))
//                .header("Content-Type","application/json; charset=utf-8")
//                .log().all();
//    }


    @Test(priority = 1)
    public void testPostUsingExternalJson() throws FileNotFoundException {

        FileReader fileReader = new FileReader(new File("data.json"));
        JSONTokener jt = new JSONTokener(fileReader);
        JSONObject data = new JSONObject(jt);

        given()
                .contentType("application/json")
                .body(data.toString())
                .when()
                .post("/students")
                .then()
                .statusCode(201)
                .body("name", equalTo("Javid"))
                .body("location", equalTo("Azerbaijan"))
                .body("phone", equalTo("2404909597"))
                .body("courses[0]", equalTo("Python"))
                .body("courses[1]", equalTo("Appium"))
                .header("Content-Type", "application/json; charset=utf-8")
                .log().all();
    }
}
