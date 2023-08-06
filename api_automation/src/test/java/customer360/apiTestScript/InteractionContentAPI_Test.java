package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.InteractionContentAPI;
import customer360.pageObjects.InteractionContentAPI.Content;
import io.restassured.response.Response;
import scripts.TestInit;

public class InteractionContentAPI_Test extends TestInit {
	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionContent", dataProviderClass = APIDP.class)
	public void last3MonthResponse(Customer360APIBean content) {
		ExtentTest t3 = pNode.createNode("interactionContent", content.testDescription);
		try {

			InteractionContentAPI contentResp = InteractionContentAPI.init(t3);
			Response resp = contentResp.interactionContentResponse(Content.LAST3MONTH, content);
			contentResp.validateInteractionContent(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionContent", dataProviderClass = APIDP.class)
	public void prior3MonthResponse(Customer360APIBean content) {
		ExtentTest t3 = pNode.createNode("interactionContent", content.testDescription);
		try {

			InteractionContentAPI contentResp = InteractionContentAPI.init(t3);
			Response resp = contentResp.interactionContentResponse(Content.PRIOR3MONTH, content);
			contentResp.validateInteractionContent(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
