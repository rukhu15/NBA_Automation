package testscripts;

import application.pageObjects.KpiPage;
import data.beans.KpiPageBean;
import dataProvidersPackage.UIDP;
import org.json.JSONObject;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class KpiPage_Test extends TestInit{

    @Test(priority = 1, groups = {"SANITY"}, enabled = true, dataProvider = "createKpi", dataProviderClass = UIDP.class)
    public void Event_Kpi_Creation(KpiPageBean kpiBean) {
        ExtentTest t1 = pNode.createNode("Event_Kpi_Creation", "Create event kpi");
        JSONObject kpiDetails=null;
        try {

            KpiPage kpiObject = KpiPage.init(t1);

            //Get the details of kpi to create
            kpiDetails = kpiObject.getKpiDetails(kpiBean,"event_kpi_single_variable");

            //Open kpi builder and click on new kpi button
            kpiObject.openKpiBuilderAndClickOnNewKpi(kpiDetails);

            //Create formula of kpi
            kpiObject.createKpiFormula(kpiDetails);

            //Set kpi name and category
            kpiObject.setKpiNameAndCategory(kpiDetails);

            //Select kpi availability
            kpiObject.setKpiAvailabilityAndSaveKpi(kpiDetails);

            //Search kpi
            kpiObject.searchKpi(kpiDetails);

            //Activate kpi
            kpiObject.activateKpi(kpiDetails, true);

            //Clear search
            kpiObject.clearSearchKpi();

        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }
        finally {
            Assertion.finalizeSoftAsserts();
        }
    }

    @Test(priority = 2, groups = {"SANITY"}, enabled = true, dataProvider = "createKpi", dataProviderClass = UIDP.class)
    public void Data_Kpi_Creation(KpiPageBean kpiBean) {
        ExtentTest t1 = pNode.createNode("Data_Kpi_Creation", "Create data kpi");
        JSONObject kpiDetails=null;
        try {

            KpiPage kpiObject = KpiPage.init(t1);

            //Get the details of kpi to create
            kpiDetails = kpiObject.getKpiDetails(kpiBean,"data_kpi_triple_variable");

            //Open kpi builder and click on new kpi button
            kpiObject.openKpiBuilderAndClickOnNewKpi(kpiDetails);

            //Create formula of kpi
            kpiObject.createKpiFormula(kpiDetails);

            //Set kpi name and category
            kpiObject.setKpiNameAndCategory(kpiDetails);

            //Select kpi availability
            kpiObject.setKpiAvailabilityAndSaveKpi(kpiDetails);

            //Search kpi
            kpiObject.searchKpi(kpiDetails);

            //Activate kpi
            kpiObject.activateKpi(kpiDetails, true);

            //Clear search
            kpiObject.clearSearchKpi();

        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }
        finally {
            Assertion.finalizeSoftAsserts();
        }
    }

}
