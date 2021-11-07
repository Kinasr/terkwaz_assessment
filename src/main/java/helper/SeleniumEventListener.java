package helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import utility.MyReport;

import java.util.Arrays;
import java.util.List;

public class SeleniumEventListener implements WebDriverListener {
    @Override
    public void beforeClick(WebElement element) {
        MyReport.startStep(element.toString(), this.getClass().getSimpleName(),
                "Clicking on element [" + element + "]");
    }

    @Override
    public void afterClick(WebElement element) {
        MyReport.updateStepToBePassed(element.toString(), this.getClass().getSimpleName(),
                "Successfully clicked on element [" + element + "]");
    }

    @Override
    public void beforeTo(WebDriver.Navigation navigation, String url) {
        MyReport.startStep(url, this.getClass().getSimpleName(), "Navigating to " + url);
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        MyReport.updateStepToBePassed(url, this.getClass().getSimpleName(),
                "Navigated to " + url + " successfully");
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        MyReport.startStep(locator.toString(), this.getClass().getSimpleName(),
                "Finding element [" + locator + "]");
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        MyReport.updateStepToBePassed(locator.toString(), this.getClass().getSimpleName(),
                "Successfully found element [" + locator + "]");
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        MyReport.startStep(locator.toString(), this.getClass().getSimpleName(),
                "Finding elements [" + locator + "]");
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        MyReport.updateStepToBePassed(locator.toString(), this.getClass().getSimpleName(),
                "Successfully found elements [" + locator + "]");
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        MyReport.startStep(element.toString(), this.getClass().getSimpleName(),
                "Sending: " + Arrays.toString(keysToSend) + " to element [" + element + "]");
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        MyReport.updateStepToBePassed(element.toString(), this.getClass().getSimpleName(),
                "Successfully sending text to [" + element + "]");
    }

    @Override
    public void beforeGetText(WebElement element) {
        MyReport.startStep(element.toString(), this.getClass().getSimpleName(),
                "Getting text of element [" + element + "]");
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        MyReport.updateStepToBePassed(element.toString(), this.getClass().getSimpleName(),
                "Successfully got: " + result + " as the text of the element [" + element + "]");
    }
}
