package application.businessrules;

import com.aventstack.extentreports.ExtentTest;

import application.pageObjects.PageInit;
import framework.utility.common.Assertion;
import framework.utility.common.DriverFactory;
import framework.utility.common.Utils;
import framework.utility.globalConst.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class TopMenu extends PageInit {

    @FindBy(linkText = "Log Out")
    WebElement linkLogOut;

    @FindBy(xpath= "//div[@aria-label='recipe']")
    WebElement profileIcon;

    @FindBy(xpath = "//li[contains(text(),'Change Workspace')]")
    WebElement changeWorkspace;

    @FindBy(xpath = "//div[@class='spinner']")
    WebElement spinner;

    public void navCountry() throws Exception {
        navTopMenu("Country");
    }

    /**
     * get all links in oneAppNavContainer, find the linkName > navigate to the same, wiat for page load and return
     *
     * @param linkName
     * @throws Exception
     */
    public void navTopMenu(String linkName) throws Exception {
        List<WebElement> links = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oneAppNavContainer")))
                .findElements(By.xpath(".//a"));

        for (WebElement elem : links) {
            if (elem.getAttribute("title").equalsIgnoreCase(linkName)) {
                // get the current URL and Append the Link URL
                DriverFactory.getDriver().get(elem.getAttribute("href"));
                break;
            }
        }

        int count = 0;
        while (!DriverFactory.getCurrentTitle().contains(linkName) && count < Constants.EXPLICIT_WAIT_TIME) {
            Thread.sleep(1200);
            count++;
        }

        if (DriverFactory.getCurrentTitle().contains(linkName)) {
            pageInfo.pass("successfully Navigated to: " + linkName);
            Utils.switchToContentFrame();
        } else
            Assertion.markAsFailure("Couldn't find the Link :" + linkName);

    }

    public void waitTillDisappearingOfSpinner(){
        // Wait till the disappearing of spinner
        while(elementIsDisplayed(spinner)){
            Utils.putThreadSleep(500);
        }
    }

    public void changeWorkSpace(String workspaceName) throws Exception {
        try{

            closeToastMessages();

            Utils.putThreadSleep(5000);

            //Click on profile
            clickOnElement(profileIcon, "Profile");

            //Click on change workspace
            clickOnElement(changeWorkspace, "Change workspace");
            Utils.captureScreen(pageInfo);

            //Click on workspace to be opened
            WebElement selectedWorkspace = driver.findElement(By.xpath("//ul[@class='dropdown_menu']//li[contains(text(), '" + workspaceName + "')]"));
            clickOnElement(selectedWorkspace, workspaceName);
            waitTillDisappearingOfSpinner();
        }
        catch(Exception e){
            logInfo(e.getMessage());
        }
    }

    public TopMenu(ExtentTest t1) {
        super(t1);
    }

    public static TopMenu init(ExtentTest t1) throws Exception {
        return new TopMenu(t1);
    }
}
