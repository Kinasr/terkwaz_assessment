package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicLoadingPage {
    private final GuiAction guiAction;
    private final WebDriver driver;

    private final By example2Link = By.xpath("//a[contains(text(),'Example 2')]");

    public DynamicLoadingPage(WebDriver driver) {
        this.driver = driver;
        guiAction = new GuiAction(driver);
    }

    public ExampleTwo navigateToExampleTwo() {
        guiAction.clickOn(example2Link);
        return new ExampleTwo(driver);
    }

    public static class ExampleTwo{
        private final GuiAction guiAction;

        private final By titleText = By.tagName("h3");
        private final By startButton = By.cssSelector("#start button");
        private final By resultText = By.id("finish");

        private ExampleTwo(WebDriver driver) {
            guiAction = new GuiAction(driver);
        }

        public ExampleTwo assertOnPageTitle(String expectedTitle) {
            var actualTitle = guiAction.getTextFrom(titleText);
            guiAction.assertThat(
                    "Checking the page title to be: " + expectedTitle,
                    () -> assertEquals(expectedTitle, actualTitle)
            );
            return this;
        }

        public ExampleTwo clickOnStart() {
            guiAction.clickOn(startButton);
            return this;
        }

        public ExampleTwo assertResultMessage(String expectedMessage) {
            guiAction.assertThat(
                    "Check that the message will be: " + expectedMessage,
                    () -> assertEquals(expectedMessage, guiAction.getTextFrom(resultText))
            );
            return this;
        }
    }
}
