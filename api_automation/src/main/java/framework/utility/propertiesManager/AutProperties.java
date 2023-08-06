package framework.utility.propertiesManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class AutProperties {

    private static Properties properties = new Properties();

    /**
     * static method to get Instance of class AutProperties
     *
     * @return
     */
    public static AutProperties getInstance(String propertyFileName) {
        final AutProperties me = new AutProperties();
        me.loadProperties(me.getPropertiesFile("/" + propertyFileName));
        return me;
    }

    /**
     * Step 1
     * Get the Property file as input stream
     *
     * @return
     */
    private InputStream getPropertiesFile(String fileName) {
        return this.getClass().getResourceAsStream(fileName);
    }

    /**
     * Step 2
     * Load Properties
     *
     * @param propertiesStream
     */
    private void loadProperties(InputStream propertiesStream) {
        try {
            properties.load(propertiesStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

}
