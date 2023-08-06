package testscripts;

import application.businessrules.TopMenu;
import com.aventstack.extentreports.ExtentTest;
import application.pageObjects.LoginPage;
import data.beans.LoginUIBean;
import dataProvidersPackage.LoginDP;
import framework.utility.common.Assertion;
import framework.utility.globalConst.ConfigInput;
import scripts.TestInit;
import org.testng.annotations.Test;

public class Login_Test extends TestInit {

	@Test(priority = 1, groups = {
			"SANITY" }, enabled = true, dataProvider = "login", dataProviderClass = LoginDP.class)
	public void LOGIN_TC_01(LoginUIBean lb) {
		ExtentTest t1 = pNode.createNode("LOGIN_TC_01", "Login to nba designer application");
		try {
			LoginPage.init(t1).login(lb.username, lb.password);
			//Change workspace
			TopMenu.init(t1).changeWorkSpace(ConfigInput.workspaceName);
		} catch (Exception e) {
			markTestAsFailure(e, t1);
		} finally {
			Assertion.finalizeSoftAsserts();
		}
	}
}