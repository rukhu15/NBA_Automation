package apiTestScript;

import application.apiPageObjects.ExecuteScenarioAPI;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.ExecuteScenarioBean;
import dataProvidersPackage.APIDP;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;

/**
 * @author Prateek Sethi
 */
public class ExecuteScenario_Test extends TestInit {
    @Test(priority = 1, groups = {
            "api" }, enabled = true, dataProvider = "activateScenario", dataProviderClass = APIDP.class)
    public void test_CreateScenarioSchedule(ExecuteScenarioBean es) {
        ExtentTest t3 = pNode.createNode("test_CreateScenarioSchedule", es.testDescription);
        try {
            ExecuteScenarioAPI executeScenarioAPI=ExecuteScenarioAPI.init(t3);
            pNode.info("Going to send the API POST req for scenario " +
                    "schedule creation for scenario :  "+es.scenarioName);
            Response resp=executeScenarioAPI.createScenarioSchedule(es);
            //executeScenarioAPI.validateScenarioSchedule(resp,es);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 2, groups = {
            "api" }, enabled = true, dataProvider = "executeScenario", dataProviderClass = APIDP.class)
    public void test_ExecuteScenario(ExecuteScenarioBean es) {
        ExtentTest t3 = pNode.createNode("test_validateTestOnEngine", es.testDescription);
        try {
            ExecuteScenarioAPI executeScenarioAPI=ExecuteScenarioAPI.init(t3);
            pNode.info("Going to send the API POST req for scenario " +
                    "execution for scenario :  "+es.scenarioName);
            Response resp=executeScenarioAPI.executeScenario(es);
            executeScenarioAPI.validateDagStatus(es,resp);
            executeScenarioAPI.uploadExpectedNoteBook(es);
            executeScenarioAPI.executeExpectedNoteBook(es);
            executeScenarioAPI.validateTestCases();
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }
}