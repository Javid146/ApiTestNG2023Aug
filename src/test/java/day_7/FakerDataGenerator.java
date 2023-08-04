package day_7;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;

public class FakerDataGenerator {

    @Test
    void testGenerateDummyData() {

        Faker faker = new Faker();

        String fullNm = faker.name().fullName();
        String firstNm = faker.name().firstName();
        String lastNm = faker.name().lastName();
        String userNm = faker.name().username();
        String password = faker.internet().password();
        String phone = faker.phoneNumber().cellPhone();
        String email = faker.internet().emailAddress();
    }
}
