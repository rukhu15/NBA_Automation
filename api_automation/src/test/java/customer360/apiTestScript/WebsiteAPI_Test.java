package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.WebsitaAPI;
import customer360.pageObjects.WebsitaAPI.WebSite;
import io.restassured.response.Response;
import scripts.TestInit;

public class WebsiteAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "webSite", dataProviderClass = APIDP.class)
	public void last3MonthWebsiteKPIResponse(Customer360APIBean website) {
		ExtentTest t3 = pNode.createNode("webSite", website.testDescription);
		try {

			WebsitaAPI websiteResp = WebsitaAPI.init(t3);
			Response resp = websiteResp.websiteKPIRespone(WebSite.LAST3MONTH, website);
			websiteResp.validateWebsiteResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "webSite", dataProviderClass = APIDP.class)
	public void prior3MonthWebsiteKPIResponse(Customer360APIBean website) {
		ExtentTest t3 = pNode.createNode("webSite", website.testDescription);
		try {

			WebsitaAPI websiteResp = WebsitaAPI.init(t3);
			Response resp = websiteResp.websiteKPIRespone(WebSite.PRIOR3MONTH, website);
			websiteResp.validateWebsiteResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "webSite", dataProviderClass = APIDP.class)
	public void last3MonthWebsiteGraphResponse(Customer360APIBean website) {
		ExtentTest t3 = pNode.createNode("webSite", website.testDescription);
		try {

			WebsitaAPI websiteResp = WebsitaAPI.init(t3);
			Response resp = websiteResp.websiteGraphRespone(WebSite.LAST3MONTH, website);
			websiteResp.validateWebsiteResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "webSite", dataProviderClass = APIDP.class)
	public void prior3MonthWebsiteGraphResponse(Customer360APIBean website) {
		ExtentTest t3 = pNode.createNode("webSite", website.testDescription);
		try {

			WebsitaAPI websiteResp = WebsitaAPI.init(t3);
			Response resp = websiteResp.websiteGraphRespone(WebSite.PRIOR3MONTH, website);
			websiteResp.validateWebsiteResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
