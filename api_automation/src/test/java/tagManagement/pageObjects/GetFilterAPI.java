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
import tagManagement.apiPojo.apiResp.getFilter.GetFilterResp;
import tagManagement.data.beans.api.TagManagementBean;

public class GetFilterAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(GetFilterAPI.class);

	public GetFilterAPI(ExtentTest t3) {
	}

	public static GetFilterAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new GetFilterAPI(pNode);
	}

	public Response getFilter(TagManagementBean tagManag) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
		int userId = DatabaseUtil.getUserID(ConfigInput.siqUserId);
		int brand_id = DatabaseUtil.getValueFromColumn("brand_id", ConfigInput.dbAppSchemaName, "nba_brand",
				"brand_name='" + tagManag.brandName + "'");

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification filterReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("content-backend/cm/filterContent").queryParam("brandId", brand_id)
				.queryParam("userId", userId).queryParam("wrkspcId", workSpaceID).queryParam("nmspcId", nameSpaceID)
				.log().all();

		Response resp = filterReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;
	}

	public void validateFilterResponse(Response resp) {

		pNode.info("Validate the tag details api response");
		logToConsole.info("Validate the tag details api response");
		ResponseSpecification genericResp = initAPIResp();
		GetFilterResp tag = resp.then().spec(genericResp).extract().as(GetFilterResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Filters fetched successfully";
		String actualResponseDetail = tag.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for filter response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for filter response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(tag.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for filter response, error: " + tag.getResponse().getStatus());
		} else {
			pNode.pass("Filters fetched successfully with status code 200");
		}
	}

}
