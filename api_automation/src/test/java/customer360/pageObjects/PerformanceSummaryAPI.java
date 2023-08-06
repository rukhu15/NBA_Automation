package customer360.pageObjects;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.given;
import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.performanceSummary.PerfSummaryResp;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class PerformanceSummaryAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(PerformanceSummaryAPI.class);

	public PerformanceSummaryAPI(ExtentTest t3) {
	}

	public static PerformanceSummaryAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new PerformanceSummaryAPI(pNode);
	}

	public Response performanceSummaryResp(Customer360APIBean perf) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification performanceSummaryReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("nbadesigner-backend/nba/performanceSummary/" + perf.customer_id).queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
				.all();

		Response resp = performanceSummaryReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;
	}

	public void validatePerfSummaryResp(Response resp) {
		pNode.info("Validate the Performance summary api response");
		logToConsole.info("Validate the Performance summary api response");

		ResponseSpecification genericResp = initAPIResp();
		PerfSummaryResp performanceresp = resp.then().spec(genericResp).extract().as(PerfSummaryResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = performanceresp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail(
					"Record have not been fetched for Performance summary graph response, {expected response detail is} : "
							+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for Performance summary graph response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(performanceresp.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for Performance summary graph response, error: "
					+ performanceresp.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for Performance summary graph response successfully with status code 200");
		}
	}
}
