package testscripts;

import application.pageObjects.CustomerProfilingPage;
import application.pageObjects.HomePage;
import application.pageObjects.ScenariosPage;
import com.aventstack.extentreports.ExtentTest;
import data.beans.DesignPageBean;
import dataProvidersPackage.UIDP;
import org.json.JSONArray;
import org.testng.annotations.Test;
import scripts.TestInit;

public class CustomerIntelligence_Test extends TestInit {

    @Test(priority = 1, groups = {"SANITY"}, enabled = true, dataProvider = "createDesign", dataProviderClass = UIDP.class)
    public void Customer_Profiling_Test(DesignPageBean SC) {
        ExtentTest t1 = pNode.createNode("Customer Profiling Page","To automate customer profiling Filters");
        try {
            JSONArray customerJson;
            CustomerProfilingPage filters = CustomerProfilingPage.init(t1);
            filters.sideNavForProfiling();
            filters.selectBrandInProfiling(SC);
            filters.selectTabOnProfiling();
            customerJson = filters.getCustomerJson(SC);
            filters.selectFilterDropDown(customerJson);
            filters.searchFilter(customerJson);
            filters.saveFilter(customerJson);
        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }
    }

    @Test(priority = 2, groups = {"SANITY"}, enabled = true, dataProvider = "createDesign", dataProviderClass = UIDP.class)
    public void Customer_ProfilingWithDesign_Test(DesignPageBean de){
        ExtentTest t2 = pNode.createNode("Scenario Design Load","To automate Load functionality in Design page in scenario");
        try {
            JSONArray customerJson;
            ScenariosPage scenarioPage = ScenariosPage.init(t2);
            HomePage homePage = HomePage.init(t2);
            CustomerProfilingPage filters = CustomerProfilingPage.init(t2);

            scenarioPage.navigateToScenarioPage();
            homePage.cancelSideNavigation();
            scenarioPage.selectAndClickScenario(de.scenarioName,"Inactive");
            filters.designPageLoadCheck();
            customerJson = filters.getCustomerJson(de);
            filters.checkLoadFilterPresent(customerJson);
        }catch (Exception e){
            markTestAsFailure(e,t2);
        }
    }
}
