package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.dataGenerator.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.dataGenerator.DataGenerator.*;

public class ServiceCreditCardTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldRegistrationHappyTest() {
        $("[data-test-id='city'] input ").setValue(generateCity());
        $x("//*[@class='menu-item__control']").click();
        $("[data-test-id='date'] input ").click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        $("[data-test-id='date'] input ").setValue(DataGenerator.generateDate(7, "dd.MM.yyyy"));
        $("[data-test-id='name'] input ").setValue(generateName());
        $("[data-test-id='phone'] input ").setValue(generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        if ($$("button").find(exactText("Перепланировать")).exists()) {
            $$("button").find(exactText("Перепланировать")).click();
        }
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
    }
}
