package apiTestScript;

import application.apiPageObjects.KPI_API;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.KPIBuilderAPIBean;
import dataProvidersPackage.APIDP;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;

/**
 * @author Prateek Sethi
 */
public class KPIBuilderAPI_Test extends TestInit {

	@Test(priority = 0, groups = {
			"api","clean" }, enabled = true, alwaysRun = true, dataProvider = "postFormulaCreate", dataProviderClass = APIDP.class)
	public void deactivateNdeleteKPI_Tc(KPIBuilderAPIBean fb) {
		ExtentTest t3 = pNode.createNode("deactivateNdeleteKPI_Tc", fb.kpiName);
		try {
			KPI_API KPIAPI = KPI_API.init(t3);
			if (DatabaseUtil.isKPIExistsInDb(fb.kpiName)) {
				System.out.println(
						"KPI: " + fb.kpiName + " exist in db therefore de-activating then will delete from db");
				KPIAPI.deactivateActivateKPI(fb, true);
				KPIAPI.deleteExistingKPI(ConfigInput.workspaceName, fb.kpiName);
			}
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

    @Test(priority = 1, groups = {
            "api" }, enabled = true, dataProvider = "postFormulaCreate", dataProviderClass = APIDP.class)
    public void postCreateFormula_Tc(KPIBuilderAPIBean fb) {
        ExtentTest t3 = pNode.createNode("postCreateFormula_Tc", fb.testDescription);
        try {
            KPI_API KPIAPI = KPI_API.init(t3);
            Response resp= KPIAPI.createFormula(fb);
            KPIAPI.deactivateActivateKPI(fb,false);
            KPIAPI.validateCreatedFormula(resp,fb,false);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 2, groups = {
            "api" }, enabled = true, dataProvider = "postFormulaUpdate", dataProviderClass = APIDP.class)
    public void postUpdateFormula_Tc(KPIBuilderAPIBean fb) {
        ExtentTest t3 = pNode.createNode("postCreateFormula_Tc", fb.testDescription);
        boolean result=DatabaseUtil.isKPIExistsInDb(fb.kpiName);
        try {
            KPI_API KPIAPI = KPI_API.init(t3);
            Response resp= KPIAPI.updateFormula(fb);
            KPIAPI.validateCreatedFormula(resp,fb,result);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 3, groups = {
            "api"}, enabled = true, dataProvider = "postFormulaCreate", dataProviderClass = APIDP.class)
    public void saveFormula_Tc(KPIBuilderAPIBean fb) {
        ExtentTest t3 = pNode.createNode("saveFormula_Tc", fb.testDescription);
        try {
            KPI_API KPIAPI = KPI_API.init(t3);
            boolean result = DatabaseUtil.isKPIExistsInDb(fb.kpiName);
            if (result) {
                System.out.println("KPI: " + fb.kpiName + " exist in db therefore saving this kpi");
                Response resp = KPIAPI.saveKPI(fb);
                KPIAPI.validateCreatedFormula(resp, fb,result);
                if(fb.distinctCustomerCountQuery !=null && !fb.distinctCustomerCountQuery.isEmpty()){
                    KPIAPI.validateKPIOutput(fb);
                }
            }
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }

}