package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegisterTest {
    private static final String URL = "http://localhost";
    private static final String PrefixURL = "/api/system/users";
    private static final Integer PORT = 9999;

    @BeforeEach
    void setupEach() {
        open(URL + ":" + PORT);
    }

    // валидный пользователь - логин\пароль\статус = Ok
    @Test
    public void checkRegistrationValidUser() {
        var regUser = DataGenerator.Registration.generateRegUser("active");
        $("[data-test-id=\"login\"] input").setValue(regUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(regUser.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
        $(".icon").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Личный кабинет")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - (Не зарег. польз) логин\пароль = ok, статус=active
    @Test
    public void checkNoRegistrationUser() {
        var noRegUser = DataGenerator.Registration.getUser("active");
        $("[data-test-id=\"login\"] input").setValue(noRegUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(noRegUser.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - (зарег. польз) логин\пароль = ok, статус=blocked
    @Test
    public void checkRegistrationNoValiStatusdUser() {
        var regUserBlock = DataGenerator.Registration.generateRegUser("blocked");
        $("[data-test-id=\"login\"] input").setValue(regUserBlock.getLogin());
        $("[data-test-id=\"password\"] input").setValue(regUserBlock.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
        $(".icon").shouldBe(visible, Duration.ofMillis(5000));
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Пользователь заблокирован")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - логин\статус = ok, пароль=Bad
    @Test
    public void checkRegistrationNoValidPasswordUser() {
        var regUser = DataGenerator.Registration.generateRegUser("active");
        var passwordBad = DataGenerator.getRandomPassword();
        $("[data-test-id=\"login\"] input").setValue(regUser.getLogin());
        $("[data-test-id=\"password\"] input").setValue(passwordBad);
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - пароль\статус = ok, логин=Bad
    @Test
    public void checkRegistrationNoValidLoginUser() {
        var regUser = DataGenerator.Registration.generateRegUser("active");
        var logindBad = DataGenerator.getRandomLogin();
        $("[data-test-id=\"login\"] input").setValue(logindBad);
        $("[data-test-id=\"password\"] input").setValue(regUser.getPassword());
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

    // Невалидный пользователь - статус = ok, пароль,логин=Bad
    @Test
    public void checkRegistrationNoValidLoginAndPasswordUser() {
        var regUser = DataGenerator.Registration.generateRegUser("active");
        var logindBad = DataGenerator.getRandomLogin();
        var passwordBad = DataGenerator.getRandomPassword();
        $("[data-test-id=\"login\"] input").setValue(logindBad);
        $("[data-test-id=\"password\"] input").setValue(passwordBad);
        $("[data-test-id=\"action-login\"] span").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Ошибка!")).shouldBe(visible, Duration.ofMillis(5000));
        $(byText("Неверно указан логин или пароль")).shouldBe(visible, Duration.ofMillis(5000));
    }

}
