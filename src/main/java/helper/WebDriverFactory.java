package helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;
import utility.MyLogger;

/**
 * This class is used to generate the webDriver with the options in the browser config file.
 */
public class WebDriverFactory {
    private static WebDriver driver = null;
    private static PropertyReader browserProps;


    /**
     * Loads the browser to generate its webDriver.
     * @return Chrome webDriver or Firefox webDriver or null if no valid browser in the config file.
     */
    public static WebDriver getDriver(PropertyReader reader) {
        if (driver == null) {
            browserProps = reader;
            String browser = browserProps.getProperty("browser");

            if (browser.equals("Chrome")) driver = getChromeDriver();
            else if (browser.equals("Firefox")) driver = getFirefoxDriver();
            else MyLogger.severe(WebDriverFactory.class.getSimpleName(),
                        "No Valid browser name in browser-config file");
        }
        return driver;
    }

    public static void quit() {
        driver.quit();
        driver = null;
    }

    /**
     * Generate the Firefox webDriver with registering WebDriverEventListener
     * @return Firefox webDriver with headless option if it "True" in the browser config file.
     */
    private static WebDriver getFirefoxDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver firefoxDriver;
        if (Boolean.parseBoolean(browserProps.getProperty("headless"))) {
            MyLogger.info(WebDriverFactory.class.getSimpleName(), "Initializing headless Firefox driver");

            var options = new FirefoxOptions();
            options.setHeadless(true);
            options.addArguments("windows-size=3000,2000");

            firefoxDriver = new FirefoxDriver(options);
        } else {
            MyLogger.info(WebDriverFactory.class.getSimpleName(), "Initializing normal Firefox driver");

            firefoxDriver = new FirefoxDriver();
        }

        return addListenerToDriver(firefoxDriver);
    }

    /**
     * Generate the Chrome webDriver with registering WebDriverEventListener
     * @return Chrome webDriver with headless option if it "True" in the browser config file.
     */
    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        WebDriver chromeDriver;
        if (Boolean.parseBoolean(browserProps.getProperty("headless"))) {
            MyLogger.info(WebDriverFactory.class.getSimpleName(), "Initializing headless Chrome driver");

            var options = new ChromeOptions();
            options.setHeadless(true);
            options.addArguments("windows-size=3000,2000");

            chromeDriver = new ChromeDriver(options);
        } else {
            MyLogger.info(WebDriverFactory.class.getSimpleName(), "Initializing normal Chrome driver");

            chromeDriver = new ChromeDriver();
        }

        return addListenerToDriver(chromeDriver);
    }

    private static WebDriver addListenerToDriver(WebDriver driver) {
        return new EventFiringDecorator(new SeleniumEventListener()).decorate(driver);
    }
}
