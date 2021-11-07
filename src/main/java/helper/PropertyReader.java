package helper;

import utility.PropertyManager;

import java.util.Properties;

public class PropertyReader {
    private final Properties prop;

    public PropertyReader(String filePath) {
        prop = PropertyManager.loadPropertyFile(filePath);
    }

    public String getProperty(String propName) {
        return prop.getProperty(propName);
    }
}
