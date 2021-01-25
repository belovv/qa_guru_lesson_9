import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static io.qameta.allure.Allure.step;

public class SearchTest {

    @BeforeAll
    static void setup (){
        Configuration.startMaximized = true;
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

       // Configuration.remote = "https://user1:1234@" + System.getProperty("remote.browser.url") + ":4444/wd/hub/";
    }

    @Test
    void searchWeatherByYandex() {
        step("Открываем яндекс", (step) -> {
            open("https://yandex.ru/");
        });
        step("Вбиваем в поиске 'Погода'", (step) -> {
            $("#text").val("Погода").pressEnter();

        });
        step("Проверяем что в поисковой выдаче есть сайт Gismeteo", (step) -> {
            $("body").shouldHave(Condition.text("gismeteo.ru"));
        });
    }


    @AfterEach
    @Step("Attachments")
    public void afterEach(){
        Helper.attachScreenshot("Last screenshot");
        Helper.attachPageSource();
        Helper.attachAsText("Browser console logs", Helper.getConsoleLogs());
       // Helper.attachVideo();

        closeWebDriver();
    }
}
