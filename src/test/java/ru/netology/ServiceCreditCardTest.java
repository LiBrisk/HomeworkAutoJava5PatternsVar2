package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    String date = DataGenerator.generateDate(7, "dd.MM.yyyy");

    @Test
    @DisplayName("Проверяем планирование встречи через генератор данных")
    void shouldRegistrationHappyTest() {
        $("[data-test-id='city'] input ").setValue(generateCity());
        $x("//*[@class='menu-item__control']").click();
        $("[data-test-id='date'] input ").click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        $("[data-test-id='date'] input ").setValue(date);
        $("[data-test-id='name'] input ").setValue(generateName());
        $("[data-test-id='phone'] input ").setValue(generatePhone());
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        if ($$("button").find(exactText("Перепланировать")).exists()) {
            $$("button").find(exactText("Перепланировать")).click();
        }
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        String actualText = $x("//*[@class='notification__content']").getText();
        String expectedText = "Встреча успешно запланирована на " + date;
        Assertions.assertEquals(expectedText, actualText, "Сообщение в сплывающем окне соответствует ожидаемому");
    }

    @Test
    @DisplayName("роверяем перепланирование встречи")
    void shouldRegistrationRescheduleTest() {
        $("[data-test-id='city'] input ").setValue("Краснодар");
        $x("//*[@class='menu-item__control']").click();
        $("[data-test-id='date'] input ").click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        $("[data-test-id='date'] input ").setValue(date);
        $("[data-test-id='name'] input ").setValue("Траханова Ксения");
        $("[data-test-id='phone'] input ").setValue("+8 929 822 06 15");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Запланировать")).click();
        $x("(//*[@class='notification__content'])[2]").shouldBe(visible);
        String actualRescheduleText = $x("(//*[@class='notification__content'])[2]").getText()
                .replace("\n", "")
                .replace("\r", "")
                .replace("<br>", "\n");
        String expectedRescheduleText = "У вас уже запланирована встреча на другую дату. Перепланировать?Перепланировать";
        Assertions.assertEquals(expectedRescheduleText, actualRescheduleText, "Сообщение в сплывающем окне не соответствует ожидаемому");
        $$("button").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(15000));
        String actualText = $x("//*[@class='notification__content']").getText();
        String expectedText = "Встреча успешно запланирована на " + date;
        Assertions.assertEquals(expectedText, actualText, "Сообщение в сплывающем окне не соответствует ожидаемому");
    }
}
