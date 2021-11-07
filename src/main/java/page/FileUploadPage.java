package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUploadPage {
    private final GuiAction guiAction;

    private final By titleText = By.tagName("h3");
    private final By fileChooseInput = By.id("file-upload");
    private final By uploadButton = By.id("file-submit");

    public FileUploadPage(WebDriver driver) {
        guiAction = new GuiAction(driver);
    }

    public FileUploadPage assertOnPageTitle(String expectedTitle) {
        var actualTitle = guiAction.getTextFrom(titleText);
        guiAction.assertThat(
                "Checking the page title to be: " + expectedTitle,
                () -> assertEquals(expectedTitle, actualTitle)
        );
        return this;
    }

    public FileUploadPage selectFile(String filePath) {
        guiAction.sendTextTo(fileChooseInput, filePath);
        return this;
    }

    public FileUploadPage uploadFile() {
        guiAction.clickOn(uploadButton);
        return this;
    }
}
