package application.pageObjects;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import framework.utility.common.DatabaseUtil;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.aventstack.extentreports.ExtentTest;

import data.beans.ScenarioPageBean;
import framework.utility.common.Assertion;
import framework.utility.globalConst.Constants;

import org.testng.Assert;

public class ScenariosPage extends PageInit {

	private static ExtentTest pNode;

	@FindBy(xpath = "//button[@role='button']")
	WebElement btn_sidebarHamburgerButton;

	@FindBy(xpath = "//div[contains(text(),'Next Best Engagement')]/ancestor::div[@id='Next Best Engagement']")
	WebElement lnk_NBE;

	@FindBy(xpath = "//span[normalize-space()='Active']")
	WebElement btn_activeTab;

	@FindBy(xpath = "//span[contains(text(),'inactive')]")
	WebElement btn_inactiveTab;

	@FindBy(xpath = "//div[@class='spinner']")
	WebElement spinner;

	@FindBy(xpath = "//button[contains(text(),'Create Scenario')]")
	WebElement btn_createScenario;

	@FindBy(xpath = "//*[@id='bootstrap-input']")
	WebElement txt_scenarioName;

	@FindBy(xpath = "//*[@placeholder='Description']")
	WebElement txt_scenarioDescription;

	@FindBy(xpath = "//*[@id=\"businessUnitFormnext\"]")
	WebElement btn_scenarioCreateButton;

	@FindBy(xpath = "//*[@id=\"businessUnitFormback\"]")
	WebElement btn_scenarioCancelButton;

	@FindBy(xpath = "//*[contains(text(),'Reschedule')]")
	WebElement reSchedule;

	@FindBy(xpath = "//input[contains(@placeholder, 'Search Scenario')]")
	WebElement txt_searchScenario;

	@FindBy(xpath = "//button[@type='submit']")
	WebElement btn_searchScenarioButton;

	@FindBy(xpath = "//li[@id='Edit']")
	WebElement link_scenarioEditOption;

	@FindBy(xpath = "//button[@aria-label = 'clear']")
	WebElement clear;

	@FindBy(xpath = "//li[@id='Delete']")
	WebElement link_scenarioDeleteOption;

	@FindBy(xpath = "//li[@id='Select Scenario']")
	WebElement link_scenarioSelectOption;

	@FindBy(xpath = "//li[@id='Activate Scenario']")
	WebElement link_scenarioActivateOption;

	@FindBy(xpath = "//p[contains(@class,'MuiTablePagination')]")
	WebElement link_scenarioNavigationCount;

	@FindBy(id = "start_date")
	WebElement txt_startDate;

	@FindBy(xpath = "//*[contains(text(),'Execute Scenario')]")
	WebElement executeScenario;

	@FindBy(xpath = "//input[@id='end_date']/parent::div")
	WebElement txt_endDate;

	@FindBy(xpath = "//button[contains(text(),'Schedule')]")
	WebElement btn_scheduleScenario;

	@FindBy(xpath = "//button[contains(text(),'Cancel')]")
	WebElement btn_cancelScheduleScenario;

	@FindBy(xpath = "//*[@id='recurringSelect']")
	WebElement drpdwn_recurringType;

	@FindBy(xpath = "//li[contains(@data-value,'month')]")
	WebElement drpdwn_recurringMonth;

	@FindBy(xpath = "//li[contains(@data-value,'week')]")
	WebElement drpdwn_recurringWeek;

	@FindBy(xpath = "//*[contains(@class,'workspaveHeaderName')]//i/parent::h3")
	WebElement title_header_activeScenario;

	@FindBy(xpath = "//select[@id='demo-customized-select-native']")
	WebElement drpdwn_scenario_brand;

	@FindBy(xpath = "//*[contains(text(),'Deactivate Scenario')]")
	WebElement deactivateScenario;

	@FindBy(xpath ="//span[normalize-space()='OK']")
	WebElement btn_scenario_switch_ok;

	String str_scenarioName="//div[@class ='hyperlink-text' and text()='ScenarioNameStr']";

	public ScenariosPage(ExtentTest t3) {
		super(t3);
	}

	public static ScenariosPage init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new ScenariosPage(pNode);
	}

	public void moveToActiveInactiveScenarioTab(String moveToTab) throws Exception {
		if (moveToTab.equalsIgnoreCase("Active") || moveToTab.equalsIgnoreCase("Any") || moveToTab.equalsIgnoreCase(""))
			clickOnElement(btn_activeTab, "scenarios_active_tab");
		else if (moveToTab.equalsIgnoreCase("Inactive"))
			clickOnElement(btn_inactiveTab, "scenarios_inactive_tab");

		Thread.sleep(5000);
	}

	public int getNumberOfActiveScenarios() throws Exception {
		moveToActiveInactiveScenarioTab("Active");
		return getScenarioCount();
	}

	public int getNumberOfInActiveScenario() throws Exception {
		moveToActiveInactiveScenarioTab("InActive");
		return getScenarioCount();
	}

	public int getScenarioCount() throws Exception {
		String scenarioCount = getText(link_scenarioNavigationCount);
		scenarioCount = scenarioCount.substring(scenarioCount.indexOf("of ") + 3);
		return Integer.parseInt(scenarioCount);
	}

	public void createScenario() throws Exception {
		pNode.info("TestUser is creating new Scenario");
		clickOnElement(btn_createScenario, "scenarios_create_button");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void enterScenarioName(String scenarioName) throws Exception {
		setText(txt_scenarioName, scenarioName, "Scenario Name");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void enterScenarioDescription(String scenarioDescription) throws Exception {
		setText(txt_scenarioDescription, scenarioDescription, "Scenario Description");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void saveScenario() throws Exception {
		pNode.info("TestUser created / modified Scenario");
		clickOnElement(btn_scenarioCreateButton, "save scenario");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void cancelScenario() throws Exception {
		pNode.info("TestUser canceled changes on Scenario");
		clickOnElement(btn_scenarioCancelButton, "cancel scenario changes");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void actionOnAddScenarioCurtain(ScenarioPageBean sc) throws Exception {
		enterScenarioName(sc.scenarioName);
		enterScenarioDescription(sc.scenarioDescription);
		selectBrandInScenario(sc.Brand);
	}

	public void editScenarioCurtain(ScenarioPageBean sc) throws Exception {
		enterScenarioName(sc.newScenarioName);
		enterScenarioDescription(sc.newScenarioDesc);
		selectBrandInScenario(sc.Brand);
	}

	public void createScenarioWithSave(ScenarioPageBean sc) throws Exception {
		createScenario();
		actionOnAddScenarioCurtain(sc);
		saveScenario();
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void createScenarioWithCancel(ScenarioPageBean sc) throws Exception {
		createScenario();
		actionOnAddScenarioCurtain(sc);
		cancelScenario();
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void searchScenario(String searchScenarioText, String scenarioIsInStatus) throws Exception {
		pNode.info("TestUser is searching " + searchScenarioText + " Scenario");
		moveToActiveInactiveScenarioTab(scenarioIsInStatus);
		if (!isScenarioPresentOnTab(searchScenarioText)) {
			setText(txt_searchScenario, searchScenarioText, "Search_Scenario");
			clickOnElement(btn_searchScenarioButton, "SearchScenariobutton");
//			txt_searchScenario.sendKeys(Keys.chord(Keys.ENTER));
			Thread.sleep(Constants.MAX_WAIT_TIME);
			if (!isScenarioPresentOnTab(searchScenarioText)
					&& (scenarioIsInStatus.equalsIgnoreCase("Any") || scenarioIsInStatus.equalsIgnoreCase("")))
				searchScenario(searchScenarioText, scenarioIsInStatus = "Inactive");
			if (clear.isDisplayed()) {
				clickOnElement(clear, "Clear the search box");
			}
		}
		waitTillDisappearingOfSpinner();

	}

	public boolean isScenarioPresentOnTab(String scenarioName) {
		try {
			wait.until(ExpectedConditions
					.visibilityOf(driver.findElement(By.xpath("//*[text()='" + scenarioName + "']"))));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void validateScenarioIsPresent(String scenarioName, boolean expectedStatus) throws Exception {
		try {
			Assertion.verifyEqual(isScenarioPresentOnTab(scenarioName), expectedStatus,
					"Verify scenario " + scenarioName + " is present", pNode, false);
		} catch (IOException e) {
			Assertion.raiseExceptionAndStop(e, pNode);
			;
		}
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void scenarioOptionsPreRequisite(String scenarioName, String scenarioIsInStatus) throws Exception {
		searchScenario(scenarioName, scenarioIsInStatus);
		validateScenarioIsPresent(scenarioName, true);
		clickOnElement(getWebElementToPerformActionOnScenarioOptions(scenarioName), "scenario_3dots_options");
	}

	public void editScenarioWithSave(ScenarioPageBean sc) throws Exception {
		scenarioOptionsPreRequisite(sc.scenarioName, "Inactive");
		pNode.info("TestUser is editing " + sc.scenarioName + " Scenario");
		clickOnElement(link_scenarioEditOption, "scenario_edit_option");
		editScenarioWithSave(sc);
		saveScenario();
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void editScenarioWithCancel(ScenarioPageBean sc) throws Exception {
		scenarioOptionsPreRequisite(sc.scenarioName, "Inactive");
		pNode.info("TestUser is editing " + sc.scenarioName + " Scenario");
		clickOnElement(link_scenarioEditOption, "scenario_edit_option");
		editScenarioWithSave(sc);
		cancelScenario();
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void validateScenarioSelected(String scenarioName) throws Exception {
		try {
			Assertion.verifyEqual(getText(title_header_activeScenario).equalsIgnoreCase(scenarioName), true,
					"Verify scenario " + scenarioName + " is selected", pNode, false);
		} catch (IOException e) {
			Assertion.raiseExceptionAndStop(e, pNode);
			;
		}
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void selectScenario(String scenarioName, String scenarioIsInStatus) throws Exception {
		scenarioOptionsPreRequisite(scenarioName, scenarioIsInStatus);
		pNode.info("TestUser is searching " + scenarioName + " Scenario");
		clickOnElement(link_scenarioSelectOption, "select_scenario_option");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void deleteScenario(String scenarioName) throws Exception {
		scenarioOptionsPreRequisite(scenarioName, "Inactive");
		pNode.info("TestUser is searching " + scenarioName + " Scenario");
		clickOnElement(link_scenarioDeleteOption, "scenario_delete_option");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void actionOnScheduleScenarioScreen(String scenarioName, String startDate, String endDate,
			String recurringType, Object recurringOn[]) throws Exception {
		setText(txt_startDate, startDate, "start_date");
		Thread.sleep(Constants.MAX_WAIT_TIME);
		// setText(txt_endDate, endDate, "end_date");
		clickOnElement(drpdwn_recurringType, "opening_recurring_dropdown");
		if (recurringType.equalsIgnoreCase("Month"))
			clickOnElement(drpdwn_recurringMonth, "recurring_month_dropdown");
		else if (recurringType.equalsIgnoreCase("week"))
			clickOnElement(drpdwn_recurringWeek, "recurring_week_dropdown");
		Thread.sleep(Constants.MAX_WAIT_TIME);
		for (Object value : recurringOn) {
			try {
				wait.until(ExpectedConditions
						.visibilityOf(driver.findElement(By.xpath("//div[@class='dayss'][" + value + "]/div"))));
				clickOnElement(driver.findElement(By.xpath("//div[@class='dayss'][" + value + "]/div")),
						"select_recurring_" + value + "_option");
			} catch (Exception e) {

			}
		}
	}

	public void scheduleScenarioWithSave(String scenarioName, String startDate, String endDate, String recurringType,
			Object recurringOn[]) throws Exception {
		scenarioOptionsPreRequisite(scenarioName, "Inactive");
		pNode.info("TestUser is searching " + scenarioName + " Scenario");
		clickOnElement(link_scenarioActivateOption, "scenario_activate_option");
		actionOnScheduleScenarioScreen(scenarioName, startDate, endDate, recurringType, recurringOn);
		clickOnElement(btn_scheduleScenario, "scheduling_scenario");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void scheduleScenarioWithCancel(String scenarioName, String startDate, String endDate, String recurringType,
			Object recurringOn[]) throws Exception {
		scenarioOptionsPreRequisite(scenarioName, "Inactive");
		pNode.info("TestUser is searching " + scenarioName + " Scenario");
		clickOnElement(link_scenarioActivateOption, "scenario_activate_option");
		actionOnScheduleScenarioScreen(scenarioName, startDate, endDate, recurringType, recurringOn);
		clickOnElement(btn_cancelScheduleScenario, "canceling_scheduling_scenario");
		Thread.sleep(Constants.MAX_WAIT_TIME);
	}

	public void selectBrandInScenario(String brandName) {
		if (brandName != null && !brandName.isEmpty()) {
			selectVisibleText(drpdwn_scenario_brand, brandName, "Scenario Brand Select box");
		}
	}

	public void deleteExistingAutomationScenario() {
		System.out.println("Going to delete all scenarios for given use nba-qar");
		String query = "UPDATE "+ ConfigInput.dbSchemaName +".nba_scenario a\n" + "SET is_deleted=true \n" + "FROM "+ConfigInput.dbAppSchemaName+".user b\n"
				+ "WHERE a.created_by =b.user_id\n" + "and b.user_name ='nba_automation';";
		DatabaseUtil.runUpdateQuery(query);
	}

	public void verifyExistingAutomationScenario() {
		String query = "select is_deleted from nbadesigner_qa.nba_scenario a\n" + "inner join nba_qa.user b\n"
				+ "on a.created_by =b.user_id \n" + "and b.user_name ='nba_automation';";
		try {

			ResultSet rs = DatabaseUtil.runSelectQuery(query);
			System.out.println("ResultSet is :" + rs);
			while (rs.next()) {
				Assert.assertTrue(rs.getBoolean("is_deleted"),
						"Some of preexisting automation scenarios could not marked deleted, please check "
								+ "either user in test data sheet or config!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void freeDBResources() {
		DatabaseUtil.closeDBResources();
	}

	public void doPageRefresh() {
		driver.navigate().refresh();
		wait.until(
				driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}

	public void reScheduleScenario(String scenarioName, String startDate, String endDate, String recurringType,
			Object recurringOn[]) throws Exception {
		scenarioOptionsPreRequisite(scenarioName, "Active");
		pNode.info("TestUser is searching " + scenarioName + " Scenario");
		clickOnElement(reSchedule, "Reschedule the scenario");
		actionOnScheduleScenarioScreen(scenarioName, startDate, endDate, recurringType, recurringOn);
		clickOnElement(btn_scheduleScenario, "scheduling_scenario");
	}

	public void executeScenario(String scenarioName) throws Exception {
		validateScenarioIsPresent(scenarioName, true);
		clickOnElement(getWebElementToPerformActionOnScenarioOptions(scenarioName), "scenario_3dots_options");
		if (executeScenario.isDisplayed()) {
			clickOnElement(executeScenario, "Deactivate the scenario");
			pNode.info(scenarioName + " is executed");
		}
	}

	public void deactivateScenario(String scenarioName) throws Exception {
		validateScenarioIsPresent(scenarioName, true);
		clickOnElement(getWebElementToPerformActionOnScenarioOptions(scenarioName), "scenario_3dots_options");
		clickOnElement(deactivateScenario, "Deactivate the scenario");
		searchScenario(scenarioName, "Inactive");
	}

	public void navigateToScenarioPage() {
		try {
			closeToastMessages();
			clickOnElement(btn_sidebarHamburgerButton, "sidebar hamburger menu button");
			clickOnElement(lnk_NBE, "NBE Link on sidebar");
			if (btn_createScenario.isDisplayed())
				pNode.pass("TestUser navigated back to scenario list page");
			else
				pNode.fail("TestUser could not be navigated back to scenario list page");
			waitTillDisappearingOfSpinner();
		}
		catch (Exception e){
			logInfo(e.getMessage());
		}
	}

	public void clickOnScenario(String scenarioName) throws InterruptedException {
		WebElement ele= driver.
				findElement(By.xpath(str_scenarioName.
								replace("ScenarioNameStr",scenarioName)));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", ele);
		List<WebElement> dialogBox = driver.findElements(By.xpath("//div[@role='dialog']"));
		if (dialogBox.size()>0) {
			if (longWait.until(ExpectedConditions.visibilityOf
					(driver.findElement(By.xpath("//div[@role='dialog']")))) != null) {
				clickOnElement(btn_scenario_switch_ok, "Accepting alert");
			} else {
				pageInfo.info("Dialog box is not Present");
			}
		}else {
			pageInfo.info("No Dialog box appear on clicking scenario");
		}
	}

	public void selectAndClickScenario(String scenarioName, String scenarioIsInStatus) throws Exception {
			moveToActiveInactiveScenarioTab(scenarioIsInStatus);
			setText(txt_searchScenario, scenarioName, "Search_Scenario");
			clickOnElement(btn_searchScenarioButton, "SearchScenariobutton");
		Utils.putThreadSleep(3333);
			clickOnScenario(scenarioName);
	}

	public void waitTillDisappearingOfSpinner(){
		// Wait till the disappearing of spinner
		while(elementIsDisplayed(spinner)){
			Utils.putThreadSleep(100);
		}
	}
}