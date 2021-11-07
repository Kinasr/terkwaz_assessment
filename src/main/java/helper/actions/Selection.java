package helper.actions;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utility.MyLogger;

import java.util.List;
import java.util.stream.Collectors;

public class Selection {
    private final Select select;
    private final List<WebElement> options;

    protected Selection(WebElement element) {
        select = new Select(element);
        options = select.getOptions();
    }

    public Selection selectByIndex(int index) {
        if (index >= options.size()) {
            MyLogger.severe(this.getClass().getSimpleName(),
                    "Trying to access Option [" + index + "] which is out of boundary ["
                            + options.size() + "]");
            return null;
        }
        select.selectByIndex(index);
        return this;
    }

    public Selection selectByValue(String value) {
        select.selectByValue(value);
        return this;
    }

    public Selection selectByText(String text) {
        for (var option : options) {
            if (option.getText().equals(text)) {
                select.selectByVisibleText(text);
                return this;
            }
        }
        MyLogger.severe(this.getClass().getSimpleName(),
                "No such option [" + text + "]");
        return null;
    }

    public List<WebElement> getOptions() {
        return options;
    }

    public List<String> getOptionsText() {
        return options.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getSelectedOption() {
        return select.getFirstSelectedOption().getText();
    }
}
