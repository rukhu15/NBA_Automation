package framework.utility.propertiesManager;

import framework.utility.globalConst.ConfigInput;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class LabelReader {
    /**
     * Create Locale Object using the parameters set in automation.properties
     * Load the resource Bundle based on the Locale
     * get the Message corresponding to the code
     */
    private static ResourceBundle resource = null;
    private static Locale locale = null;

    public static String getString(String code, Object... params) {
        // get locale object
        if (locale == null) {
            locale = new Locale(ConfigInput.language, ConfigInput.country);
        }

        // get resorce bundle
        if (resource == null) {
            resource = PropertyResourceBundle.getBundle("labels", locale);
        }

        // get the message
        String message = resource.getString(code).trim();

        // get Dynamic Message
        if (params.length > 0) {
            message = MessageFormat.format(message, params);
        }

        return message;

    }


}
