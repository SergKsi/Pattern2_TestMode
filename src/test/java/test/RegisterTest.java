package test;

import api.RegisterUserData;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class RegisterTest {
    private static final String URL = "http://localhost";
    private static final String PrefixURL = "/api/system/users";
    private static final Integer PORT = 9999;

    private static final String BadPassword = "qwerty123";
    private static final String BadLogin = "vasya3131";


    @Test
    public void checkStatusCode200() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
        RequestSpecification requestSpec = DataGenerator.requestSpec;
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    // валидный пользователь - логин\пароль\статус = Ok
    @Test
    public void checkRegistrationValidUser() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
        RequestSpecification requestSpec = DataGenerator.requestSpec;
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
        $(".icon").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - логин\пароль = ok, статус=disable
    @Test
    public void checkRegistrationNoValiStatusdUser() {
        RegisterUserData user = DataGenerator.Registration.generatePassiveInfo("en-US");
        RequestSpecification requestSpec = DataGenerator.requestSpec;
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(500); // код 500
        open(URL + ":" + PORT);
        $("[data-test-id=\"login\"] input").setValue(user.getLogin());
        $("[data-test-id=\"password\"] input").setValue(user.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
        $(".icon").shouldBe(visible, Duration.ofMillis(5000));
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - логин\статус = ok, пароль=Bad
    @Test
    public void checkRegistrationNoValidPasswordUser() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
        RequestSpecification requestSpec = DataGenerator.requestSpec;
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
        $("[data-test-id=\"password\"] input").setValue(BadPassword);
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - пароль\статус = ok, логин=Bad
    @Test
    public void checkRegistrationNoValidLoginUser() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
        RequestSpecification requestSpec = DataGenerator.requestSpec;
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        open(URL + ":" + PORT);
        $("[data-test-id=\"login\"] input").setValue(BadLogin);
        $("[data-test-id=\"password\"] input").setValue(user.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - статус = ok, пароль,логин=Bad
    @Test
    public void checkRegistrationNoValidLoginAndPasswordUser() {
        RegisterUserData user = DataGenerator.Registration.generateInfo("en-US");
        RequestSpecification requestSpec = DataGenerator.requestSpec;
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post(PrefixURL) // на какой путь относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        open(URL + ":" + PORT);
        $("[data-test-id=\"login\"] input").setValue(BadLogin);
        $("[data-test-id=\"password\"] input").setValue(BadPassword);
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

}
