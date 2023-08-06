package framework.utility.common;

import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Start {

    private static Logger logger = LoggerFactory.getLogger(Start.class.getName());

    public static void openRegistrationPage() {
        DriverFactory.getDriver().get(ConfigInput.url);
        logger.info("Open User Registration Page. URL: " + ConfigInput.url);


    }
}
