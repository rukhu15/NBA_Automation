package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.interaction.PerformanceResp;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

import java.util.HashMap;
import java.util.Map;

public class NrxPerformance extends BaseAPI {

	private static final Logger logToConsole = LoggerFactory.getLogger(NrxPerformance.class);
	private static ExtentTest pNode;

	public NrxPerformance(ExtentTest t3) {
	}

	public static NrxPerformance init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new NrxPerformance(pNode);
	}

	public Response getNrxPerformance(performance perf, Customer360APIBean perform) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		RequestSpecification genericReq = initAPIReq();

		RequestSpecification nrxPerformanceReq = null;
		if (perf.equals(performance.MONTHLYPERFORMANCE)) {
			nrxPerformanceReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("/nbadesigner-backend/nba/nrxWeeklyInteraction/" + perform.customer_id + "/monthly")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID)
					.queryParam("companyId", companyId).log().all();
		} else if (perf.equals(performance.WEEKLYPERFORMANCE)) {
			nrxPerformanceReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("/nbadesigner-backend/nba/nrxWeeklyInteraction/" + perform.customer_id + "/weekly")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID)
					.queryParam("companyId", companyId).log().all();
		}

		Response resp = nrxPerformanceReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());

		return resp;

	}

	public void getValidatePerformanceResponse(Response resp) {
		pNode.info("Validate the NRX Interaction api response");
		logToConsole.info("Validate the NRX Interaction api response");
		ResponseSpecification genericResp = initAPIResp();
		PerformanceResp perfResp = resp.then().spec(genericResp).extract().as(PerformanceResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Record fetched for NRx Performance";
		String actualResponseDetail = perfResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record is not fetched for NRx Performance, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for NRx Performance. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(perfResp.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for NRx Performance, error: " + perfResp.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for NRx Performance successfully with status code 200");
		}
	}

	public enum performance {
		WEEKLYPERFORMANCE, MONTHLYPERFORMANCE
	}
}
