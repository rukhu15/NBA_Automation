package application.apiPageObjects;

import static io.restassured.RestAssured.given;

import java.util.List;

import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

import apiPojo.apiPostResponseAdjudicate.PostDataResponse;

import apiPojo.apiReq.AdjudicateClientPost.AdjudicateClientReq;
import apiPojo.apiRes.adjudicateGET.AdjudicateResp;
import apiPojo.apiRes.adjudicateGET.Data;
import apiPojo.apiRes.adjudicateGET.RespAdjudicate;
import apiPojo.apiResp.scenario.ScenarioResp;
import data.beans.api.AdjudicateAPIBean;
import data.beans.api.ScenarioAPIBean;
import framework.utility.common.DatabaseUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class AdjClientAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(AdjClientAPI.class);

	public AdjClientAPI(ExtentTest t3) {

	}

	public static AdjClientAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new AdjClientAPI(pNode);
	}

	public Response CreateAdjudicateClient(AdjudicateAPIBean adj)

	{
		AdjudicateClientReq adjclientreq = new AdjudicateClientReq();

		String max_sug_limit = adj.max_sug_limit;
		int max_sug_limitvalue = Integer.parseInt(max_sug_limit);

		System.out.println(max_sug_limitvalue);

		adjclientreq.setMax_sug_limit(max_sug_limit);

		String min_interval_days = adj.min_interval_days;
		int min_interval_daysvalue = Integer.parseInt(min_interval_days);

		System.out.println(min_interval_daysvalue);

		adjclientreq.setMin_interval_days(min_interval_daysvalue);

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification AdjudicatePostReq = given().spec(genericReq).queryParam("workspaceId", workSpaceID)
				.relaxedHTTPSValidation().queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/adjudicate/hcp").body(adjclientreq);

		pNode.info("########  The decorated api request is: " + AdjudicatePostReq.log().body(true));
		logToConsole.info("########  The decorated api request is: " + AdjudicatePostReq.log().body(true));
		Response resp = AdjudicatePostReq.when().post();
		pNode.info("########  The received api response is: " + resp.prettyPrint());
		logToConsole.info("########  The received api response is: " + resp.prettyPrint());
		return resp;

	}

	public void ValidateClientPost(Response resp, AdjudicateAPIBean adj) {
		pNode.info("########  Validating the api response ###################");
		logToConsole.info("########  Validating the api response ###################");
		ResponseSpecification genericResp = initAPIResp();
		PostDataResponse postDataResponse = resp.then().spec(genericResp).extract().as(PostDataResponse.class);

		System.out.println(resp.prettyPrint());

		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int actualscenarioID = resp.jsonPath().get("data.scenario_id");
		int actualworkSpaceID = resp.jsonPath().get("data.wrkspc_id");

		String expectedResponseDetail = "Data is fetched";
		String actualResponseDetail = postDataResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Adjudicate Hcp not Created, {expected response detail is} : " + expectedResponseDetail
					+ " and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Adjudicate Hcp created. " + " expected response detail is: " + expectedResponseDetail
					+ " and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(postDataResponse.getResponse().getStatus()) != 201) {
			pNode.fail("Adjudicate Hcp Not created, error: " + postDataResponse.getResponse().getStatus());
		} else {
			pNode.pass("Adjudicate Hcp created successfully with status code 201");
		}

		if (!"Data is fetched".equalsIgnoreCase(postDataResponse.getResponse().getInstance())) {
			pNode.fail("Adjudicate Hcp not Created , error: " + postDataResponse.getResponse().getInstance());
		} else {
			pNode.pass("Adjudicate Hcp Created");

		}

		if (!(scenarioID == actualscenarioID)) {
			pNode.fail("The scenarioID not matches, : The with expected value is  " + actualscenarioID);

		}

		else {

			pNode.pass("The scenarioID  matches, : The with expected value is  " + actualscenarioID);

		}

		if (!(workSpaceID == actualworkSpaceID)) {
			pNode.fail("The workSpaceID not matches, : The with expected value is  " + actualworkSpaceID);

		}

		else {

			pNode.pass("The workSpaceID matches, : The with expected value is  " + actualworkSpaceID);
		}

	}

	public Response GetAdjudicateClient(AdjudicateAPIBean adj) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(adj.scenarioName);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification AdjudicateGetReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("workspaceId", workSpaceID).queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/adjudicate/hcp");

		Response resp = AdjudicateGetReq.when().get();
		return resp;
	}

	public void validateAdjClientInResponse(Response resp, AdjudicateAPIBean adj) {
		ResponseSpecification genericResp = initAPIResp();
		RespAdjudicate respAdjResponse = resp.then().spec(genericResp).extract().as(RespAdjudicate.class);

		System.out.println(resp.prettyPrint());

		String expectedResponseDetail = "Adjudicate Hcp details fetched";
		String actualResponseDetail = respAdjResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Adjudicate Hcp details NOT fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Adjudicate Hcp details fetched matches  with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(respAdjResponse.getResponse().getStatus()) != 200) {
			pNode.fail("Adjudicate Hcp details Not fetched, error: " + respAdjResponse.getResponse().getStatus());
		} else {
			pNode.pass("Adjudicate Hcp details fetched successfully with status code 200");
		}

		if (!"Adjudicate Hcp details fetched".equalsIgnoreCase(respAdjResponse.getResponse().getInstance())) {
			pNode.fail("Adjudicate Hcp details not fetched, error: " + respAdjResponse.getResponse().getInstance());
		} else {
			pNode.pass("Adjudicate Hcp details fetched");
		}

		if (!"Adjudicate Hcp details fetched".equalsIgnoreCase(respAdjResponse.getResponse().getInstance())) {
			pNode.fail("Adjudicate Hcp details not fetched, error: " + respAdjResponse.getResponse().getInstance());
		} else {
			pNode.pass("Adjudicate Hcp details fetched");
		}

	}

}
