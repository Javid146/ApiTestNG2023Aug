package day_5;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ParsingXMLResponse {

    Response response;

    @BeforeMethod
    public void setup() {
        RestAssured.baseURI = "http://restapi.adequateshop.com/api";
        response = given().contentType(ContentType.XML).queryParam("page", "1").pathParam("travel", "Traveler").when().get("/{travel}");

    }

    @Test
    public void testXMLResponse() {
        //APPROACH 1
        given().contentType(ContentType.XML).queryParam("page", "1").pathParam("travel", "Traveler")
                .when().get("/{travel}")
                .then().statusCode(200)
                .header("Content-Type", "application/xml; charset=utf-8")
                //dots below are like slash in xpath, in xml dot is used in order to find relation of nodes.
                .body("TravelerinformationResponse.travelers.Travelerinformation[0].email", equalTo("Developer12@gmail.com"));

        //APPROACH 2
        String page = response.xmlPath().get("TravelerinformationResponse.page").toString();
        String email = response.xmlPath().get("TravelerinformationResponse.travelers.Travelerinformation[0].email").toString();

        Assert.assertEquals(page, "1");
        Assert.assertEquals(email, "Developer12@gmail.com");
        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.header("Content-Type"), "application/xml; charset=utf-8");
    }

    @Test
    public void testXMLResponseBody() {
        //XmlPath is like JSONObject but for xml
        XmlPath xmlobj = new XmlPath(response.asString());
        List<String> travellers = xmlobj.getList("TravelerinformationResponse.travelers.Travelerinformation");

        Assert.assertEquals(travellers.size(), 10);

        List<String> traveller_names = xmlobj.getList("TravelerinformationResponse.travelers.Travelerinformation.name");

        boolean status = false;
        for (String name : traveller_names) {
            if (name.equals("Developer123")) {
                status = true;
                break;
            }
        }

        Assert.assertTrue(status);


    }
}
