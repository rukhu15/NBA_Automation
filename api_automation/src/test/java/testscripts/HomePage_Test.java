package testscripts;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;

import application.pageObjects.HomePage;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class HomePage_Test extends TestInit{
	
    @Test(priority = 1, groups = {"SANITY"}, enabled = true)
    public void HomePage_TC_01() {
        ExtentTest t2 = pNode.createNode("HomePage_TC_01", "Verify user navigated to home page successfully");
        try {
        	HomePage homePage=HomePage.init(t2);
        	homePage.validateNavigationToHomePage();
        	homePage.clickOnViewAllScenarios();
			homePage.validateNavigationToHomePage();
        } catch (Exception e) {
            markTestAsFailure(e, t2);
        } finally {
            Assertion.finalizeSoftAsserts();
        }
    }
    
    @Test(priority = 2, groups = { "sanity" }, enabled = true)
	public void ValidateBuildAnAudience_TC_02() 
	{
		ExtentTest t2 = pNode.createNode("ValidateBuildAnAudience_TC_02","Validate the user clicks on Build an Audience navigates to audience screen ");
		try {
			HomePage homePage = HomePage.init(t2);
			homePage.clickonBuildAudience();

		} catch (Exception e) {
			markTestAsFailure(e, t2);
		} finally {
			Assertion.finalizeSoftAsserts();
		}
	}
    
    @Test(priority = 3, groups = { "sanity" }, enabled = true)
	public void ValidateCreateaWorkflow_TC_03()
	{
		ExtentTest t2 = pNode.createNode("ValidateCreateaWorkflow_TC_03","Validate the user clicks on create a workflow card navigates to model page.");
		try {
			HomePage homePage = HomePage.init(t2);
			homePage.clickonCreateaworkflow();

		} catch (Exception e) {
			markTestAsFailure(e, t2);
		} finally {
			Assertion.finalizeSoftAsserts();
		}
	}
    
    
    @Test(priority = 4, groups = { "sanity" }, enabled = true)
    public void ValidateExeuteScenario_TC_05()
    {
    	ExtentTest t2 = pNode.createNode("ValidateExeuteScenario_05","Validate the user clicks on exeute a scenarios card navigates to list of active scenarios");
    	try {
    		HomePage homePage = HomePage.init(t2);
    		homePage.cliconExecuteaScenario();

    	} catch (Exception e) {
    		markTestAsFailure(e, t2);
    	} finally {
    		Assertion.finalizeSoftAsserts();
    	}
    }
    
    

@Test(priority = 5, groups = { "sanity" }, enabled = true)
public void ValidateMonitorSuggestions_TC_06()
{
	ExtentTest t2 = pNode.createNode("ValidateMonitorSuggestions_06","Validate the user clicks on monitor suggestions card navigates to monitor suggestions");
	try {
		HomePage homePage = HomePage.init(t2);
		homePage.cliconMonitorSuggestionslink();
		homePage.validateElementsOnModelScreen();

	} catch (Exception e) {
		markTestAsFailure(e, t2);
	} finally {
		Assertion.finalizeSoftAsserts();
	}
}
}
