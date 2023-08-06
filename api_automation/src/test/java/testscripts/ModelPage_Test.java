package testscripts;

import application.pageObjects.HomePage;
import application.pageObjects.ModelActionsPage;
import application.pageObjects.ModelPage;
import application.pageObjects.ScenariosPage;
import data.beans.ModelPageBean;
import data.beans.TagPageBean;
import dataProvidersPackage.UIDP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class ModelPage_Test extends TestInit{

    @Test(priority = 1,groups = {"SANITY"},enabled = true,dataProvider = "createWorkFlow",dataProviderClass = UIDP.class)
    public void ModelPage_TC_01(ModelPageBean mg){
        ExtentTest t1 = pNode.createNode("create Model Workflow","To create the Workflow");
        try {
            ModelPage modelPage = ModelPage.init(t1);
            ScenariosPage scenarioPage = ScenariosPage.init(t1);
            HomePage homePage = HomePage.init(t1);

            scenarioPage.navigateToScenarioPage();
            homePage.cancelSideNavigation();
            scenarioPage.selectAndClickScenario(mg.scenarioName,"Inactive");
            modelPage.toNavigateModel(mg);
            modelPage.checkWorkFlow(mg);
            modelPage.createWorkFlow(mg);
            modelPage.enterIntoWorkFlow(mg);
        }catch (Exception e){
            markTestAsFailure(e, t1);
        }
    }

    @Test(priority = 2,groups = {"SANITY"}, enabled = true, dataProvider = "createWorkFlow", dataProviderClass = UIDP.class)
    public void TriggerPage_TC_01(ModelPageBean mg){
        ExtentTest t2 = pNode.createNode("Create Model Trigger","to create trigger part in the model module");
        try {
            String getKPIName = "";
            ModelPage triggerPage = ModelPage.init(t2);
            triggerPage.verifyTriggerPage(mg);
            triggerPage.clickOnBtnAddTrigger();
            getKPIName = triggerPage.searchKpi(mg);
            triggerPage.selectKpiAndAdd(getKPIName);
            triggerPage.triggerInputsAndSave(getKPIName,mg);
        }catch (Exception e){
            markTestAsFailure(e, t2);
        }
    }

    @Test(priority = 3,groups = {"SANITY"},enabled = true,dataProvider = "createWorkFlow", dataProviderClass = UIDP.class)
    public void RulePage_TC_01(ModelPageBean mg){
        ExtentTest t3 = pNode.createNode("Create Model Rule","to create Rule part in the model module");
        try {
            String getKPIName ="";
            ModelPage rulePage = ModelPage.init(t3);
            rulePage.verifyRules(mg);
            rulePage.clickOnBtnAddRule();
            getKPIName = rulePage.searchKpiForRule(mg);
            rulePage.ruleInputsAndSave(getKPIName,mg);
        }catch (Exception e){
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 4,groups = {"SANITY"},enabled = true,dataProvider = "createWorkFlow",dataProviderClass = UIDP.class)
    public void ActionPage_TC_01(ModelPageBean mg){
        ExtentTest t4 = pNode.createNode("Create Model Actions","to create Actions part in the model module");
        try {
            JSONArray actionDetails;
            ModelActionsPage actionPage = ModelActionsPage.init(t4);
            actionDetails = actionPage.getActionsInput(mg);
            actionPage.verifyActionsPage(mg);
            actionPage.clickOnBtnAddActions();
            actionPage.clickCheckBoxChannels(actionDetails);
            actionPage.contentConfigure(actionDetails,mg);
            actionPage.channelPriority(actionDetails);
            actionPage.suggestionTitleAndReason(actionDetails);
        }catch (Exception e){
            markTestAsFailure(e, t4);
        }finally {
            Assertion.finalizeSoftAsserts();
        }
    }
    
}