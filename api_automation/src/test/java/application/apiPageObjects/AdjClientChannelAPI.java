package application.apiPageObjects;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import framework.utility.globalConst.ConfigInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import apiPojo.apiReq.adjudicate.AdjClientChannelReq;
import apiPojo.apiReq.adjudicate.Channel;
import apiPojo.apiRes.adjudicateGET.RespAdjudicate;
import apiPojo.apiResp.adjudicate.AdjClientChannelResp;
import data.beans.api.AdjudicateAPIBean;
import framework.utility.common.DatabaseUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class AdjClientChannelAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(AdjClientChannelAPI.class);

	public AdjClientChannelAPI(ExtentTest t3) {
	}

	public static AdjClientChannelAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new AdjClientChannelAPI(pNode);
	}

	public Response addClientChannel(AdjudicateAPIBean adj) {
		Channel channel = new Channel();
		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int userID = DatabaseUtil.getUserID(ConfigInput.apiUserName);

		List<AdjClientChannelReq> clientChannelList = new ArrayList<>();

		JSONObject clientChannelJson = new JSONObject(adj.channel_data);

		JSONArray clientChannelArr = clientChannelJson.getJSONArray("channel_data");

		for (int i = 0; i < clientChannelArr.length(); i++) {
			JSONObject clientChannelObj = clientChannelArr.getJSONObject(i);
			AdjClientChannelReq clientChannelReqObj = new AdjClientChannelReq();
			clientChannelReqObj = new AdjClientChannelReq();
			String channelname = channel.setChannel_name(clientChannelObj.getString("channel_name"));
			int channelID = DatabaseUtil.getChannelID(channelname);
			clientChannelReqObj.setChannel_id(channelID);

			int maxLimit = clientChannelObj.getInt("max_sug_limit");
			clientChannelReqObj.setMax_sug_limit(maxLimit);

			int minIntervalDays = clientChannelObj.getInt("min_interval_days");
			clientChannelReqObj.setMin_interval_days(minIntervalDays);

			clientChannelList.add(clientChannelReqObj);

		}

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification clientChannelReq = given().spec(genericReq)
				.basePath("/nbadesigner-backend/nba/adjudicate/hcpChannel").queryParam("workspaceId", workSpaceID)
				.relaxedHTTPSValidation().queryParam("scenarioId", scenarioID).queryParam("userId", userID)
				.body(clientChannelList);

		pNode.info("########  The  api request is: " + clientChannelReq.log().body(true));
		logToConsole.info("########  The  api request is: " + clientChannelReq.log().body(true));
		Response resp = clientChannelReq.when().post();
		pNode.info("########  The received api response is: " + resp.prettyPrint());
		logToConsole.info("########  The received api response is: " + resp.prettyPrint());
		return resp;
	}

	public void validateClientChannelResponse(Response resp, AdjudicateAPIBean adj) {

		pNode.info("########  Validating the Client Channel create api response ###################");
		logToConsole.info("########  Validating the Client Channel create api response ###################");
		ResponseSpecification genericResp = initAPIResp();
		AdjClientChannelResp adjudicateResp = resp.then().spec(genericResp).extract().as(AdjClientChannelResp.class);

		if (Integer.parseInt(adjudicateResp.getResponse().getStatus()) != 201) {
			pNode.fail("Client Channel could not be created, error: " + adjudicateResp.getResponse().getStatus());
		} else {
			pNode.pass("Client Channel created successfully with status code 201");
		}

	}

	public Response GetAdjudicateClient_Channel(AdjudicateAPIBean adj) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification AdjudicateGetReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("workspaceId", workSpaceID).queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/adjudicate/hcpChannel");

		Response resp = AdjudicateGetReq.when().get();
		return resp;
	}

	public void validateAdjClient_ChanneltInResponse(Response resp, AdjudicateAPIBean adj) {
		ResponseSpecification genericResp = initAPIResp();
		RespAdjudicate respAdjResponse = resp.then().spec(genericResp).extract().as(RespAdjudicate.class);

		System.out.println("The api response is: " + resp.prettyPrint());

		String expectedResponseDetail = "Adjudicate Hcp Channel details fetched";
		String actualResponseDetail = respAdjResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Adjudicate Hcp Channel details NOT fetched, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass(
					"Adjudicate Hcp Channel details fetched matches with expected. " + " expected response detail is: "
							+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(respAdjResponse.getResponse().getStatus()) != 200) {
			pNode.fail(
					"Adjudicate Hcp Channel details NOT fetched, error: " + respAdjResponse.getResponse().getStatus());
		} else {
			pNode.pass("Adjudicate Hcp Channel details fetched successfully with status code 200");
		}

		if (!"Adjudicate Hcp Channel details fetched".equalsIgnoreCase(respAdjResponse.getResponse().getInstance())) {
			pNode.fail("Adjudicate Hcp Channel details NOT fetched, error: "
					+ respAdjResponse.getResponse().getInstance());
		} else {
			pNode.pass("Adjudicate Hcp Channel details fetched");
		}

	}

}
