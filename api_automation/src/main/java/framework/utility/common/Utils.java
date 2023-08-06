package framework.utility.common;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import framework.utility.globalConst.Constants;
import framework.utility.reportManager.ScreenShot;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);
    private static WebDriver driver;

    public static void restoreWindowSize(WebDriver driver) {
        driver.manage().window().setSize(new Dimension(768, 768));
    }

    public static void maximizeWindowsSize(WebDriver driver) {
        driver.manage().window().maximize();
    }

    public static void setValueUsingJs(WebElement elem, String value) {
        driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='" + value + "';", elem);
    }

    public static void setCssUsingJs(String attribute, String value, WebElement elem) {
        String val = attribute + ":" + value;
        driver = DriverFactory.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', '" + val + "')", elem);
    }

    public static void highLightElement(WebElement webElement) {
        JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();
        js.executeScript("arguments[0].setAttribute('style','background-color:yellow; border: 2px solid red;');", webElement);
    }

    /**
     * Switch to Frame
     *
     * @param frame
     */
    public static void switchToFrame(int frame) {
        DriverFactory.getDriver().switchTo().defaultContent();
        DriverFactory.getDriver().switchTo().frame(frame);
        System.out.println("Switching to frame with id " + frame);
    }

    public static void switchToContentFrame() throws Exception {
        try {
            WebDriver driver = DriverFactory.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
            Thread.sleep(Constants.MAX_WAIT_TIME);
            System.out.println("Switch to Content Frame");
        } catch (Exception e) {
            e.printStackTrace();
            Assertion.markAsFailure(e.getMessage());
        }
    }

    public static void switchToContentFrame(int index) throws Exception {
        try {
            WebDriver driver = DriverFactory.getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
            driver.switchTo().defaultContent();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
            Thread.sleep(Constants.MAX_WAIT_TIME);
            System.out.println("Switch to Content Frame");
        } catch (Exception e) {
            e.printStackTrace();
            Assertion.markAsFailure(e.getMessage());
        }
    }

    public static void switchToDefaultContent() {
        DriverFactory.getDriver().switchTo().defaultContent();
        logger.info("Switch To Default Content");
        try {
            Thread.sleep(Constants.MAX_WAIT_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * scroll To Bottom Of Page
     */
    public static void scrollToBottomOfPage() {
        driver = DriverFactory.getDriver();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToBottomOfPage(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void scrollToMiddleOfPage() {
        driver = DriverFactory.getDriver();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 500)");
    }

    public static void scrollToTopOfPage() throws Exception {
        driver = DriverFactory.getDriver();
        Utils.restoreWindowSize(driver);
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
        Thread.sleep(12000);
        Utils.maximizeWindowsSize(driver);

    }

    public static void scrollUntilElementIsVisible(WebElement element) {
        driver = DriverFactory.getDriver();
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView()", element);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scroll to a particular element
     *
     * @param scrollArea
     */
    public static void scrollToAnElement(WebElement scrollArea) {
        driver = DriverFactory.getDriver();
        Actions actions = new Actions(driver);
        actions.moveToElement(scrollArea);
        actions.perform();
    }


    public static void checkElementIsEditable(String elemValue, ExtentTest t1) {
        try {
            WebElement check = DriverFactory.getDriver().findElement(By.xpath(elemValue));
            t1.info("Element found");
            if (!check.getTagName().contains("label")) {
                if (check.getAttribute("readonly").contains("true")) {
                    t1.pass("Element have readonly Attribute ");
                } else {
                    t1.fail("Element dose not have readonly Attribute ");
                }
                captureScreen(t1);
            } else {
                t1.info("Element is Label ");
            }

        } catch (Exception e) {
            t1.fail("Element is Not Verified ");
        }
    }


    /**
     * put Thread Sleep
     *
     * @param miliSecond
     */
    public static void putThreadSleep(int miliSecond) {
        try {
            Thread.sleep(miliSecond);
        } catch (InterruptedException e) {
            System.err.println("Interrupted Exception occurred :" + e);
        } catch (Exception e) {
            System.err.println("Exception occurred :" + e);
        }
    }

    /**
     * Capture screenshot and attach to report log
     *
     * @param t1
     * @throws IOException
     */
    public static void captureScreen(ExtentTest t1) throws IOException {
        t1.info("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
    }

    public static void captureScreen(WebDriver driver, ExtentTest t1) throws IOException {
        t1.info("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen(driver)).build());
    }

    public static void captureScreenAsPass(ExtentTest t1) throws IOException {
        t1.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen(driver)).build());
    }

    public static void captureScreenAsFailure(ExtentTest t1) throws IOException {
        t1.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
    }

    public static void captureScreenAsWarning(ExtentTest t1) throws IOException {
        t1.warning("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
    }

    public static void createDirectoryIfNotPresent(String dirPath) {
        File dirToCheck = new File(dirPath);
        if (!dirToCheck.exists())
            dirToCheck.mkdir();
    }

    public static void createFileIfNotPresent(String dirPath) throws IOException {
        File file = new File(dirPath);
        if (!file.exists())
            file.createNewFile();
    }

    public static boolean checkIfContentFrameExist(ExtentTest t1) throws Exception {
        try {
            DriverFactory.getDriver().switchTo().defaultContent();
            Thread.sleep(1000);
            DriverFactory.getDriver().switchTo().frame(0);
            Thread.sleep(1000);
            t1.pass("Successfully switched to Content Frame");
            return true;
        } catch (NoSuchFrameException e) {
            return false;
        }
    }

    public static String switchToCurrentTab() {
        WebDriver driver = DriverFactory.getDriver();
        String currentTab = driver.getWindowHandle();
        for (String tab : driver.getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                driver.switchTo().window(tab);
                return tab;
            }
        }
        return null;
    }

    public static String switchToChildWindow() throws Exception {
        try {
            WebDriver driver = DriverFactory.getDriver();
            Thread.sleep(Constants.MAX_WAIT_TIME);
            String parent = driver.getWindowHandle();
            logger.info("Parent Window Handle: " + parent);
            Set<String> s1 = driver.getWindowHandles();
            Iterator<String> I1 = s1.iterator();

            while (I1.hasNext()) {
                String child_window = I1.next();
                if (!parent.equals(child_window)) {
                    driver.switchTo().window(child_window);
                    logger.info("Switch to Child Window: " + child_window);
                }
            }
            return parent;
        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        } finally {
            Utils.switchToContentFrame();
        }
        return null;
    }


    /**
     * Switch to main Window
     *
     * @param mainHandle
     */
    public static void switchToMainWindow(String mainHandle) throws Exception {
        WebDriver driver = DriverFactory.getDriver();
        Set<String> listHandles = driver.getWindowHandles();

        for (String handle : listHandles) {
            if (!handle.equalsIgnoreCase(mainHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(mainHandle);
        logger.info("Switch to Main Window.");
    }

    public static String switchToWindowWithoutClosingOtherTabs(String targetWindow) {
        WebDriver driver = DriverFactory.getDriver();
        String currentHandle = driver.getWindowHandle();
        Set<String> listHandles = driver.getWindowHandles();

        for (String handle : listHandles) {
            if (handle.equalsIgnoreCase(targetWindow)) {
                driver.switchTo().window(targetWindow);
                break;
            }
        }
        System.out.println("Switch to Target Window having handle: " + targetWindow);
        return currentHandle;
    }

    public static String getMainWindowHandle() {
        return DriverFactory.getDriver().getWindowHandle();
    }

    public static void clearCsv(String fileName) {
        try {
            FileWriter fw = null;
            fw = new FileWriter(fileName, false);
            PrintWriter pw = new PrintWriter(fw, false);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            Assertion.failAndStopTest("Exception " + e);
        }

    }

    public static void pageRefresh() throws Exception {
        DriverFactory.getDriver().navigate().refresh();
        Thread.sleep(8000);
    }

    /**
     * Switch To WorkSpace Tab
     *
     * @param workSpaceName
     * @deprecated - this is a work around and must not be scaled further
     */
    public static void switchToWorkSpaceTab(String workSpaceName) throws Exception {
        WebDriver driver = DriverFactory.getDriver();
        Set<String> listHandles = null;
        int i = 0;
        while (i < 5) {
            listHandles = driver.getWindowHandles();
            if (listHandles.size() > 1) {
                break;
            } else {
                Thread.sleep(Constants.MAX_WAIT_TIME);
            }
        }

        String wsHandle = null;
        for (String handle : listHandles) {
            driver.switchTo().window(handle);
            Thread.sleep(Constants.MAX_WAIT_TIME);
            if (driver.getTitle().contains(workSpaceName)) {
                wsHandle = handle;
            } else {
                driver.close();
            }
        }
        if (wsHandle != null) {
            driver.switchTo().window(wsHandle);
            System.out.println("Switch to New Tab:" + workSpaceName);
        }
    }

    public static void switchToScenarioTab() throws Exception {
        WebDriver driver = DriverFactory.getDriver();
        Set<String> listHandles = driver.getWindowHandles();

        String wsHandle = null;
        for (String handle : listHandles) {
            driver.switchTo().window(handle);
            Thread.sleep(Constants.MAX_WAIT_TIME);
            if (driver.getTitle().contains("Scenario")) {
                wsHandle = handle;
            } else {
                driver.close();
                Thread.sleep(Constants.MAX_WAIT_TIME);
            }
        }
        if (wsHandle != null) {
            driver.switchTo().window(wsHandle);
            System.out.println("Switch to Scenario Tab.");
        }
    }

    public static String getLatestFileFromTheDirectory(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }

        File lastModifiedFile = files[0];
        for (int i = 1; i < files.length; i++) {
            if (files[i].getName().equalsIgnoreCase("screenshots"))
                continue;

            if (lastModifiedFile.lastModified() < files[i].lastModified()) {
                lastModifiedFile = files[i];
            }
        }
        return lastModifiedFile.getName();
    }


    public static boolean deleteFilesForPathByPrefix(final String path, final String prefix) {
        boolean success = true;
        try (DirectoryStream<Path> newDirectoryStream = Files.newDirectoryStream(Paths.get(path), prefix + "*")) {
            for (final Path newDirectoryStreamItem : newDirectoryStream) {
                Files.delete(newDirectoryStreamItem);
            }
        } catch (final Exception e) {
            success = false;
            e.printStackTrace();
        }
        Utils.putThreadSleep(Constants.MAX_WAIT_TIME);
        return success;
    }

    public static String getFlooredMetricData(double number) {
        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(number);
    }

    public static String getDifference(String a, String b) {
        return getFlooredMetricData(Double.parseDouble(a) - Double.parseDouble(b));
    }

    public static String setDateInFormat(String inputDate,String originalFormat,String targetFormat){
        SimpleDateFormat DATE_ORIGINAL_FORMAT = new SimpleDateFormat(originalFormat);
        SimpleDateFormat DATE_TARGET_FORMAT = new SimpleDateFormat(targetFormat);

        Date yourDate = null;
        try {
            yourDate = DATE_ORIGINAL_FORMAT.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String date = DATE_TARGET_FORMAT.format(yourDate);
        System.out.println("Date after conversion is:"+date);
        return date;
    }

}