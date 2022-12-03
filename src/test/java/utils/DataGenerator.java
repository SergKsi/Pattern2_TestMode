package utils;

import api.RegisterUserData;
import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

// Утилитный класс - содержит приватный конструктор и статичные методы
// Для удобства - вызвали один метод,к-ый сенерировал сразу 3 поля (фио, тел, город)
@UtilityClass
public class DataGenerator {

    // класс generateInfo возвращает RegistrationInfo (Data класс с 3-мя полями)
    // генерируем новый RegistrationInfo с 3-мя полями с помощью faker
    //     @UtilityClass позволяет вызывать generateInfo в Тестах без подробного описания.
    @UtilityClass
    public static class Registration {
        public static RegisterUserData generateInfo(String locate) {
            Faker faker = new Faker(new Locale(locate));
            String[] statusUser = {"active", "passive"};
            return new RegisterUserData(faker.name().username(),
                    faker.internet().password(), statusUser[faker.number().numberBetween(0, 1)]);
        }
    }

}
