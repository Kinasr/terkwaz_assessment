package data_providers;

import helper.Constant;
import helper.JsonReader;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DataProviders {

    public static List<Arguments> gerValidUserCredentials() {
        var reader = new JsonReader(Constant.TEST_RESOURCES_PATH +"data/login_data");
        var jsonSourcePath = "valid-credentials";
        var list = new  ArrayList<Arguments>();

        for (var i = 0; i < reader.get(jsonSourcePath).toArray().length; i++) {
            list.add(arguments(
               reader.get(jsonSourcePath + "[" + i + "].username").toString(),
               reader.get(jsonSourcePath + "[" + i + "].password").toString(),
               reader.get(jsonSourcePath + "[" + i + "].next-page-title").toString()
            ));
        }
        return list;
    }


    public static List<Arguments> geInValidUserCredentials() {
        var reader = new JsonReader(Constant.TEST_RESOURCES_PATH +"data/login_data");
        var jsonSourcePath = "invalid-credentials";
        var list = new  ArrayList<Arguments>();

        for (var i = 0; i < reader.get(jsonSourcePath).toArray().length; i++) {
            list.add(arguments(
                    reader.get(jsonSourcePath + "[" + i + "].username").toString(),
                    reader.get(jsonSourcePath + "[" + i + "].password").toString(),
                    reader.get(jsonSourcePath + "[" + i + "].message").toString()
            ));
        }
        return list;
    }
}
