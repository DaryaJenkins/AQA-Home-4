import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    protected String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitOrderSuccessfully() {
        generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='city'] input").setValue("Москва");
        $$("[data-test-id='date'] input").findBy(visible).press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("01.03.2025");
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79991112233");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $(withText(("Встреча успешно забронирована на " + $("[data-test-id='date'] input").getValue()))).shouldBe(visible, Duration.ofSeconds(15));
    }
}