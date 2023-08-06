package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aventstack.extentreports.ExtentTest;

import com.google.gson.Gson;
import customer360.apiPojo.apiResp.affiliatedAccountsGraph.AffiliatedAccountsGraphResp;
import customer360.data.beans.api.Customer360APIBean;
import scripts.BaseAPI;

public class affiliatedAccountsGraphAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(affiliatedAccountsGraphAPI.class);

	public affiliatedAccountsGraphAPI(ExtentTest t3) {
	}

	public static affiliatedAccountsGraphAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new affiliatedAccountsGraphAPI(pNode);
	}

	public Response affiliatedAccountsGraphAPIRespone(Customer360APIBean affiliatedAcc) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification affiliatedAccReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/affiliatedAccountsGraph/" + affiliatedAcc.customer_id).queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
				.all();
		
		Response resp = affiliatedAccReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateAffiliatedAccResp(Response resp) {
		pNode.info("Validate the Affiliated Account Graph api response");
		logToConsole.info("Validate the Affiliated Account Graph api response");
		
		AffiliatedAccountsGraphResp affAccResponse;
		affAccResponse = new Gson().fromJson(resp.asString(), AffiliatedAccountsGraphResp.class);
		System.out.println("The GSON response for YoY_Pct_Change i.e. first YoY_%_Change key is: " + affAccResponse.getData().getRowData().get(0).getYoY_Pct_Change());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = affAccResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for affiliated accounts graph response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			System.out.println("Pass");
			pNode.pass("Record fetched for affiliated accounts graph response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (affAccResponse.getResponse().getStatus() != 200) {
			pNode.fail(
					"Record is not fetched for affiliated accounts graph response, error: " + affAccResponse.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for affiliated accounts graph response successfully with status code 200");
		}
	}
}
