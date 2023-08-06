package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.EmailGraphAPI;
import customer360.pageObjects.EmailGraphAPI.Email;
import io.restassured.response.Response;
import scripts.TestInit;

public class EmailGraphAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "emailGraph", dataProviderClass = APIDP.class)
	public void last3MonthEmailResponse(Customer360APIBean emailGraph) {
		ExtentTest t3 = pNode.createNode("emailGraph", emailGraph.testDescription);
		try {

			EmailGraphAPI emailResp = EmailGraphAPI.init(t3);
			Response resp = emailResp.emailGraphRespone(Email.LAST3MONTH, emailGraph);
			emailResp.validateEmailResp(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "emailGraph", dataProviderClass = APIDP.class)
	public void prior3MonthEmailResponse(Customer360APIBean emailGraph) {
		ExtentTest t3 = pNode.createNode("emailGraph", emailGraph.testDescription);
		try {

			EmailGraphAPI emailResp = EmailGraphAPI.init(t3);
			Response resp = emailResp.emailGraphRespone(Email.PRIOR3MONTH, emailGraph);
			emailResp.validateEmailResp(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
