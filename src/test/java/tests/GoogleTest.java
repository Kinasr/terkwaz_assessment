package tests;

import base.BaseTest;
import helper.PropertyReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.GooglePage;

import static helper.Constant.TEST_RESOURCES_PATH;

public class GoogleTest extends BaseTest {

    @BeforeEach
    @Override
    public void minorSetUp() {
        var url = new PropertyReader(TEST_RESOURCES_PATH + "configuration/test-configurations")
                .getProperty("google-url");
        getDriver().get(url);
    }

    @Test
    @DisplayName("Check the content of the third result at Google page")
    public void checkTheThirdResultText() {
        var searchText = getReader().get("google.search-for").toString();
        var indexOfResult = getReader().get("google.index-of-result").toInteger();
        var resultShouldContains = getReader().get("google.result-contains").toString();

        new GooglePage(getDriver())
                .sendSearchText(searchText)
                .search()
                .assertThatTheResultNumberNContains(indexOfResult, resultShouldContains);
    }
}
