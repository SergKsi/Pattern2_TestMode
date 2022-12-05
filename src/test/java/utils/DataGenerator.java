package utils;

import api.RegisterUserData;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

// Утилитный класс - содержит приватный конструктор и статичные методы
// Для удобства - вызвали один метод,к-ый сенерировал сразу 3 поля (фио, тел, город)
@UtilityClass
public class DataGenerator {

    // класс generateInfo возвращает RegistrationInfo (Data класс с 3-мя полями)
    // генерируем новый RegistrationInfo с 3-мя полями с помощью faker
    //     @UtilityClass позволяет вызывать generateInfo в Тестах без подробного описания.

    private static final String URL = "http://localhost";
    private static final String PrefixURL = "/api/system/users";
    private static final Integer PORT = 9999;


    public static RequestSpecification requestSpec = new RequestSpecBuilder()
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

    @UtilityClass
    public static class Registration {

        // генерация валидных данных (status = "active")
        public static RegisterUserData generateInfo(String locate) {
            Faker faker = new Faker(new Locale(locate));
            String[] statusUser = {"active", "passive"};
            return new RegisterUserData(faker.name().username(),
                    faker.internet().password(), statusUser[0]);
        }

        // генерация НЕвалидных данных (status = "passive")
        public static RegisterUserData generatePassiveInfo(String locate) {
            Faker faker = new Faker(new Locale(locate));
            String[] statusUser = {"active", "passive"};
            return new RegisterUserData(faker.name().username(),
                    faker.internet().password(), statusUser[1]);
        }
    }

}
