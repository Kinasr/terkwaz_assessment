package object_model;

import helpers.ApiActions;
import io.restassured.response.Response;
import models.HttpRequest;
import models.HttpStatusCodes;
import models.RequestTypes;

import static org.testng.Assert.assertFalse;

public class RandomFactModel {
    private final ApiActions apiActions;
    private final Response response;

    private RandomFactModel(HttpRequest request){
        apiActions = new ApiActions(request);

        response = apiActions
                .send()
                .assertStatusCode()
                .extractResponse();
    }

    public static RandomFactModel getRandomCatFact() {
        return new RandomFactModel(
                new HttpRequest(RequestTypes.GET, "random", HttpStatusCodes.OK)
        );
    }

    public RandomFactModel assertThatTheResponseHasText() {
        apiActions.assertThat(
                "Check that the response is not empty",
                () -> assertFalse(response.jsonPath().get("text").toString().isEmpty())
        );

        return this;
    }
}
