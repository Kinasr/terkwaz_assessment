package helper;

/**
 * A class contains the fixed constants and the loaded property files.
 * This class follow the Single Tone Pattern.
 */
public class Constant {
    public static final String TEST_RESOURCES_PATH = "src/test/resources/";
    public static final String MAIN_RESOURCES_PATH = "src/main/resources/";

    public static long getWaitTime() {
        return Long.parseLong(new PropertyReader(TEST_RESOURCES_PATH + "configuration/test-configurations")
                .getProperty("wait-time"));
    }
}
