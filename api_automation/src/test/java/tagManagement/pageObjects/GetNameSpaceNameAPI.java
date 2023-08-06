package tagManagement.pageObjects;

import static io.restassured.RestAssured.given;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiResp.getNameSpace.NameSpaceResp;

public class GetNameSpaceNameAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(GetNameSpaceNameAPI.class);

	public GetNameSpaceNameAPI(ExtentTest t3) {
	}

	public static GetNameSpaceNameAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new GetNameSpaceNameAPI(pNode);
	}

	public Response getNameSpace() {

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification nameSpaceReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("content-backend/cm/nameSpace").queryParam("wrkspcId", workSpaceID)
				.queryParam("companyId", companyId).log().all();

		Response resp = nameSpaceReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;
	}

	public void validateNameSpace(Response resp) {

		pNode.info("Validate the namespace api response");
		logToConsole.info("Validate the namespace api response");
		ResponseSpecification genericResp = initAPIResp();
		NameSpaceResp nameSpace = resp.then().spec(genericResp).extract().as(NameSpaceResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Namespace fetched successfully";
		String actualResponseDetail = nameSpace.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for namespace response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for namespace response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(nameSpace.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for namespace response, error: " + nameSpace.getResponse().getStatus());
		} else {
			pNode.pass("Namespace fetched successfully with status code 200");
		}
	}
}
