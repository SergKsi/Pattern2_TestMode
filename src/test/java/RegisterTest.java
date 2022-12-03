import api.RegisterUserData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class RegisterTest {
    private static final String URL = "http://localhost";
    private static final String PrefixURL = "/api/system/users";
    private static final Integer PORT = 9999;


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(URL)
            .setPort(PORT)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void checkStatusCode200() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
//        RegisterUserData user = new RegisterUserData("vasya", "password", "active");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    @Test
    public void checkRegistrationUser() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        open(URL + ":" + PORT);
        $("[data-test-id=\"login\"] input").setValue(user.getLogin());
        $("[data-test-id=\"password\"] input").setValue(user.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
    }
}
