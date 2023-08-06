package application.apiPageObjects;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import framework.utility.globalConst.ConfigInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import apiPojo.apiReq.adjudicate.AdjChannelReq;
import apiPojo.apiReq.adjudicate.Channel;
import apiPojo.apiReq.adjudicate.ChannelData;
import apiPojo.apiRes.adjudicateGET.Data;
import apiPojo.apiRes.adjudicateGET.RespAdjudicate;
import apiPojo.apiResp.adjudicate.AdjudicateResp;
import apiTestScript.AdjudicateAPI_Test;
import data.beans.api.AdjudicateAPIBean;
import framework.utility.common.DatabaseUtil;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class AdjChannelAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(AdjudicateAPI_Test.class);

	public AdjChannelAPI(ExtentTest t3) {
	}

	public static AdjChannelAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new AdjChannelAPI(pNode);
	}

	public Response addAdjudicateChannel(AdjudicateAPIBean adj) {

		Channel channel = new Channel();
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);

		AdjChannelReq AdjudicateReqObj = new AdjChannelReq();

		List<ChannelData> channelIdList = new ArrayList<>();
		JSONObject channelExpr = new JSONObject(adj.channel_data);
		JSONArray channelArr = channelExpr.getJSONArray("channel_data");

		for (int i = 0; i < channelArr.length(); i++) {
			JSONObject channelJson = channelArr.getJSONObject(i);
			ChannelData channelDataObj = new ChannelData();
			String channelname = channel.setChannel_name(channelJson.getString("channel_name"));
			int channelID = DatabaseUtil.getChannelID(channelname);
			channelDataObj.setChannel_id(channelID);

			int maxLimit = channelJson.getInt("max_limit");
			channelDataObj.setMax_limit(maxLimit);

			channelIdList.add(channelDataObj);
		}

		AdjudicateReqObj.setChannel_data(channelIdList);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification adjReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("workspaceId", workSpaceID).queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/adjudicate/channel").body(AdjudicateReqObj);
		pNode.info("URI for the api request is:" + adjReq.log().body(true));
		logToConsole.info("URI for the api request is: " + adjReq.log().body(true));

		Response resp = adjReq.when().post();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;
	}

	public void validateRseponseInAdjChannel(Response resp, AdjudicateAPIBean adj) {
		pNode.info("Validate the adjudicate channel api response");
		logToConsole.info("Validate the adjudicate channel api response");
		ResponseSpecification genericResp = initAPIResp();
		AdjudicateResp adjudicateResp = resp.then().spec(genericResp).extract().as(AdjudicateResp.class);

		if (Integer.parseInt(adjudicateResp.getResponse().getStatus()) != 200) {
			pNode.fail("Channel could not be created, error: " + adjudicateResp.getResponse().getStatus());
		} else {
			pNode.pass("Channel created successfully with status code 200");
		}
	}

	public Response GetAdjudicateChannel(AdjudicateAPIBean adj) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification AdjudicateGetReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("workspaceId", workSpaceID).queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/adjudicate/channel");

		Response resp = AdjudicateGetReq.when().get();
		return resp;
	}

	public void validateAdjChannelInResponse(Response resp, AdjudicateAPIBean adj) {
		ResponseSpecification genericResp = initAPIResp();
		RespAdjudicate respAdjResponse = resp.then().spec(genericResp).extract().as(RespAdjudicate.class);
		System.out.println("The api response is: " + resp.prettyPrint());

		String expectedResponseDetail = "Adjudicate Channel details fetched";
		String actualResponseDetail = respAdjResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Adjudicate Channel details NOT fetched, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Adjudicate Channel details fetched matches with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(respAdjResponse.getResponse().getStatus()) != 200) {
			pNode.fail("Adjudicate Channel details not fetched, error, error: "
					+ respAdjResponse.getResponse().getStatus());
		} else {
			pNode.pass("Adjudicate Channel details fetched successfully with status code 200");
		}

		if (!"Adjudicate Channel details fetched".equalsIgnoreCase(respAdjResponse.getResponse().getInstance())) {
			pNode.fail("Adjudicate Channel details not fetched, error: " + respAdjResponse.getResponse().getInstance());
		} else {
			pNode.pass("Adjudicate Channel details fetched successfully");
		}

	}

}
