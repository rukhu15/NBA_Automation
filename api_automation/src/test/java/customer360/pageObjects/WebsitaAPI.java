package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.website.WebsiteResponse;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class WebsitaAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(WebsitaAPI.class);

	public WebsitaAPI(ExtentTest t3) {
	}

	public static WebsitaAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new WebsitaAPI(pNode);
	}

	public enum WebSite {
		LAST3MONTH, PRIOR3MONTH
	}

	public Response websiteKPIRespone(WebSite website, Customer360APIBean web) {

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification websiteReq = null;
		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		if (website.equals(WebSite.LAST3MONTH)) {
			websiteReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/websiteVisits/" + web.customer_id + "/last_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (website.equals(WebSite.PRIOR3MONTH)) {
			websiteReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/websiteVisits/" + web.customer_id + "/prior_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = websiteReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public Response websiteGraphRespone(WebSite website, Customer360APIBean web) {

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification websiteReq = null;
		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		if (website.equals(WebSite.LAST3MONTH)) {
			websiteReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/websiteVisitsGraph/" + web.customer_id + "/last_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (website.equals(WebSite.PRIOR3MONTH)) {
			websiteReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/websiteVisitsGraph/" + web.customer_id + "/prior_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = websiteReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateWebsiteResponse(Response resp) {
		pNode.info("Validate the website api response");
		logToConsole.info("Validate the website api response");
		ResponseSpecification genericResp = initAPIResp();
		WebsiteResponse websiteResp = resp.then().spec(genericResp).extract().as(WebsiteResponse.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = websiteResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for website response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for website response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(websiteResp.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for website response, error: " + websiteResp.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for website response successfully with status code 200");
		}
	}
}
