package generalTestingUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Prateek Sethi
 */
public class TestOverrideTimeZone {

    //https://mkyong.com/java/java-display-list-of-timezone-with-gmt/

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        DevTools devTools=((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        Map<String,Object> map = new HashMap<>();
        map.put("timezoneId","America/Antigua");
        ((ChromeDriver)driver).executeCdpCommand("Emulation.setTimezoneOverride",map);
        driver.get("https://customeriq-qa.axtria.com/");
    }
}
