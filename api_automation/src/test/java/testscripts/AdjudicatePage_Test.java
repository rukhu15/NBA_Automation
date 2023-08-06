package testscripts;

import application.pageObjects.AdjudicatePage;
import application.pageObjects.HomePage;
import application.pageObjects.ScenariosPage;
import com.aventstack.extentreports.ExtentTest;
import data.beans.AdjudicatePageBean;
import dataProvidersPackage.UIDP;
import framework.utility.common.Assertion;
import org.json.JSONArray;
import org.testng.annotations.Test;
import scripts.TestInit;

public class AdjudicatePage_Test extends TestInit {

    @Test(priority = 1,groups = {"SANITY"},enabled = true,dataProvider = "createAdjudicate",dataProviderClass = UIDP.class)
    public void Adjudicate_TC_01(AdjudicatePageBean AC){
        ExtentTest t1 = pNode.createNode("create Adjudicate Flow","To create Adjudicate channel flow");
        try {
            JSONArray adjudicateJson;
            ScenariosPage scenarioPage = ScenariosPage.init(t1);
            HomePage homePage = HomePage.init(t1);

            scenarioPage.navigateToScenarioPage();
            homePage.cancelSideNavigation();
            scenarioPage.selectAndClickScenario(AC.scenarioName,"Inactive");
            AdjudicatePage adjudicate = AdjudicatePage.init(t1);
            adjudicate.toNavigateAdjudicate();
            adjudicate.verifyAdjudicateChannelPage();
            adjudicateJson = adjudicate.getAdjudicateInput(AC);
            adjudicate.clickCheckBoxChannels(adjudicateJson);
            adjudicate.channelsInput(adjudicateJson,AC);
        }catch (Exception e){
            markTestAsFailure(e,t1);
        }
    }

    @Test(priority = 2,groups = {"SANITY"},enabled = true,dataProvider = "createAdjudicate",dataProviderClass = UIDP.class)
    public void Adjudicate_TC_02(AdjudicatePageBean AC){
        ExtentTest t2 = pNode.createNode("create Adjudicate Flow","To create Adjudicate CUSTOMER flow");
        try {
            JSONArray adjudicateJson;
            AdjudicatePage adjudicate = AdjudicatePage.init(t2);
            adjudicate.customerTab();
            adjudicate.verifyAdjudicateCustomer();
            adjudicateJson = adjudicate.getAdjudicateInput(AC);
            adjudicate.suggestionAndInterval(adjudicateJson,AC);
            adjudicate.selectSave();
        }catch (Exception e){
            markTestAsFailure(e,t2);
        }
    }

    @Test(priority = 3,groups = {"SANITY"},enabled = true,dataProvider = "createAdjudicate",dataProviderClass = UIDP.class)
    public void Adjudicate_TC_03(AdjudicatePageBean AC){
        ExtentTest t2 = pNode.createNode("create Adjudicate Flow","To create Adjudicate CUSTOMER-CHANNEL flow");
        try {
            JSONArray adjudicateJson;
            AdjudicatePage adjudicate = AdjudicatePage.init(t2);
            adjudicateJson = adjudicate.getAdjudicateInput(AC);
            adjudicate.customerChannelTab();
            adjudicate.verifyAdjudicateCustomerChannel();
            adjudicate.suggestionAndIntervalCC(adjudicateJson,AC);
            adjudicate.selectSave();
        }catch (Exception e){
            markTestAsFailure(e,t2);
        }finally {
            Assertion.finalizeSoftAsserts();
        }
    }

}
