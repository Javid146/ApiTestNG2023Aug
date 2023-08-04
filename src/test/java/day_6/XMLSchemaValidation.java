package day_6;

import io.restassured.matcher.RestAssuredMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class XMLSchemaValidation {
    /* for xmlschema or xsd  *.xsd FILE YARAT (traveler.xsd). FILE ICINDE https://www.liquid-technologies.com/online-xml-to-xsd-converter sayt istifade ederek xml
    datani XMLSCHEMAYA CEVIR > SONRA data.json AT FRAMEWORK-A resources foldera*/
    @BeforeMethod
    public void setup() {
        baseURI = "http://restapi.adequateshop.com/api";
    }


    @Test
    void jsonSchemaValidation() {
        given().when().get("/Traveler")
                .then().assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("traveler.xsd"));
    }
}
