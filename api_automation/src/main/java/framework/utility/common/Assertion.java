package framework.utility.common;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.Constants;
import framework.utility.propertiesManager.MessageReader;
import framework.utility.reportManager.ScreenShot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Assertion {
    private static WebDriverWait wait, smallWait, longWait;
    private static WebDriver driver;
    private static SoftAssert sAssert;

    public static void init(ExtentTest t1) {
        driver = DriverFactory.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT_TIME));
        smallWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_SMALL_WAIT_TIME));
        longWait = new WebDriverWait(driver, Duration.ofSeconds(Constants.NOTIFICATION_WAIT_TIME));
    }

    public static void markTestAsFailure(String message, ExtentTest t1) {
        t1.fail(message);
        try {
            t1.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void markAsFailure(String message) {
        getSoftAssert().fail(message);
    }

    public static void markAsFailure(String message, ExtentTest node) {
        node.fail(message);
        try {
            Utils.captureScreenAsFailure(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        getSoftAssert().fail(message);
    }

    public static SoftAssert getSoftAssert() {
        if (sAssert == null) {
            sAssert = new SoftAssert();
        }
        return sAssert;
    }

    public static void finalizeSoftAsserts() {
        try {
            getSoftAssert().assertAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            resetSoftAsserts();
        }
    }

    public static void resetSoftAsserts() {
        if (sAssert == null) {
            return;
        } else {
            sAssert = null;
        }
    }

    public static void failAndStopTest(String message) {
        markAsFailure(message);
        Assert.fail(message);
    }

    /**
     * Verify For Equal with min difference , comparison  1.5 and 1 will return true
     *
     * @param actual
     * @param expected
     * @param message
     * @param node
     * @throws IOException
     */
    public static boolean verifyIntegerEquality(Object actual, Object expected, String message, ExtentTest node, boolean... takeScreenShot) throws IOException {
        String code[][] = {{"Verify Integer Equality", message}, {"Actual", actual.toString()}, {"Expected", expected.toString()}};
        Markup m = MarkupHelper.createTable(code);

        int i = (int) Double.parseDouble(actual + "");
        int j = (int) Double.parseDouble(expected + "");
        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;

        if (i == j) {
            node.pass(m);
            if (takeSnap)
                node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            return true;
        } else {
            node.fail(m);
            if (takeSnap)
                node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            markAsFailure("Failed to verify for equality!");
        }
        return false;
    }

    public static boolean verifyEqual(Object actual, Object expected, String message, ExtentTest node, boolean... takeScreenShot) throws IOException {
        String code[][] = {{"Verify", message}, {"Actual", actual.toString()}, {"Expected", expected.toString()}};
        Markup m = MarkupHelper.createTable(code);

        boolean isEqual = false;
        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;
        if (actual instanceof BigDecimal && expected instanceof BigDecimal) {
            BigDecimal act = (BigDecimal) actual;
            BigDecimal exp = (BigDecimal) expected;
            isEqual = (act.compareTo(exp) == 0);
        } else if (actual instanceof Integer && expected instanceof Integer) {
            isEqual = actual == expected;
        } else if (actual instanceof String && expected instanceof String) {
            isEqual = actual.equals(expected);
        } else if (actual instanceof List && expected instanceof List) {
            isEqual = actual.equals(expected);
        } else if (actual instanceof Boolean && expected instanceof Boolean) {
            isEqual = actual == expected;
        } else if (actual instanceof Double && expected instanceof Double) {
            Double a = (Double) actual;
            Double b = (Double) expected;
            if (Double.compare(a, b) == 0) {
                isEqual = true;
            }
        }

        if (isEqual) {
            node.pass(m);
            if (takeSnap)
                node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            return true;
        } else {
            node.fail(m);
            if (takeSnap)
                node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            markAsFailure("Failed to verify for equality!");
        }
        return false;
    }

    public static boolean verifyIncremented(String first, String second, String message, ExtentTest node) throws IOException {
        String code[][] = {{"Verify ", message}, {"First Number", first.toString()}, {"Second Number", second.toString()}};
        Markup m = MarkupHelper.createTable(code);

        Double a = Double.parseDouble(first);
        Double b = Double.parseDouble(second);
        if (Double.compare(a, b) > 0) {
            node.pass(m);
            return true;
        } else {
            node.fail(m);
            markAsFailure("Failed to verify for Increment!");
        }
        return false;
    }


    public static boolean verifyContains(String actual, String expected, String message, ExtentTest node, boolean... takeScreenShot) throws IOException {
        String code[][] = {{"Verify", message}, {"Actual", actual}, {"Expected", expected}};
        Markup m = MarkupHelper.createTable(code);
        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;

        if (actual.contains(expected)) {
            node.pass(m);
            if (takeSnap)
                node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            return true;
        } else {
            node.fail(m);
            if (takeSnap)
                node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            markAsFailure("Failure: Verify Action message Contain:- " + expected);
        }
        return false;
    }

    public static boolean verifyDateEqual(String actual, String expected, String message, ExtentTest node, boolean... takeScreenShot) throws IOException, ParseException {
        String code[][] = {{"Verify", message}, {"Actual", actual}, {"Expected", expected}};
        Markup m = MarkupHelper.createTable(code);

        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;

        SimpleDateFormat sdfo = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdfo.parse(actual);
        Date d2 = sdfo.parse(expected);
        if (d1.compareTo(d2) == 0) {
            node.pass(m);
            if (takeSnap)
                node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            return true;
        } else {
            node.fail(m);
            if (takeSnap)
                node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            markAsFailure("Failed to verify for equality!");
        }
        return false;
    }


    public static boolean verifyDateIsLess(String actual, String expected, String message, ExtentTest node, boolean... takeScreenShot) throws IOException, ParseException {
        String code[][] = {{"Verify", message}, {"Actual", actual}, {"Expected", expected}};
        Markup m = MarkupHelper.createTable(code);

        boolean takeSnap = takeScreenShot.length > 0 ? takeScreenShot[0] : true;

        SimpleDateFormat sdfo = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdfo.parse(actual);
        Date d2 = sdfo.parse(expected);
        if (d1.compareTo(d2) < 0) {
            node.pass(m);
            if (takeSnap)
                node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            return true;
        } else {
            node.fail(m);
            if (takeSnap)
                node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());

            markAsFailure("Failed to verify for equality!");
        }
        return false;
    }


    /**
     * Get Action Message
     *
     * @return
     */
    public static String getNotificationMessage() {
        try {
            String notificationText = "";
            for (int i = 0; i < 4; i++) {
                notificationText = longWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("slds-notify__content"))).getText();
                if (notificationText != null && !notificationText.equalsIgnoreCase(""))
                    return notificationText;
                else
                    Thread.sleep(500);
            }

        } catch (Exception e) {
            Assertion.markAsFailure("Failed to get the Notification Message");
        }
        return null;
    }

    public static String getToasterNotification() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".forceActionsText.toastMessage"))).getText();
    }

    public static String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("errorsList"))).getText();
    }

    public static String getUiErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("uiInputDefaultError"))).getText();
    }

    public static String getAsErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AS_errorPopover"))).getText().split("\n")[0];
    }

    /**
     * Verify Notification message
     *
     * @param expected
     * @param message
     * @param node
     * @param variation
     * @return
     * @throws Exception
     */
    public static Boolean verifyNotificationMsg(String expected, String message, ExtentTest node, String... variation) throws Exception {
        try {
            String expectedMsg = null;
            String actualNotification = getNotificationMessage();
            node.info("Actual Notification: " + actualNotification);
            if (variation.length > 0)
                expectedMsg = MessageReader.getDynamicMessage(expected, variation);
            else
                expectedMsg = MessageReader.getMessage(expected, null);

            return verifyContains(actualNotification, expectedMsg, message, node);
        } catch (Exception e) {
            Assertion.raiseExceptionAndContinue(e, node);
        }
        return false;
    }

    public static Boolean verifyNotificationContains(String expected, String message, ExtentTest node, String... variation) throws Exception {
        try {
            String actualNotification = getNotificationMessage();
            return verifyContains(actualNotification, expected, message, node);
        } catch (Exception e) {
            Assertion.raiseExceptionAndContinue(e, node);
        }
        return false;
    }

    /**
     * Verify Notification message
     *
     * @param expected
     * @param message
     * @param node
     * @param variation
     * @return
     * @throws Exception
     */
    public static Boolean verifyToasterNotificationMsg(String expected, String message, ExtentTest node, String... variation) throws Exception {
        try {
            String expectedMsg = null;
            String actualNotification = getToasterNotification();

            if (variation.length > 0)
                expectedMsg = MessageReader.getDynamicMessage(expected, variation);
            else
                expectedMsg = MessageReader.getMessage(expected, null);

            return verifyContains(actualNotification, expectedMsg, message, node);
        } catch (Exception e) {
            Assertion.raiseExceptionAndContinue(e, node);
        }
        return false;
    }

    public static Boolean verifyNotificationMsgLongWait(String expected, String message, ExtentTest node, String... variation) throws Exception {
        try {
            String expectedMsg = null;

            String actualNotification = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(450))
                    .until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("slds-notify__content")))).getText();

            Utils.captureScreen(node);

            if (variation.length > 0)
                expectedMsg = MessageReader.getDynamicMessage(expected, variation);
            else
                expectedMsg = MessageReader.getMessage(expected, null);

            return verifyContains(actualNotification, expectedMsg, message, node);
        } catch (Exception e) {
            Assertion.raiseExceptionAndContinue(e, node);
        }
        return false;
    }


    /**
     * Verify for error message
     *
     * @param expectedCode - expected code as present in Property resource bundle
     * @param message      - User defined message to describe the step verifiction
     * @param node         - Extent test, to which the verification logs to be attached
     * @return
     * @throws Exception
     */
    public static boolean verifyErrorMessage(String expectedCode, String message, ExtentTest node, String... variation) throws Exception {
        String expectedMessage = null;
        if (variation.length > 0)
            expectedMessage = MessageReader.getDynamicMessage(expectedCode, variation);
        else
            expectedMessage = MessageReader.getMessage(expectedCode, null);

        String actualMessage = getErrorMessage();

        String code[][] = {{"Verify", message}, {"Actual", actualMessage.toString()}, {"Expected", expectedMessage.toString()}};
        Markup m = MarkupHelper.createTable(code);

        if (actualMessage.contains(expectedMessage)) {
            node.pass(m);
            node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            return true;
        } else {
            node.fail(m);
            node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            markAsFailure("Failure: Verify Action message Contain:- " + expectedMessage);
            return false;
        }
    }

    /**
     * verifyErrorPopoverHeader on Roster Create Position Module
     *
     * @param -         expected code as present in Property resource bundle
     * @param message   - User defined message to describe the step verification
     * @param node      - Extent test, to which the verification logs to be attached
     * @param variation
     * @return
     * @throws Exception
     */
    public static boolean verifyErrorPopoverHeader(String expectedCode, String message, ExtentTest node, String... variation) throws Exception {
        String expectedMessage = null;
        if (variation.length > 0)
            expectedMessage = MessageReader.getDynamicMessage(expectedCode, variation);
        else
            expectedMessage = MessageReader.getMessage(expectedCode, null);
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("slds-popover__body"))).getText().split("\n")[1];
        String code[][] = {{"Verify", message}, {"Actual", actualMessage.toString()}, {"Expected", expectedMessage.toString()}};
        Markup m = MarkupHelper.createTable(code);

        if (actualMessage.contains(expectedMessage)) {
            node.pass(m);
            node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            return true;
        } else {
            node.fail(m);
            node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            markAsFailure("Failure: Verify Action message Contain:- " + expectedMessage);
            return false;
        }
    }


    /**
     * verifyErrorPopoverHeader on Create Position Module on Alignment
     *
     * @param -         expected code as present in Property resource bundle
     * @param message   - User defined message to describe the step verification
     * @param node      - Extent test, to which the verification logs to be attached
     * @param variation
     * @return
     * @throws Exception
     */
    public static boolean verifyErrorPopoverHeaderOnPositionModule(String expectedCode, String message, ExtentTest node, String... variation) throws Exception {
        String expectedMessage = null;
        if (variation.length > 0)
            expectedMessage = MessageReader.getDynamicMessage(expectedCode, variation);
        else
            expectedMessage = MessageReader.getMessage(expectedCode, null);
        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorMsgDiv"))).getText();
        String code[][] = {{"Verify", message}, {"Actual", actualMessage.toString()}, {"Expected", expectedMessage.toString()}};
        Markup m = MarkupHelper.createTable(code);

        if (actualMessage.contains(expectedMessage)) {
            node.pass(m);
            node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            return true;
        } else {
            node.fail(m);
            node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            markAsFailure("Failure: Verify Action message Contain:- " + expectedMessage);
            return false;
        }
    }

    /**
     * verifyErrorPopover on Advanced Search
     *
     * @param -         expected code as present in Property resource bundle
     * @param message   - User defined message to describe the step verification
     * @param node      - Extent test, to which the verification logs to be attached
     * @param variation
     * @return
     * @throws Exception
     */
    public static boolean verifyAsErrorPopover(String expectedCode, String message, ExtentTest node, String... variation) throws Exception {
        String expectedMessage = null;
        if (variation.length > 0)
            expectedMessage = MessageReader.getDynamicMessage(expectedCode, variation);
        else
            expectedMessage = MessageReader.getMessage(expectedCode, null);

        String actualMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("AS_errorPopover"))).getText().replace("\n1", " ");
        String code[][] = {{"Verify", message}, {"Actual", actualMessage.toString()}, {"Expected", expectedMessage.toString()}};
        Markup m = MarkupHelper.createTable(code);

        if (actualMessage.contains(expectedMessage)) {
            node.pass(m);
            node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            return true;
        } else {
            node.fail(m);
            node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            markAsFailure("Failure: Verify Action message Contain:- " + expectedMessage);
            return false;
        }
    }

    /**
     * Verify for UI error message
     *
     * @param expectedCode - expected code as present in Property resource bundle
     * @param message      - User defined message to describe the step verifiction
     * @param node         - Extent test, to which the verification logs to be attached
     * @return
     * @throws Exception
     */
    public static boolean verifyUiErrorMessage(String expectedCode, String message, ExtentTest node, String... variation) throws Exception {
        String expectedMessage = null;
        if (variation.length > 0)
            expectedMessage = MessageReader.getDynamicMessage(expectedCode, variation);
        else
            expectedMessage = MessageReader.getMessage(expectedCode, null);

        String actualMessage = getUiErrorMessage();

        String code[][] = {{"Verify", message}, {"Actual", actualMessage.toString()}, {"Expected", expectedMessage.toString()}};
        Markup m = MarkupHelper.createTable(code);

        if (actualMessage.contains(expectedMessage)) {
            node.pass(m);
            node.pass("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            return true;
        } else {
            node.fail(m);
            node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
            markAsFailure("Failure: Verify Action message Contain:- " + expectedMessage);
            return false;
        }
    }


    public static void raiseExceptionAndStop(Exception e, ExtentTest node) throws IOException {
        node.fail(e);
        if(ConfigInput.test_type.equalsIgnoreCase("ui")) {
            node.fail("", MediaEntityBuilder.createScreenCaptureFromPath(ScreenShot.captureScreen()).build());
        }
        e.printStackTrace();
        System.err.print("Stopping the execution, as an Exception has been Raised, Refer Reports for More Details!");
        Assertion.finalizeSoftAsserts();
        Assert.fail(e.getMessage());
    }

    public static void raiseExceptionAndContinue(Exception e, ExtentTest node) throws IOException {
        e.printStackTrace();
        node.fail(e);
        markAsFailure(e.getMessage());
        Utils.captureScreen(node);
    }

}
