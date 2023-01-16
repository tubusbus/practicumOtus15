import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.Waiter;
import org.example.Browser;
import org.example.Driver;
import org.example.PagesEnum;
import org.example.Steps;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.WebDriverRunner.currentFrameUrl;
import static org.example.PagesEnum.MAIN_PAGE;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class tests {

    //    @Driver
//    public WebDriver driver;
    Browser browser = new Browser();
    WebDriver driver = browser.runDriwer();

//    @BeforeEach
//    public void closeBrowser() {
//        driver.get(MAIN_PAGE.getValue());
//
//    }

    @Test
    public void dragAndDrop() {
//        Selenide.open(MAIN_PAGE.getValue());
//        driver.get(MAIN_PAGE.getValue());
        Steps steps = new Steps();
//        steps.getDatesCourses("Популярные курсы");
//        steps.getDatesCourses("Специализации");
//        steps.getDatesCourses("Рекомендации для вас");

        steps.dELETEsetDriver(driver);


        SelenideElement element = steps.getCourseForQueue("Все", "ранний");
//        steps.lightingElement(element);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        steps.unLightingElement(element);
        element.click();
        System.out.println();

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
