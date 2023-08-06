package apiTestScript;

import application.apiPageObjects.ScenarioAPI;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.ScenarioAPIBean;
import dataProvidersPackage.APIDP;
import framework.utility.common.DatabaseUtil;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import scripts.TestInit;

/**
 * @author Prateek Sethi
 */
public class ScenarioAPI_Test extends TestInit {

private static final Logger logToConsole = LoggerFactory.getLogger(ScenarioAPI_Test.class);

	@Test(priority = -2, groups = {
			"api","clean" }, enabled = true, alwaysRun = true, dataProvider = "createScenarioAPI", dataProviderClass = APIDP.class)
	public void deleteExistingScenario(ScenarioAPIBean sc) {
		ExtentTest t3 = pNode.createNode("deleteExistingScenario", sc.scenarioName);
		// Deleting preExisting scenario if present thru api tag
		// Deleting scenarios after executing for environment cleanup thru "clean" tag
		try {
//				System.setProperty("***********workspace Name**********", System.getProperty("WORKSPACE_NAME"));
				DatabaseUtil.deleteExistingAutomationScenario(sc.scenarioName);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

    @Test(priority = -1, groups = {"api"}, enabled = true,dataProvider="createScenarioAPI",
            dataProviderClass = APIDP.class)
    public void ScenarioAPI_TC_Create(ScenarioAPIBean sc) {
        ExtentTest t3 = pNode.createNode("ScenarioAPI_TC_Create", sc.testDescription);
        try {
            ScenarioAPI scenarioAPI=ScenarioAPI.init(t3);
            pNode.info("Going to send the API POST req for scenario:  "+sc.scenarioOwner);
            Response resp=scenarioAPI.createScenario(sc);
            scenarioAPI.validateScenarioCreation(resp,sc);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 2, groups = {"api"}, enabled = true,dataProvider="updateScenarioAPI",
            dataProviderClass = APIDP.class)
    public void ScenarioAPI_TC_Update(ScenarioAPIBean sc) {
        ExtentTest t3 = pNode.createNode("ScenarioAPI_TC_Update", sc.testDescription);
        try {
            ScenarioAPI scenarioAPI=ScenarioAPI.init(t3);
            Response resp=scenarioAPI.updateScenario(sc);
            scenarioAPI.validateScenarioInResponse(resp,sc);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 3, groups = {"api"}, enabled = true,dataProvider="getScenarioAPI",
            dataProviderClass = APIDP.class)
    public void ScenarioAPI_TC_Get(ScenarioAPIBean sc) {
        ExtentTest t3 = pNode.createNode("ScenarioAPI_TC_Get", sc.testDescription);
        try {
            ScenarioAPI scenarioAPI=ScenarioAPI.init(t3);
            Response resp=scenarioAPI.getScenario(sc);
            scenarioAPI.validateScenarioInResponse(resp,sc);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 4, groups = {"api"}, enabled = true,dataProvider="deleteScenarioAPI",
            dataProviderClass = APIDP.class)
    public void ScenarioAPI_TC_Delete(ScenarioAPIBean sc) {
        ExtentTest t3 = pNode.createNode("ScenarioAPI_TC_Delete", sc.testDescription);
        try {
            ScenarioAPI scenarioAPI=ScenarioAPI.init(t3);
            Response resp=scenarioAPI.deleteScenario(sc);
            scenarioAPI.validateScenarioInResponse(resp,sc);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }
}