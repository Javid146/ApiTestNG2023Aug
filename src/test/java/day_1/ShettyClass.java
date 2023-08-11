package day_1;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShettyClass {

    JsonPath jsonPath = new JsonPath(payload.coursePrice());

    @Test
    public void Practice(){

        int count = jsonPath.getInt("courses.size()"); //it is arraylist that's why
        System.out.println("count = " + count);

        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println("purchaseAmount = " + purchaseAmount);

        String firstTitle = jsonPath.get("courses[0].title");
        System.out.println("firstTitle = " + firstTitle);

        int totalPrice = 0;
        int price = 0;
        int copies = 0;

        for (int i = 0; i < jsonPath.getInt("courses.size()"); i++) {
            System.out.println(jsonPath.get("courses[" + i + "].title").toString());
            price = jsonPath.getInt("courses[" + i + "].price");
            copies = jsonPath.getInt("courses[" + i + "].copies");
            totalPrice +=  price * copies;
        }

        System.out.println("totalPrice = " + totalPrice);
        Assert.assertEquals(totalPrice, purchaseAmount);
    }

    @Test
    public void getTitleAndPrice() {
        int totalPrice = 0;
        int price = 0;
        int copies = 0;

        for (int i = 0; i < jsonPath.getInt("courses.size()"); i++) {

            if (jsonPath.get("courses[" + i + "].title").equals("RPA")) {
                price = jsonPath.getInt("courses[" + i + "].price");
                copies = jsonPath.getInt("courses[" + i + "].copies");
                totalPrice +=  price * copies;
                 Assert.assertEquals(totalPrice,450);
            }
        }
    }
}
