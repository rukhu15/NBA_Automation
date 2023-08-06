package framework.utility.propertiesManager;

import framework.utility.globalConst.ConfigInput;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageReader {

    /**
     * Create Locale Object using the parameters set in automation.properties
     * Load the resource Bundle based on the Locale
     * get the Message corresponding to the code
     */

    /*private static final AutProperties autTestProperties = AutProperties.getInstance();
    private static String language = autTestProperties.getProperty("locale.language");
    private static String country = autTestProperties.getProperty("locale.country");*/

    private static ResourceBundle resourceBundle = null;
    private static ResourceBundle labelBundle = null;
    private static Locale locale;

    /**
     * Get Dynamic message
     *
     * @param Code
     * @param params
     * @return
     */
    public static String getDynamicMessage(String Code, Object... params) {
        String message = getMessage(Code, null);
        return MessageFormat.format(message, params);
    }

    public static boolean loadResourceBundle(Locale locale) {
        if (locale == null) {
            locale = new Locale(ConfigInput.language, ConfigInput.country);
        }
        try {
            resourceBundle = ResourceBundle.getBundle("messages", locale);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean loadLabelBundle(Locale locale) {
        if (locale == null) {
            locale = new Locale(ConfigInput.language, ConfigInput.country);
        }
        try {
            labelBundle = ResourceBundle.getBundle("labels", locale);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static String getMessage(String code, Locale locale) {
        if (resourceBundle == null || locale != null) {
            if (!loadResourceBundle(locale))
                return "";
        }

        String str = "";
        try {
            str = resourceBundle.getString(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.trim();
    }

    public static String getLabel(String code, Locale locale) {
        if (labelBundle == null || locale != null) {
            if (!loadLabelBundle(locale))
                return "";
        }

        String str = "";
        try {
            str = labelBundle.getString(code);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.trim();
    }

}
