package framework.utility.reportManager;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import framework.utility.globalConst.FilePath;
import framework.utility.globalVars.GlobalData;

import java.io.File;


public class ExtentManager {
    private static ExtentReports extent;
    private static String fileName;
    private static String filePath;

    public static ExtentReports getInstance() {
        if (fileName == null) {
            return null;
        }

        if (extent == null) {
            String tDate = java.time.LocalDate.now().toString();
            GlobalData.reportDir = FilePath.dirReports + tDate;
            GlobalData.screenshotDir = GlobalData.reportDir + "/screenshots";
            GlobalData.screenshotPath = "../" + tDate + "/screenshots";
            File reportDir = new File(GlobalData.reportDir);

            if (!reportDir.exists()) {
                reportDir.mkdir();
            }

            File screenShotDir = new File(GlobalData.screenshotDir);
            if (!screenShotDir.exists()) {
                screenShotDir.mkdir();
            }

            filePath = GlobalData.reportDir + "/" + fileName;
            extent = createInstance();
        }

        return extent;
    }

    public static void extentFlush() {
        extent.flush();
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fName) {
        fileName = fName;
    }

    public static ExtentReports createInstance() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(filePath);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);

        extent = new ExtentReports();
        extent.setAnalysisStrategy(AnalysisStrategy.TEST);
        extent.attachReporter(htmlReporter);
        extent.flush();

        return extent;
    }
}
