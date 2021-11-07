package tests;

import object_model.RandomFactModel;
import org.testng.annotations.Test;

public class RandomFactTest {
    @Test(testName = "Get Random Cat Fact",
    description = "Test that getting random cat fact returns not empty text")
    public void getRandomCatFact() {
        RandomFactModel
                .getRandomCatFact()
                .assertThatTheResponseHasText();
    }
}
