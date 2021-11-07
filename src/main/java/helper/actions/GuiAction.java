package helper.actions;

import helper.Constant;
import io.qameta.allure.model.Status;
import model.VerifyRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.opentest4j.AssertionFailedError;
import utility.MyReport;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class GuiAction {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;
    private Select select;
    private List<VerifyRecord> verifications = new ArrayList<>();

    private final BiFunction<WebDriverWait, WebElement, WebElement> waitVisibilityOf = (wait, element) ->
            wait.until(ExpectedConditions.visibilityOf(element));
    private final BiFunction<WebDriverWait, By, WebElement> waitPresenceOf = (wait, by) ->
            wait.until(ExpectedConditions.presenceOfElementLocated(by));

    public GuiAction(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.getWaitTime()));
        actions = new Actions(driver);
    }

    public int getElementsSize(By by) {
        return driver.findElements(by).size();
    }

    public List<WebElement> getElements(By by) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public WebElement waitForVisibility(WebElement element) {
        waitVisibilityOf.apply(wait, element);
        return element;
    }

    public void clickOn(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public void clickOn(By by) {
        waitPresenceOf.apply(wait, by);
        clickOn(driver.findElement(by));
    }

    public void sendTextTo(WebElement element, String text) {
        waitVisibilityOf.apply(wait, element);
        element.clear();
        element.sendKeys(text);
    }

    public void sendTextTo(By by, String text) {
        waitPresenceOf.apply(wait, by);
        sendTextTo(driver.findElement(by), text);
    }

    public String getURL() {
        return driver.getCurrentUrl();
    }

    public String getTextFrom(WebElement element) {
        return element.getText();
    }

    public String getTextFrom(By by) {
        waitPresenceOf.apply(wait, by);
        return getTextFrom(driver.findElement(by));
    }

    // -------------------------------------------------- Actions --------------------------------------------------
    public void rightClickOn(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        actions.contextClick(element).perform();
    }

    public void rightClickOn(By by) {
        waitPresenceOf.apply(wait, by);
        rightClickOn(driver.findElement(by));
    }

    public void dragAndDrop(WebElement fromElement, WebElement toElement) {
        waitVisibilityOf.apply(wait, fromElement);
        waitVisibilityOf.apply(wait, toElement);

        var javaScript = """
                var src=arguments[0],tgt=arguments[1];
                var dataTransfer={
                    dropEffect:'',
                    effectAllowed:'all',
                    files:[],
                    items:{},
                    types:[],
                    setData:
                    function(format,data){
                        this.items[format]=data;
                        this.types.append(format);
                        },
                    getData:function(format){
                        return this.items[format];
                        },
                    clearData:function(format){}
                    };
                var emit=function(event,target){
                    var evt=document.createEvent('Event');
                    evt.initEvent(event,true,false);
                    evt.dataTransfer=dataTransfer;target.dispatchEvent(evt);
                    };
                emit('dragstart',src);
                emit('dragenter',tgt);
                emit('dragover',tgt);
                emit('drop',tgt);
                emit('dragend',src);
                """;

        ((JavascriptExecutor) driver).executeScript(javaScript, fromElement, toElement);
    }

    public void dragAndDrop(By from, By to) {
        waitPresenceOf.apply(wait, from);
        waitPresenceOf.apply(wait, to);
        dragAndDrop(
                driver.findElement(from),
                driver.findElement(to)
        );
    }

    public Selection locateSelector(WebElement element) {
        waitVisibilityOf.apply(wait, element);
        return new Selection(element);
    }

    public Selection locateSelector(By by) {
        waitPresenceOf.apply(wait, by);
        return locateSelector(driver.findElement(by));
    }

    public void sendTextToAlert(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public boolean isAlertPresent() {
        try {
            var alert = wait.until(ExpectedConditions.alertIsPresent());
            if (alert != null) {
                return true;
            } else {
                throw new Throwable();
            }
        } catch (Throwable e) {
            return false;
        }
    }

    public boolean isPresent(By by) {
        try {
            waitPresenceOf.apply(wait, by);
            return true;
        } catch (TimeoutException e){
            return false;
        }
    }

    public boolean isSelected(WebElement element) {
        waitVisibilityOf.apply(wait, element);
        return element.isSelected();
    }

    public boolean isSelected(By by) {
        waitPresenceOf.apply(wait, by);
        return isSelected(driver.findElement(by));
    }

    public boolean isDisplayed(WebElement element) {
        waitVisibilityOf.apply(wait, element);
        return element.isDisplayed();
    }

    public boolean isDisplayed(By by) {
        waitPresenceOf.apply(wait, by);
        return isDisplayed(driver.findElement(by));
    }

    public boolean isEnabled(WebElement element) {
        waitVisibilityOf.apply(wait, element);
        return element.isEnabled();
    }

    public boolean isEnabled(By by) {
        waitPresenceOf.apply(wait, by);
        return isEnabled(driver.findElement(by));
    }

    public void assertThat(String message, Runnable runnable) {
        var stepId = MyReport.startStep(this.getClass().getSimpleName(), message);
        try {
            runnable.run();
            MyReport.updateStepToBePassed(stepId);
        } catch (AssertionFailedError e){
            MyReport.stopStep(stepId);
            throw e;
        }
    }

    public void performVerification(List<VerifyRecord> records) {
        var assertions = new ArrayList<Executable>();

        for (VerifyRecord record : records) {
            var stepId = MyReport.startStep(this.getClass().getSimpleName(), record.getMessage());

            assertions.add(record.getExecutable());

            if (record.condition().orElse(true))
                MyReport.updateStepStatus(stepId, Status.PASSED);
            else
                MyReport.updateStepStatus(stepId, Status.FAILED);

            MyReport.stopStep(stepId);
        }

        Assertions.assertAll(assertions.stream());
    }

    public GuiAction startVerification(VerifyRecord record) {
        if (verifications.isEmpty()) verifications = new ArrayList<>();

        verifications.add(record);

        return this;
    }

    public void verify() {
        if (!verifications.isEmpty()) {
            performVerification(verifications);
            verifications.clear();
        }
    }
}
