package testscripts;

import application.pageObjects.DesignPage;
import application.pageObjects.HomePage;
import application.pageObjects.ScenariosPage;
import com.aventstack.extentreports.ExtentTest;
import data.beans.DesignPageBean;
import dataProvidersPackage.UIDP;
import framework.utility.common.Assertion;
import org.testng.annotations.Test;
import scripts.TestInit;

/**
 * @author Prateek Sethi
 */
public class DesignPage_Test extends TestInit {

    @Test(priority = 1, groups = {
            "SANITY" }, enabled = true, dataProvider = "createDesign", dataProviderClass = UIDP.class)
    public void DesignPage_TC_Create(DesignPageBean de) {
        ExtentTest t2 = pNode.createNode("DesignPage_TC_Create", de.testDescription);
        try {
            DesignPage designPage = DesignPage.init(t2);
            ScenariosPage scenarioPage = ScenariosPage.init(t2);
            HomePage homePage = HomePage.init(t2);

            scenarioPage.navigateToScenarioPage();
            homePage.cancelSideNavigation();
            scenarioPage.selectAndClickScenario(de.scenarioName,"Inactive");
            designPage.createAudience(de.audienceName);
            designPage.createChannles(de);
            designPage.openContentSection();
           // designPage.checkMessageList();
            designPage.waitTillDisappearingOfSpinner();
        } catch (Exception e) {
            markTestAsFailure(e, t2);
        }
        Assertion.finalizeSoftAsserts();
    }
}
