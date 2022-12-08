package utils;

import api.RegisterUserData;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static final String URL = "http://localhost";
    private static final String PrefixURL = "/api/system/users";
    private static final Integer PORT = 9999;

    private static final Faker faker = new Faker(new Locale("en-US"));

    private DataGenerator() {

    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(URL)
            .setPort(PORT)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendReq(RegisterUserData user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static class Registration {
        private Registration() {

        }

        public static RegisterUserData getUser(String statusUser) {
            var user = new RegisterUserData(faker.name().username(),
                    faker.internet().password(), statusUser);
            return user;
        }

        public static RegisterUserData generateRegUser(String statusUser) {
            var regUser = getUser(statusUser); // создание пользователя
            sendReq(regUser); // регистрация
            return regUser;
        }
    }
}
