package framework.utility.reportManager;

import framework.utility.common.DriverFactory;
import framework.utility.globalVars.GlobalData;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenShot {

    public static String captureScreen() {
        String fileName = System.currentTimeMillis() + ".png";
        try {
            File src = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(GlobalData.screenshotDir + "/" + fileName));
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return GlobalData.screenshotPath + "/" + fileName;
    }

    public static String captureScreen(WebDriver driver) {
        String fileName = System.currentTimeMillis() + ".png";
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(GlobalData.screenshotDir + "/" + fileName));
        } catch (Exception ex) {
            System.err.println(ex);
        }
        return GlobalData.screenshotPath + "/" + fileName;
    }

}
