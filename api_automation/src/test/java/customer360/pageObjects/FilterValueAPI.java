package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.google.gson.Gson;

import customer360.apiPojo.apiResp.filterValues.FilterValuesResp;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import scripts.BaseAPI;

public class FilterValueAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(FilterValueAPI.class);

	public FilterValueAPI(ExtentTest t3) {
	}

	public static FilterValueAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new FilterValueAPI(pNode);
	}

	public Response getFilterValue() {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification filterValueReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("/nbadesigner-backend/nba/filterValue2").queryParam("wrkspcId", workSpaceID)
				.queryParam("brand_name", ConfigInput.api_brandName).queryParam("seg_grp_name", "HCP")
				.queryParam("customer_section", "c360").queryParam("companyId", companyId).log().all();

		Response resp = filterValueReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());

		return resp;

	}

	public void validateFilterResponse(Response resp) {
		pNode.info("########  Validating the fetched record api response ###################");
		logToConsole.info("########  Validating the fetched record api response ###################");

		FilterValuesResp fetchRecordResp = new Gson().fromJson(resp.asString(), FilterValuesResp.class);

		String expectedResponseDetail = "Filter Name Fetched";
		String actualResponseDetail = fetchRecordResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Profiling Data have not been Fetched, {expected response detail is} : " + expectedResponseDetail
					+ " and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Profiling Data Fetched " + " expected response detail is: " + expectedResponseDetail
					+ " and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(fetchRecordResp.getResponse().getStatus()) != 200) {
			pNode.fail("Profiling Data have not been Fetched, error: " + fetchRecordResp.getResponse().getStatus());
		} else {
			pNode.pass("Profiling Data have been Fetched successfully with status code 200");
		}
	}

}
