package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DynamicLoadingTest extends BaseTest {

    @Test
    @DisplayName("Test that clicking on start will return Hello World!")
    public void exTwoEnsureThatResultWillDisplayAfterClickingOnStart() {
        var expectedMessage = getReader().get("dynamic-loading.expected-message").toString();
        homePage
                .navigateToDynamicLoadingPage()
                .navigateToExampleTwo()
                .clickOnStart()
                .assertResultMessage(expectedMessage);
    }
}
