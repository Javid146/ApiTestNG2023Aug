package day_6;

import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class JSONSchemaValidation {
    /* for jsonschema *.json FILE YARAT (DATA.JSON). FILE ICINDE JSON DATA ELAVE ET VE https://jsonformatter.org/json-to-jsonschema SAYTDA O DATANIN SCHEMASINI CIXART
     > SONRA data.json AT FRAMEWORK-A resources foldera> UBUNTU CMD-DE json-server data.json VE WINDOWN ACIQ SAXLA*/
    @BeforeMethod
    public void setup() {
        baseURI = "http://localhost:3000";
    }

    //http://restapi.adequateshop.com/api/Traveler

    @Test
    void jsonSchemaValidation() {
        given().when().get("/store")
                .then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("JsonSchema.json"));
    }
}
