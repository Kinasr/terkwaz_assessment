package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.function.Function;

public class HomePage {
    private final WebDriver driver;
    private final GuiAction guiAction;

    private final By dynamicLoadingLink = By.linkText("Dynamic Loading");
    private final By fileUploadLink = By.linkText("File Upload");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        guiAction = new GuiAction(driver);
    }

    public DynamicLoadingPage navigateToDynamicLoadingPage() {
        guiAction.clickOn(dynamicLoadingLink);
        return new DynamicLoadingPage(driver);
    }

    public FileUploadPage navigateToFileUploadPage() {
        guiAction.clickOn(fileUploadLink);
        return new FileUploadPage(driver);
    }
}