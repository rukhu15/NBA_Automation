package testscripts;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import application.pageObjects.ScenariosPage;
import data.beans.ScenarioPageBean;
import dataProvidersPackage.UIDP;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class ScenarioPage_Test extends TestInit {

	@BeforeClass(alwaysRun = true)
	public void deletePreExistingTestScenarios() {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_DeleteExistingAutomationScenarios",
				"ScenarioPage_TC_DeleteExistingAutomationScenarios");
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.deleteExistingAutomationScenario();
			scenarioPage.verifyExistingAutomationScenario();
//			scenarioPage.freeDBResources();
			scenarioPage.doPageRefresh();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(priority = 1, groups = {
			"SANITY" }, enabled = true, dataProvider = "createScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_CreateCancel(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_CreateCancel", sc.testDescription);

		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.navigateToScenarioPage();
			scenarioPage.createScenarioWithCancel(sc);
			scenarioPage.searchScenario(sc.scenarioName, "Inactive");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, false);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 2, groups = {
			"SANITY" }, enabled = true, dataProvider = "createScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_Create(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_Create", sc.testDescription);

		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.createScenarioWithSave(sc);
			scenarioPage.searchScenario(sc.scenarioName, "Inactive");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 3, groups = {
			"SANITY" }, enabled = true, dataProvider = "createScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_Search(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_Search", "Verify scenario search");
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.searchScenario(sc.scenarioName, "Inactive");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 4, groups = {
			"SANITY" }, enabled = true, dataProvider = "editScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_EditWithCancel(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_EditWithCancel", sc.testDescription);
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.editScenarioWithCancel(sc);
			scenarioPage.searchScenario(sc.newScenarioName, "Inactive");
			scenarioPage.validateScenarioIsPresent(sc.newScenarioName, false);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 5, groups = {
			"SANITY" }, enabled = true, dataProvider = "editScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_EditWithSave(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_EditWithSave", sc.testDescription);
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.editScenarioWithSave(sc);
			scenarioPage.searchScenario(sc.newScenarioName, "Inactive");
			scenarioPage.validateScenarioIsPresent(sc.newScenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 6, groups = {
			"SANITY" }, enabled = true, dataProvider = "selectScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_SelectScenario(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_SelectScenario", sc.testDescription);
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.selectScenario(sc.scenarioName, "Inactive");
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 7, groups = {
			"SANITY" }, enabled = false, dataProvider = "scheduleScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_ScheduleWithCancel(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_ScheduleWtihCancel", sc.testDescription);
		Object[] recurringOn = { 2, 4, 7 };
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.scheduleScenarioWithCancel(sc.scenarioName, sc.startDate, sc.endDate, sc.recurringType,
					recurringOn);
			scenarioPage.searchScenario(sc.scenarioName, "Inactive");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
			scenarioPage.searchScenario(sc.scenarioName, "active");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, false);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	/**
	 * Schedule the inactive scenario and scenario move to active list
	 */

	@Test(priority = 8, groups = {
			"SANITY" }, enabled = false, dataProvider = "scheduleScenario", dataProviderClass = UIDP.class)
	public void scenarioPage_TC_ScheduleScenario(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("scenarioPage_TC_ScheduleScenario", "Verify scenario activation");

		Object[] recurringOn = { 2, 4, 7 };
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.scheduleScenarioWithSave(sc.scenarioName, sc.startDate, sc.endDate, sc.recurringType,
					recurringOn);
			scenarioPage.searchScenario(sc.scenarioName, "active");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	/**
	 * This test is used to reschedule the scenario
	 */

	@Test(priority = 9, groups = {
			"SANITY" }, enabled = false, dataProvider = "rescheduleScenario", dataProviderClass = UIDP.class)
	public void scenarioPage_TC_ReScheduleScenario(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("scenarioPage_TC_ReScheduleScenario", sc.testDescription);
//// String scenarioName, String startDate, String endDate, String recurringType,
//// Object recurringOn[]
//String scenarioName = "Scenario_Automation1234", startDate = "01-12-2021", endDate = "30-12-2021",
//		recurringType = "Month";
		Object[] recurringOn = { 10, 20, 30 };
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.reScheduleScenario(sc.scenarioName, sc.startDate, sc.endDate, sc.recurringType, recurringOn);
			scenarioPage.searchScenario(sc.scenarioName, "active");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	/**
	 * Select the scenario from active scenario list
	 */

	@Test(priority = 10, groups = {
			"SANITY" }, enabled = false, dataProvider = "selectScenario", dataProviderClass = UIDP.class)
	public void activeScenarioPageSelectScenario(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("activeScenarioPageSelectScenario", sc.testDescription);
//// String scenarioName, String scenarioState
//String scenarioName, scenarioState;
//scenarioName = "Scenario_Automation1234";
//scenarioState = "active";
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.selectScenario(sc.scenarioName, "active");
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	/**
	 * Execute the scenario
	 */
	@Test(priority = 11, groups = {
			"SANITY" }, enabled = false, dataProvider = "executeScenario", dataProviderClass = UIDP.class)
	public void executeActiveScenario(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("executeScenario", sc.testDescription);
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.executeScenario(sc.scenarioName);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	/**
	 * Deactivate the scenario
	 */

	@Test(priority = 12, groups = {
			"SANITY" }, enabled = false, dataProvider = "scheduleScenario", dataProviderClass = UIDP.class)
	public void deactivateActiveScenario(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("deactivateScenario", "Deactivate the scenario");
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.deactivateScenario(sc.scenarioName);
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, true);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}

	@Test(priority = 13, groups = {
			"SANITY" }, enabled = true, dataProvider = "scheduleScenario", dataProviderClass = UIDP.class)
	public void ScenarioPage_TC_Delete(ScenarioPageBean sc) {
		ExtentTest t3 = pNode.createNode("ScenarioPage_TC_Delete", sc.testDescription);
		try {
			ScenariosPage scenarioPage = ScenariosPage.init(t3);
			scenarioPage.deleteScenario(sc.scenarioName);
			scenarioPage.validateScenarioIsPresent(sc.scenarioName, false);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
		Assertion.finalizeSoftAsserts();
	}
}