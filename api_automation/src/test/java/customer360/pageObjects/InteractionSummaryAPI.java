package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.interactionSummary.InteractionSummaryResponse;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class InteractionSummaryAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(InteractionSummaryAPI.class);

	public InteractionSummaryAPI(ExtentTest t3) {
	}

	public static InteractionSummaryAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new InteractionSummaryAPI(pNode);
	}

	public enum TimeFrame {
		CURRENTWEEK, LAST13WEEKS, LAST4WEEKS
	}

	public Response interactionSummaryResponse(TimeFrame time, Customer360APIBean summary) {

		RequestSpecification genericReq = initAPIReq();
		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification InteractionSummaryReq = null;
		if (time.equals(TimeFrame.CURRENTWEEK)) {
			InteractionSummaryReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/interactionSummaryGraph/" + summary.customer_id + "/curr_week")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (time.equals(TimeFrame.LAST13WEEKS)) {
			InteractionSummaryReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath(
							"nbadesigner-backend/nba/interactionSummaryGraph/" + summary.customer_id + "/last_13_weeks")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (time.equals(TimeFrame.LAST4WEEKS)) {
			InteractionSummaryReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath(
							"nbadesigner-backend/nba/interactionSummaryGraph/" + summary.customer_id + "/last_4_weeks")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = InteractionSummaryReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateInteractionSummary(Response resp) {
		pNode.info("Validate the Interaction Summary api response");
		logToConsole.info("Validate the Interaction Summary api response");
		ResponseSpecification genericResp = initAPIResp();
		InteractionSummaryResponse interactionSummaryResponseResp = resp.then().spec(genericResp).extract()
				.as(InteractionSummaryResponse.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = interactionSummaryResponseResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for Interaction Summary, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for Interaction Summary. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(interactionSummaryResponseResp.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for Interaction Summary, error: "
					+ interactionSummaryResponseResp.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for Interaction Summary successfully with status code 200");
		}
	}

}
