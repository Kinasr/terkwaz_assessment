package helper;

import org.apache.commons.io.FileUtils;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.TestSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import utility.MyLogger;
import utility.MyReport;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static helper.Constant.TEST_RESOURCES_PATH;

public class JunitListener implements TestExecutionListener {
    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.getType().isTest()){
            var timestamp = Timestamp.valueOf(LocalDateTime.now()).getTime();
            var driver = (TakesScreenshot) WebDriverFactory.getDriver(new PropertyReader(TEST_RESOURCES_PATH
                    + "configuration/browser-config"));
            var src = driver.getScreenshotAs(OutputType.FILE);
            try {
                FileHandler.copy(
                        src,
                        new File("output/screenshots/" + testIdentifier.getDisplayName()
                                + "_" + timestamp + ".png")
                );
                MyReport.attachScreenshot(FileUtils.openInputStream(src));
            } catch (IOException e) {
                MyLogger.severe(this.getClass().getSimpleName(), "Can't take a screenshot for: " +
                        testIdentifier.getDisplayName());
                e.printStackTrace();
            }
        }
    }
}
