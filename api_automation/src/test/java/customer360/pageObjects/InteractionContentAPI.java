package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.interactionContent.ContentResponse;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class InteractionContentAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(InteractionFieldsResponseAPI.class);

	public InteractionContentAPI(ExtentTest t3) {
	}

	public static InteractionContentAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new InteractionContentAPI(pNode);
	}

	public enum Content {
		LAST3MONTH, PRIOR3MONTH
	}

	public Response interactionContentResponse(Content content, Customer360APIBean field) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification contentReq = null;
		if (content.equals(Content.LAST3MONTH)) {
			contentReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/content/" + field.customer_id + "/last_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (content.equals(Content.PRIOR3MONTH)) {
			contentReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/content/" + field.customer_id + "/prior_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = contentReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateInteractionContent(Response resp) {
		pNode.info("Validate the Interaction content api response");
		logToConsole.info("Validate the Interaction content api response");
		ResponseSpecification genericResp = initAPIResp();
		ContentResponse fieldResp = resp.then().spec(genericResp).extract().as(ContentResponse.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = fieldResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for interaction content response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for interaction content response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(fieldResp.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for interaction content response, error: "
					+ fieldResp.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for interaction content response successfully with status code 200");
		}
	}
}
