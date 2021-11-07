package base;

import helper.JsonReader;
import helper.PropertyReader;
import helper.WebDriverFactory;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import page.HomePage;

import static helper.Constant.TEST_RESOURCES_PATH;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    private WebDriver driver;
    protected HomePage homePage;

    private JsonReader reader;

    @BeforeAll
    public void majorSetUp() {
        reader = new JsonReader(TEST_RESOURCES_PATH + "data/data");
        driver = WebDriverFactory.getDriver(
                new PropertyReader(TEST_RESOURCES_PATH + "configuration/browser-config")
        );
        homePage = new HomePage(driver);
    }

    @BeforeEach
    public void minorSetUp() {
        var url = new PropertyReader(TEST_RESOURCES_PATH + "configuration/test-configurations")
                .getProperty("base-url");
        driver.get(url);
    }

    @AfterEach
    public void minorTearDown() {}

    @AfterAll
    public void majorTearDown() {
        WebDriverFactory.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public JsonReader getReader() {
        return reader;
    }
}
