package tagManagement.pageObjects;

import static io.restassured.RestAssured.given;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import framework.utility.common.DatabaseUtil;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiResp.permissionDetails.PermissionResp;
import tagManagement.data.beans.api.TagManagementBean;

public class PermissionAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(PermissionAPI.class);

	public PermissionAPI(ExtentTest t3) {
	}

	public static PermissionAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new PermissionAPI(pNode);
	}

	public Response getPermission(TagManagementBean tagManag) {

		int tagSeqId = DatabaseUtil.getTagSeqID(tagManag.tag_name);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification permissionReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("content-backend/cm/permissionDetails").queryParam("tagSeqId", tagSeqId).log().all();
		Response resp = permissionReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;
	}

	public void validatePermission(Response resp) {

		pNode.info("Validate the permission api response");
		logToConsole.info("Validate the permission api response");
		ResponseSpecification genericResp = initAPIResp();
		PermissionResp permission = resp.then().spec(genericResp).extract().as(PermissionResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Brand and Channel permissions fetched successfully";
		String actualResponseDetail = permission.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for permission response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for permission response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(permission.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for permission response, error: " + permission.getResponse().getStatus());
		} else {
			pNode.pass("permission fetched successfully with status code 200");
		}
	}

}
