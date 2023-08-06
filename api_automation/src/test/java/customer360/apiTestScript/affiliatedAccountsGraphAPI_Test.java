package customer360.apiTestScript;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.affiliatedAccountsGraphAPI;
import io.restassured.response.Response;
import scripts.TestInit;

public class affiliatedAccountsGraphAPI_Test extends TestInit {

	@Test(priority = 1, groups = {"Customer360_API" }, 
			enabled = true, dataProvider = "affliatedAccount", 
					dataProviderClass = APIDP.class)
	public void affiliatedAccountsGraphAPIResponse(Customer360APIBean affiliatedAccounts) {
		ExtentTest t3 = pNode.createNode("affiliatedAccountsGraph", affiliatedAccounts.testDescription);
		try {

			affiliatedAccountsGraphAPI affiliatedAccResp = affiliatedAccountsGraphAPI.init(t3);
			Response resp = affiliatedAccResp.affiliatedAccountsGraphAPIRespone(affiliatedAccounts);
			affiliatedAccResp.validateAffiliatedAccResp(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
