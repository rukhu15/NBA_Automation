package framework.utility.common;

import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;

public class DriverFactory {

    private static final String CHROME = "chrome";
    public static final String IE = "ie";
    public static RemoteWebDriver driver;
    private static Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public static RemoteWebDriver getDriver() {

        if (driver == null & ConfigInput.test_type.equalsIgnoreCase("ui")) {
            driver = createDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT_TIME));
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static RemoteWebDriver createDriver() {
        logger.info("Create Selenium WebDriver...");
        try {
            ChromeOptions chromeOptions=null;
            if (ConfigInput.browser.equalsIgnoreCase(CHROME) ) {
                if(!ConfigInput.debugMode.isEmpty()
                        && ConfigInput.debugMode.equalsIgnoreCase("y")){
                    chromeOptions=setDebugChromeOptions();
                }else{
                    chromeOptions=setChromeOptions();
                }

                if(ConfigInput.executionMode.equalsIgnoreCase("remote")){
                    String hubUrl = ConfigInput.hubURL;
                    driver= new RemoteWebDriver(
                            new URL(hubUrl),chromeOptions);
                }else {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(chromeOptions);
                }
            }
            else if (ConfigInput.browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                driver= new EdgeDriver();
            }else if(ConfigInput.browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver=new FirefoxDriver();
            }

            else {
                Assertion.markAsFailure("Provide correct Browser Type");
            }

        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        }
        return driver;
    }

    public static ChromeOptions setChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.addArguments("--incognito");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        return options;
    }

    public static ChromeOptions setDebugChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("debuggerAddress","localhost:8989");
        return options;
    }

    public static String getCurrentTitle() {
        return driver.getTitle();
    }

    public static void closeDriver() {
        if (driver == null)
            return;

        driver.close();
        driver = null;
    }

    public static void quitDriver() {
        if (driver == null)
            return;

        driver.quit();
        driver = null;
    }

}
