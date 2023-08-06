package application.pageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import framework.utility.common.Assertion;

public class HomePage extends PageInit {
	private static ExtentTest pNode;
	private static int scenariosCount = 0;

	@FindBy(xpath = "//div[@id='parentElement']/*[text()=' Recent Execution']")
	WebElement lbl_monitorScreen;

	@FindBy(xpath = "//div[normalize-space()='Scenarios']/following-sibling::div[1]")
	WebElement scenarioCount;

	@FindBy(id = "viewCompanyList")
	WebElement lnk_viewAllScenario;

	@FindBy(xpath = "//button[normalize-space()='Create Scenario']")
	WebElement btn_createScenario;

	@FindBy(xpath = "//p[@id='title1']")
	WebElement btn_view_all_scenarios;

	// Home link

	@FindBy(css = "#Home")
	WebElement Homelink;

	//sidebar hamburger button
	@FindBy(xpath = "//button[@role='button']")
	WebElement btn_sidebarHamburgerButton;

	// Build an Audience//
	@FindBy(xpath = "//div[contains(text(),'Build an Audience')]")
	WebElement lnk_Audiencelink;

	// Create a workflow//
	@FindBy(xpath = "//div[@id='Create a Workflow']")
	WebElement lnk_Workflowlink;

	// Execute a Scenario//

	@FindBy(xpath = "//div[contains(text(),'Execute a Scenario')]")
	WebElement lnk_Executeascenario;

	// Monitor Suggestions//
	@FindBy(xpath = "//div[contains(text(),'Monitor Suggestions')]")
	WebElement lnk_MonitorSuggestionslink;

	// Create workflow button//

	@FindBy(xpath = "//div[contains(text(),'Create a Workflow')]")
	WebElement btn_createworkflow;

	//Model Icon
	@FindBy(xpath="//span[text()='Model']")
	WebElement icon_model_workflow;

	// Audience Text //

	@FindBy(xpath = "//span[@class='MuiTab-wrapper']//span[contains(text(),'Audience')]")
	WebElement txt_audience;

	// Create Scenario Button//

	@FindBy(xpath = "//button[contains(text(),'Create Scenario')]")
	WebElement btn_createscenario;

	// Monitor screen tabs//

	@FindBy(xpath = "//span[contains(text(),'Summary')]")
	WebElement tab_summary_monitor;

	@FindBy(xpath = "//span[contains(text(),'Details')]")
	WebElement tab_details_monitor;

	// Change Scenario Button//

	@FindBy(xpath = "//button[contains(text(),'Change Scenario')]")
	WebElement btn_ChangeScenario;

	@FindBy(xpath = "//span[@class='sidenav---icon-bar---u1f02'][1]")
	WebElement btn_cancel_side_navigation;

	public HomePage(ExtentTest t2) {
		super(t2);
	}

	public int getNumberOfScenariosCreated() {
		pNode.info("Fetching the total number of scenarios created(active+inactive) present on home page");
		scenariosCount = Integer.parseInt(getText(scenarioCount));
		System.out.println("Total scenarios created: " + scenariosCount);
		return scenariosCount;
	}

	public static HomePage init(ExtentTest t2) throws Exception {
		pNode = t2;
		return new HomePage(pNode);
	}

	public void validateNavigationToScenarioPage() {
		pNode.info("TestUser is navigating to scenario page");
		clickOnElement(lnk_viewAllScenario, "view_all_scenarios_link");
		if (elementIsClickable(btn_createScenario))
			pNode.pass("TestUser navigated to Scenario page successfully from Home page: ");
	}

	public void validateTotalScenariosOnHomepage() throws Exception {

		ScenariosPage scenarioPage = new ScenariosPage(pNode);
		int activeScenariosCount = scenarioPage.getNumberOfActiveScenarios();
		System.out.println("Active Scenario: " + activeScenariosCount);
		int inactiveScenariosCount = scenarioPage.getNumberOfInActiveScenario();
		System.out.println("Inactive Scenario: " + inactiveScenariosCount);
		try {
			Assertion.verifyEqual(scenariosCount, (activeScenariosCount + inactiveScenariosCount),
					"Verify total created scenarios(active+ inactive) should be equal to number present on home page",
					pNode, false);
		} catch (IOException e) {

			Assertion.raiseExceptionAndStop(e, pNode);
			;
		}
	}

	public void clickOnViewAllScenarios() {
		clickOnElement(btn_view_all_scenarios, "View All Scenarios Button");
		clickOnElement(btn_sidebarHamburgerButton,"Side bar Hamburger menu button");
		clickOnElement(Homelink, "Home Link");
	}

	public void validateNavigationToHomePage() {
		if (elementIsClickable(btn_view_all_scenarios))
			pNode.pass("TestUser navigated to Home page successfully from Login page: ");

	}

	// The below method will click on Build an Audience card and than click on home
	// button.

	public void clickonBuildAudience() throws InterruptedException

	{

		clickOnElement(lnk_Audiencelink, "Build an Audience card present on home page");

		String txtaudience = txt_audience.getText();

		Assert.assertEquals(txtaudience, "AUDIENCE");

		pNode.pass("TestUser navigates to Build an Audience Screen");

		clickOnElement(Homelink, "Home Link");

	}

	// The below method will click on Create a Workflow card and than click on home.

	public void clickonCreateaworkflow() throws InterruptedException

	{

		clickOnElement(driver.findElement(RelativeLocator.with(By.xpath("//div[@id='Create a Workflow']")).toRightOf(By.xpath("//div[contains(text(),'Build an Audience')]"))), "Create a Workflow card present on home page");

		boolean icon_model_workflow_dispalyed = icon_model_workflow.isDisplayed();

		Assert.assertEquals(icon_model_workflow_dispalyed, true);

		pNode.pass("TestUser navigates to Model Screen page");

		clickOnElement(Homelink, "Home Link");

	}

	// The below method will click on Execute a Scenario card and than click on home
	// button.//

	public void cliconExecuteaScenario() throws InterruptedException

	{

		clickOnElement(lnk_Executeascenario, "Execute a Scenario card present on home page");

		boolean btncreatescenario = btn_createscenario.isDisplayed();

		Assert.assertEquals(btncreatescenario, true);

		pNode.pass("TestUser navigates to Active Scenarios lists");

		clickOnElement(Homelink, "Home Link");

	}

	// The below method will click on Monitor Suggestions card and than click on
	// home button.

	public void cliconMonitorSuggestionslink() throws InterruptedException

	{
		clickOnElement(lnk_MonitorSuggestionslink, " Monitor Suggestions card present on home page");
		boolean tabSummary = lbl_monitorScreen.isDisplayed();
		Assert.assertEquals(tabSummary, true);
		if(tabSummary){
			pNode.pass("TestUser navigates to Monitor Screen ");
		}else{
			pNode.fail("TestUser could not navigate to Monitor screen");
		}
	}

	public void validateElementsOnModelScreen() {

		boolean tabSummary = tab_summary_monitor.isDisplayed();
		if(tabSummary){
			pNode.pass("Summary Tab is present on Monitor screen");
		}else{
			pNode.fail("Summary Tab is not present on Monitor screen");
		}

		boolean tabDetailPresent=tab_details_monitor.isDisplayed();
		if(tabDetailPresent){
			pNode.pass("Details Tab is present on Monitor screen");
		}else{
			pNode.fail("Details Tab is not present on Monitor screen");
		}

		clickOnElement(Homelink, "Home Link");
	}

	public void navigateToHomePage() throws Exception {
		clickOnElement(btn_sidebarHamburgerButton,"Clicking on humburger menu button");
		clickOnElement(Homelink, "Clicking on Home Link");
		clickOnElementUsingJs(btn_cancel_side_navigation,"closing side nav bar");
		validateNavigationToHomePage();
	}

	public void cancelSideNavigation() throws Exception {
		clickOnElementUsingJs(btn_cancel_side_navigation,"closing side nav bar");
	}
}
