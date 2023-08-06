package scripts;


import application.pageObjects.HomePage;
import application.pageObjects.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.*;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.FilePath;
import framework.utility.reportManager.ExtentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;

/**
 * Created by Automation Team
 */
public class TestInit {
    private static final Logger logToConsole = LoggerFactory.getLogger(TestInit.class);
    protected static ExtentReports extent;
    protected static String suiteName = null, fileName = null;
    protected ExtentTest pNode;


    @BeforeSuite(alwaysRun = true)
    @Parameters({"aut_prop_file_name"})
    public void beforeSuite(ITestContext ctx,String aut_prop_file_name) {

        try {

            /**
             * if the suite is executed from XML then create extent file with Suite Name mentioned in the xml,
             * else
             * use the Class name.
             */
            if (ExtentManager.getFileName() == null) {
                suiteName = (ctx.getSuite().getName().
                        equalsIgnoreCase("Default Suite")) ? getClass().getSimpleName() : ctx.getSuite().getName();
                String timeStamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm").format(new Date());
                //ExtentManager.setFileName(suiteName + "_" + timeStamp + ".html");
                ExtentManager.setFileName(suiteName + ".html");
            }

            // load the automation.properties file
            ConfigInput.init(aut_prop_file_name);

            // initialize Extent reports
            extent = ExtentManager.getInstance();
            extent.setSystemInfo("Application URL", ConfigInput.url);
            extent.setSystemInfo("Browser", ConfigInput.browser);
            extent.setSystemInfo("Country", ConfigInput.defaultCountry);

            Utils.createDirectoryIfNotPresent(FilePath.dirFailSuitesPath);
            Utils.createDirectoryIfNotPresent(FilePath.dirFailTestRecord);
            Utils.createDirectoryIfNotPresent(FilePath.dirDailyExecStatusLog);

            if( ConfigInput.test_type.equalsIgnoreCase("ui") ||  ConfigInput.test_type.isEmpty()) {
                DriverFactory.getDriver();
            }

        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
            e.printStackTrace();
        } finally {
            Assertion.finalizeSoftAsserts();
        }

    }

    @BeforeClass(alwaysRun = true)
    public void beforeClassRun() throws Exception {
        // create the parent node
        if (pNode == null)
            pNode = extent.createTest(getClass().getSimpleName());
        if( ConfigInput.test_type.equalsIgnoreCase("ui") ||  ConfigInput.test_type.isEmpty()) {
            DriverFactory.getDriver(); // init Driver
            Assertion.init(pNode);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        System.out.print("START TEST: " + method.getName() + "\n");
        try {
            Assertion.resetSoftAsserts();
            Utils.switchToDefaultContent();
        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, Method method) throws IOException {
        try {
            String dependentOnMethod = method.getDeclaredAnnotations()[0].toString().split("dependsOnMethods=")[1].split(",")[0].replace("[", "").replace("]", "");
            if (result.getStatus() == ITestResult.SUCCESS) {
                logToConsole.info("TEST         >---> " + result.getName() + " - Executed Successfully");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                logToConsole.info("TEST         >---> " + result.getName() + " - Execution Failed");

                // update the Execution log files
                //updateFailExecutionLog(result.getInstanceName(), result.getName(), dependentOnMethod);

            } else if (result.getStatus() == ITestResult.SKIP) {
                logToConsole.info("TEST         >---> " + result.getName() + " - Execution Skipped");
                ExtentTest test = pNode.createNode(result.getName() + ": Test Case Execution Skipped!");
                test.skip("Test Case Skipped");

                // update the Execution log files
                //updateFailExecutionLog(result.getInstanceName(), result.getName(), dependentOnMethod);
            } else {
                ExtentTest test = pNode.createNode(result.getName() + ": Add Test Case Execution Type in TestInit class");
                test.skip("Test Case type");
            }

            logToConsole.info("END TEST     >---> " + result.getName());
            logToConsole.info("---/");
            extent.flush();
            Assertion.resetSoftAsserts();
            ConfigInput.isAssert = true;
            ConfigInput.isConfirm = true;
            Utils.switchToDefaultContent();
        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        }

    }

    @AfterClass(alwaysRun = true)
    public void afterClassRun() throws Exception {
       HomePage.init(pNode).navigateToHomePage();
    	//To-Do add click to home page.
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        try {
            if( ConfigInput.test_type.equalsIgnoreCase("ui") ||  ConfigInput.test_type.isEmpty()) {
                DriverFactory.quitDriver();
            }
           // ReRunSuite.createReRunSuite(suiteName);
            
            //closing Athena connection in case connection established in our Automation Suite
            AthenaDataUtil.closeAthenaConnection();
            String scenarioName=System.getProperty("scenarioName");
            DatabaseUtil.deleteExistingAutomationScenario(scenarioName);
        } catch (Exception e) {
            Assertion.markAsFailure(e.getMessage());
        }

    }


    protected void markSetupAsFailure(Exception e) {
        try {
            Utils.captureScreenAsFailure(pNode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        pNode.fail("Exception: " + e.toString());
        Assert.fail(e.getMessage());
    }

    public void markTestAsFailure(Exception e, ExtentTest t1) {
        try {
            if( ConfigInput.test_type.equalsIgnoreCase("ui") ||  ConfigInput.test_type.isEmpty()) {
                Utils.captureScreenAsFailure(t1);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
        t1.fail("Test Case Failed due to Exception");
        t1.error(e);
        Assert.fail(e.getMessage());
    }

    public void startNegativeTest() {
        ConfigInput.isAssert = false;
    }

    public void skipConfirm() {
        ConfigInput.isConfirm = false;
        ConfigInput.isAssert = false;
    }

    public void resumeNegativeTest() {
        ConfigInput.isAssert = true;
    }

    public void resumeConfirm() {
        ConfigInput.isConfirm = true;
        ConfigInput.isAssert = true;
    }

    /**
     * Update Log Files with current test status
     *
     * @param suiteName
     * @param methodName
     * @throws IOException
     */
    private void updateFailExecutionLog(String suiteName, String methodName, String dependentOnMethod) throws IOException {
        /**
         * Update daily log as well as the latest log
         * add timestamp to all entry
         */
        String tDate = java.time.LocalDate.now().toString();
        String tStamp = java.time.LocalTime.now().toString();

        File logFile = new File(FilePath.dirDailyExecStatusLog + tDate + ".csv");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File logFile1 = new File(FilePath.fileCurrentExecutionLog);
        if (!logFile1.exists()) {
            try {
                logFile1.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
        out.newLine();
        out.write(tStamp + "," +
                suiteName + "," +
                methodName + "," +
                dependentOnMethod);
        out.close();

        BufferedWriter out1 = new BufferedWriter(new FileWriter(FilePath.fileCurrentExecutionLog, true));
        out1.newLine();
        out1.write(tStamp + "," +
                suiteName + "," +
                methodName + "," +
                dependentOnMethod);
        out1.close();
    }

}
