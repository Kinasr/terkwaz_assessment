package page;

import helper.actions.GuiAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GooglePage {
    private final GuiAction guiAction;

    private final By searchBar = By.name("q");
    private final By searchButton = By.name("btnK");
    private final By searchResult = By.className("tF2Cxc");

    public GooglePage(WebDriver driver) {
        guiAction = new GuiAction(driver);
    }

    public GooglePage sendSearchText(String text) {
        guiAction.sendTextTo(searchBar, text);
        return this;
    }

    public GooglePage search() {
        guiAction.clickOn(searchButton);
        return this;
    }

    public GooglePage assertThatTheResultNumberNContains(int n, String contain) {
        var results = guiAction.getElements(searchResult);
        if (n >= results.size()) return null;
        var x = guiAction.getTextFrom(results.get(n));

        guiAction.assertThat(
                "Check the result number {" + n + "} contains: " + contain,
                () -> assertTrue(guiAction.getTextFrom(results.get(n)).contains(contain))
        );
        return this;
    }
}
