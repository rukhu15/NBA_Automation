package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.email.EmailResp;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class EmailGraphAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(EmailGraphAPI.class);

	public EmailGraphAPI(ExtentTest t3) {
	}

	public static EmailGraphAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new EmailGraphAPI(pNode);
	}

	public enum Email {
		LAST3MONTH, PRIOR3MONTH
	}

	public Response emailGraphRespone(Email email, Customer360APIBean emailGraph) {

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification emailReq = null;
		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		if (email.equals(Email.LAST3MONTH)) {
			emailReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/emailGraph/" + emailGraph.customer_id + "/last_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (email.equals(Email.PRIOR3MONTH)) {
			emailReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/emailGraph/" + emailGraph.customer_id + "/prior_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = emailReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateEmailResp(Response resp) {
		pNode.info("Validate the email graph api response");
		logToConsole.info("Validate the email graph api response");
		ResponseSpecification genericResp = initAPIResp();
		EmailResp emailResponse = resp.then().spec(genericResp).extract().as(EmailResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = emailResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for email graph response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for email graph response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(emailResponse.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for email graph response, error: "
					+ emailResponse.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for email graph response successfully with status code 200");
		}
	}

}
