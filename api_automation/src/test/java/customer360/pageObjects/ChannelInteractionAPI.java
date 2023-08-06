package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.channelInteraction.ChannelResponse;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class ChannelInteractionAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(ChannelInteractionAPI.class);

	public ChannelInteractionAPI(ExtentTest t3) {
	}

	public static ChannelInteractionAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new ChannelInteractionAPI(pNode);
	}

	public enum Channel {
		LAST3MONTH, PRIOR3MONTH
	}

	public Response interactionChannelResponse(Channel channelInteraction, Customer360APIBean channel) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification ChannelReq = null;
		if (channelInteraction.equals(Channel.LAST3MONTH)) {
			ChannelReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/channelInteraction/" + channel.customer_id + "/last_3_months")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (channelInteraction.equals(Channel.PRIOR3MONTH)) {
			ChannelReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/channelInteraction/" + channel.customer_id + "/prior_3_months")
					.queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = ChannelReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateChannelInteraction(Response resp) {
		pNode.info("Validate the Channel Interaction api response");
		logToConsole.info("Validate the Channel Interaction api response");
		ResponseSpecification genericResp = initAPIResp();
		ChannelResponse channelResp = resp.then().spec(genericResp).extract().as(ChannelResponse.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Record fetched for Channel Interaction";
		String actualResponseDetail = channelResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for Channel Interaction response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for Channel Interaction response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(channelResp.getResponse().getStatus()) != 200) {
			pNode.fail("Record is not fetched for Channel Interaction response, error: "
					+ channelResp.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for Channel Interaction response successfully with status code 200");
		}
	}

}
