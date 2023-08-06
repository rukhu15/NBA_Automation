package application.pageObjects;


import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import framework.utility.common.Assertion;
import framework.utility.common.DataFactory;
import framework.utility.common.DriverFactory;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.Constants;

public class LoginPage extends PageInit {
	
	private static ExtentTest pNode;

    @FindBy(id = "username")
    WebElement txtUserName;

    @FindBy(id = "password")
    WebElement txtPassWord;

    @FindBy(css = "[id='kc-login'][type='submit']")
    WebElement btnSignIn;
    
    @FindBy(linkText = "Log Out")
    WebElement linkLogOut;

    @FindBy(css = "[data-aura-class='forceSocialPhoto']")
    WebElement profileIcon;


    public LoginPage setUserName(String text) {
        setText(txtUserName, text, "UserName");
        return this;
    }

    public LoginPage setPassword(String text) {
        setText(txtPassWord, text, "Password");
        return this;
    }

    public LoginPage clickOnSubmit() throws Exception {
        clickOnElement(btnSignIn, "Sign In");
        Thread.sleep(Constants.MAX_WAIT_TIME);
        return this;
    }

    public LoginPage(ExtentTest t1) {
        super(t1);
    }
    
    

    /**
     * Login with specific user, if user is already logged in > return
     *
     * @param "loginId"
     * @throws IOException
     */
    public void login(String userName,String password) throws Exception {
        Markup m = MarkupHelper.createLabel("Login as: " + userName, ExtentColor.BLUE);
        pNode.info(m);

        closeToastMessages();

        //TestUser siqUser = DataFactory.getSiqUser(loginId);

        // check if user is already logged in with current View
        if (ConfigInput.lastUser != null && ConfigInput.lastUser.equalsIgnoreCase(userName)) {
            pNode.pass("Already Logged in as TestUser: " + userName);
            return;
        }

        if (ConfigInput.lastUser != null) {
            logOut();
        }

        try {
            WebDriver driver = DriverFactory.getDriver();
            System.out.println(ConfigInput.url);
            driver.get(ConfigInput.url);
            pNode.info("Open Application, URL: " + ConfigInput.url);

            LoginPage p1 = LoginPage.init(pNode);
            p1.setUserName(userName);
            p1.setPassword(password);
            p1.clickOnSubmit();

            int count = 0;
            while (!driver.getTitle().contains("Customer IQ") && count < Constants.EXPLICIT_WAIT_TIME) {
                Thread.sleep(700);
                count++;
            }

            // Double check that user is successfully logged in
            if (Assertion.verifyEqual(driver.getTitle().contains("Customer IQ"),
                    true,
                    "Verify that user should be able to login into Customer IQ application", pNode)) {
                ConfigInput.lastUser = userName;
            }

        } catch (Exception e) {
            Assertion.raiseExceptionAndStop(e, pNode);
        }
    }

    /**
     * Log out from application, close any additional Tabs
     *
     * @throws Exception
     */
    public void logOut() throws Exception {
        try {
            if (ConfigInput.lastUser == null) {
                pNode.info("No Last TestUser Logged In!");
                return;
            }

            // close CR handle if open
            String mainHandle = Utils.getMainWindowHandle();
            Utils.switchToMainWindow(mainHandle); // close any open window other than Main Window
            LoginPage.init(pNode).clickOnLogout();
            int count = 0;
            while (!DriverFactory.getDriver().getTitle().contains("Login | Salesforce") && count < 8) {
                Thread.sleep(1000);
                count++;
            }
        } catch (Exception e) {
            Assertion.raiseExceptionAndContinue(e, pNode);
        } finally {
            Utils.captureScreen(pNode);
            ConfigInput.lastUser = null;
            DriverFactory.closeDriver();
        }
    }

    public static LoginPage init(ExtentTest t1) throws Exception {
        pNode = t1;
        return new LoginPage(pNode);
    }
    
    public void clickOnLogout() throws Exception {
        if (elementIsDisplayed(profileIcon)) {
            clickOnElement(profileIcon, "Profile Icon");
            clickOnElementUsingJs(linkLogOut, "Logout");
        }
    }

    
}