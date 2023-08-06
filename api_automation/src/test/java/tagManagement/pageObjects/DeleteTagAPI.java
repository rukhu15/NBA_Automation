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
import tagManagement.apiPojo.apiResp.deleteTag.DeleteTagResp;
import tagManagement.data.beans.api.TagManagementBean;

public class DeleteTagAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(DeleteTagAPI.class);

	public DeleteTagAPI(ExtentTest t3) {
	}

	public static DeleteTagAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new DeleteTagAPI(pNode);
	}

	public Response deleteTag(TagManagementBean tagManag) {

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
		int tagSeqId = DatabaseUtil.getTagSeqID(tagManag.tag_name);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification tagReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("content-backend/cm/tag").queryParam("wrkspcId", workSpaceID)
				.queryParam("nmspcId", nameSpaceID).queryParam("tagSeqId", tagSeqId).log().all();

		Response resp = tagReq.when().delete();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;
	}

	public void validateDeleteTagResponse(Response resp, TagManagementBean tag) {

		pNode.info("Validate the tag details api response");
		logToConsole.info("Validate the tag details api response");
		ResponseSpecification genericResp = initAPIResp();
		DeleteTagResp deleteTag = resp.then().spec(genericResp).extract().as(DeleteTagResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "The tag " + tag.tag_name + " deleted successfully";
		String actualResponseDetail = deleteTag.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for tag response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for tag response. " + " expected response detail is: " + expectedResponseDetail
					+ "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(deleteTag.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for tag response, error: " + deleteTag.getResponse().getStatus());
		} else {
			pNode.pass("Tag deleted successfully with status code 200");
		}
	}
}
