package application.apiPageObjects;

import apiPojo.apiReq.audience.AudienceReq;
import apiPojo.apiReq.audience.AudienceUpdateReq;
import apiPojo.apiReq.audience.filter_expr;
import apiPojo.apiReq.audience.rules;
import apiPojo.apiReq.channel.ChannelAssignReq;
import apiPojo.apiResp.audience.AudienceResp;
import apiPojo.apiResp.audience.AudienceRespGet;
import apiPojo.apiResp.channel.ChannelResp;
import apiTestScript.ScenarioAPI_Test;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.DesignAPIBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.StatusCode;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripts.BaseAPI;

import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * @author Prateek Sethi
 */
public class AudienceAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(ScenarioAPI_Test.class);

	public AudienceAPI(ExtentTest t3) {
	}

	public static AudienceAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new AudienceAPI(pNode);
	}

	public Response createAudienceInScenario(DesignAPIBean sc) {
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int brandId=DatabaseUtil.getValueFromColumn("brand_id",ConfigInput.dbAppSchemaName,"nba_brand","brand_name='"+ConfigInput.api_brandName+"'");
		AudienceReq audienceReqObj = new AudienceReq();
		filter_expr filterExpr = new filter_expr();

		JSONObject audienceFilterJson = new JSONObject(sc.filter_expr);
		JSONObject audienceFilterExprJson = audienceFilterJson.getJSONObject("filter_expr");
		JSONArray rulesArr = audienceFilterExprJson.getJSONArray("rules");
		List<rules> rulesList = new ArrayList<>();
		for (int i = 0; i < rulesArr.length(); i++) {
			rules ruleExpr = new rules();
			JSONObject ruleObj = rulesArr.getJSONObject(i);
			String ruleType = ruleObj.getString("type");
			ruleExpr.setType(ruleType);

			List<String> valueList = new ArrayList();
			JSONArray valueArr = ruleObj.getJSONArray("value");
			if (valueArr != null) {
				for (int j = 0; j < valueArr.length(); j++) {
					valueList.add(valueArr.getString(j));
				}
			}
			ruleExpr.setValue(valueList);
			ruleExpr.setKpi_id(ruleObj.getInt("kpi_id"));
			ruleExpr.setOperator(ruleObj.getString("operator"));
			ruleExpr.setBase_table(ConfigInput.wideBaseTable);
			ruleExpr.setColumn_name(ruleObj.getString("column_name"));
			ruleExpr.setDisplay_name(ruleObj.getString("display_name"));
			ruleExpr.setInterim_table(ruleObj.getString("interim_table"));
			ruleExpr.setCompound_table(ruleObj.getString("compound_table"));
			ruleExpr.setDisplay_category(ruleObj.getString("display_category"));
			ruleExpr.setRule_seqno(ruleObj.getInt("rule_seqno"));
			rulesList.add(ruleExpr);
		}
		filterExpr.setNot(false);
		filterExpr.setRules(rulesList);
		filterExpr.setCombinator("AND");
		filterExpr.setGroup_name("root");
		filterExpr.setRule_seqno(null);
		List<Integer> brand_list = new ArrayList<>();
		audienceReqObj.setFilter_expr(filterExpr);
		// Tp-do: to get brand-id from db
		brand_list.add(brandId);
		audienceReqObj.setBrand_list(brand_list);
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		audienceReqObj.setWrkspc_id(workSpaceID);
		audienceReqObj.setIs_template("False");
		audienceReqObj.setAudience_name(sc.audience_name);
		audienceReqObj.setCust_type(sc.cust_type);
		audienceReqObj.setIs_active(sc.is_active);
		audienceReqObj.setIs_deleted(sc.is_deleted);

		int userID = DatabaseUtil.getUserID(ConfigInput.apiUserName);
		audienceReqObj.setCreated_by(userID);
		audienceReqObj.setUpdated_by(userID);

		RequestSpecification audienceReq=getRequestSpecification("/nbadesigner-backend/nba/designAudience/scenarioId/" + scenarioID,
				null,audienceReqObj);

		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(audienceReq);
		System.out.println( "Request is:"+queryRequest.getURI());


		pNode.info("########  The decorated api request is: " + audienceReq.log().body(true));
		logToConsole.info("########  The decorated api request is: " + audienceReq.log().body(true));
		Response resp = audienceReq.when().post();
		pNode.info("########  The received api response is: " + resp.prettyPrint());
		logToConsole.info("########  The received api response is: " + resp.prettyPrint());
		return resp;
	}

	public void validateAudienceInScenario(Response resp, DesignAPIBean sc) {
		pNode.info("########  Validating the audience create api response ###################");
		logToConsole.info("########  Validating the audience create api response ###################");
		ResponseSpecification genericResp = initAPIResp();
		AudienceResp respAudience = resp.then().spec(genericResp).extract().as(AudienceResp.class);
		int wrkspc_id = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenario_id = DatabaseUtil.getScenarioID(sc.scenarioName);
		int audienceId = DatabaseUtil.getCurrentActiveAudienceIDForScenario(scenario_id, wrkspc_id);
		String expectedResponseDetail = "Audience " + sc.audience_name + "  With Id " + audienceId
				+ " Has Been Created Successfully";
		String actualResponseDetail = respAudience.getResponse().getDetail();
		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Created Audience not matches with expected, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Created Audience matches with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (!"Audience Created And Scenario Mapping Updated"
				.equalsIgnoreCase(respAudience.getResponse().getInstance())) {
			pNode.fail("Audience could not be created, error: " + respAudience.getResponse().getInstance());
		} else {
			pNode.pass("Audience created successfully");
		}

		if (Integer.parseInt(respAudience.getResponse().getStatus()) != 200) {
			pNode.fail("Audience could not be created, error: " + respAudience.getResponse().getStatus());
		} else {
			pNode.pass("Audience created successfully with status code 200");
		}
	}

	public Response getAudienceForGivenScenario(DesignAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int userID = DatabaseUtil.getUserID(ConfigInput.apiUserName);

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification scenarioGetReq = given().spec(genericReq).queryParam("userId", userID)
				.queryParam("wrkspcId", workSpaceID)
				.basePath("/nbadesigner-backend/nba/designAudience/scenarioId/" + scenarioID).log().all();
		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioGetReq);
		System.out.println( "Request is:"+queryRequest.getURI());
		Response resp = scenarioGetReq.when().get();
		return resp;
	}

	public Response assignChannelsToScenario(DesignAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int userID=DatabaseUtil.getUserID(ConfigInput.apiUserName);
		ChannelAssignReq channelObj = new ChannelAssignReq();
		List<String> arrChannels = Arrays.asList((sc.channel_name).split("\\|"));
		List<Integer> channelList = DatabaseUtil.getListOfChannelIds(arrChannels);
		channelObj.setChannel(channelList);
		Map<String,Object> queryMap= new HashMap<>();
		queryMap.put("workspaceId",workSpaceID);
		queryMap.put("scenarioId",scenarioID);
		queryMap.put("userId",userID);
		RequestSpecification scenarioGetReq =getRequestSpecification("/nbadesigner-backend/nba/channel", queryMap,channelObj);
		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioGetReq);
		System.out.println( "Request is:"+queryRequest.getURI());
		Response resp = scenarioGetReq.when().post();
		return resp;
	}

	public void validatechannelAssignmentToScenario(Response resp, DesignAPIBean sc) {
		pNode.info("########  Validating the created channel for scenario ###################");
		logToConsole.info("########  Validating the created channel for scenario ###################");
		ResponseSpecification genericResp = initAPIResp();
		ChannelResp respChannel = resp.then().spec(genericResp).extract().as(ChannelResp.class);

		if (respChannel.getResponse().getStatus() == 200) {
			pNode.pass("Created channel successfully with status code 200");
		} else {
			pNode.fail("Could not Create channel actual status code is: " + respChannel.getResponse().getStatus());
		}

		if (respChannel.getResponse().getDetail().equalsIgnoreCase("Channel Has Been Selected Successfully")) {
			pNode.pass("Channel created successfully");
		} else {
			pNode.fail("Could not Create channel");
		}

	}

	public void validateAudienceResponse(Response resp, DesignAPIBean sc) {
		ResponseSpecification genericResp = initAPIResp();
		AudienceRespGet audienceResp = resp.then().spec(genericResp).extract().as(AudienceRespGet.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Audience details fetched";
		String actualResponseDetail = audienceResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Audience details NOT fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Audience details fetched matches with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(audienceResp.getResponse().getStatus()) != 200) {
			pNode.fail("Audience details not fetched, error, error: " + audienceResp.getResponse().getStatus());
		} else {
			pNode.pass("Audience details fetched successfully with status code 200");
		}

		if (!"Audience details fetched".equalsIgnoreCase(audienceResp.getResponse().getInstance())) {
			pNode.fail("Audience details not fetched, error: " + audienceResp.getResponse().getInstance());
		} else {
			pNode.pass("Audience details fetched successfully");
		}
	}

	public Response updateAudience(DesignAPIBean sc) {

		int userID = DatabaseUtil.getUserID(ConfigInput.apiUserName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int wrkspc_id = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		String action_flag = "edit_multifield";
		int audienceId = DatabaseUtil.getCurrentActiveAudienceIDForScenario(scenarioID, wrkspc_id);

		AudienceUpdateReq audienceUpdate = new AudienceUpdateReq();
		filter_expr filterExpr = new filter_expr();

		JSONObject audienceFilterJson = new JSONObject(sc.filter_expr);
		JSONObject audienceFilterExprJson = audienceFilterJson.getJSONObject("filter_expr");
		JSONArray rulesArr = audienceFilterExprJson.getJSONArray("rules");
		List<rules> rulesList = new ArrayList<>();
		for (int i = 0; i < rulesArr.length(); i++) {
			rules ruleExpr = new rules();
			JSONObject ruleObj = rulesArr.getJSONObject(i);
			String ruleType = ruleObj.getString("type");
			ruleExpr.setType(ruleType);

			List<String> valueList = new ArrayList();
			JSONArray valueArr = ruleObj.getJSONArray("value");
			if (valueArr != null) {
				for (int j = 0; j < valueArr.length(); j++) {
					valueList.add(valueArr.getString(j));
				}
			}
			ruleExpr.setValue(valueList);
			ruleExpr.setOperator(ruleObj.getString("operator"));
			ruleExpr.setBase_table(ConfigInput.wideBaseTable);
			ruleExpr.setColumn_name(ruleObj.getString("column_name"));
			ruleExpr.setDisplay_name(ruleObj.getString("display_name"));
			ruleExpr.setInterim_table(ruleObj.getString("interim_table"));
			ruleExpr.setCompound_table(ruleObj.getString("compound_table"));
			ruleExpr.setDisplay_category(ruleObj.getString("display_category"));
			ruleExpr.setRule_seqno(ruleObj.getInt("rule_seqno"));
			rulesList.add(ruleExpr);
		}
		filterExpr.setNot(false);
		filterExpr.setRules(rulesList);
		filterExpr.setCombinator("AND");
		filterExpr.setGroup_name("root");
		filterExpr.setRule_seqno(null);

		audienceUpdate.setFilter_expr(filterExpr);
		audienceUpdate.setAudience_id(audienceId);
		audienceUpdate.setIs_template("false");
		audienceUpdate.setAudience_name(sc.audience_name);
		audienceUpdate.setCust_type(sc.cust_type);
		audienceUpdate.setIs_active(sc.is_active);
		audienceUpdate.setIs_deleted(sc.is_deleted);
		audienceUpdate.setAction_flag(action_flag);
		audienceUpdate.setWrkspc_id(wrkspc_id);

		Map<String,Object> queryParamsMap = new HashMap();
		queryParamsMap.put("wrkspcId",wrkspc_id);
		queryParamsMap.put("action_flag",action_flag);

		RequestSpecification audienceReq=getRequestSpecification("nbadesigner-backend/nba/designAudience/scenarioId/" + scenarioID, queryParamsMap,audienceUpdate);
		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(audienceReq);
		System.out.println( "Request is:"+queryRequest.getURI());

		Response resp = audienceReq.when().put();
		return resp;
	}

	public void validateUpdatedAudienceResponse(Response resp) {
		ResponseSpecification genericResp = initAPIResp();
		AudienceResp audienceResp = resp.then().spec(genericResp).extract().as(AudienceResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "NBA Audience Columns Updated";
		String actualResponseDetail = audienceResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Audience Columns NOT updated, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Audience Columns updated matches with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(audienceResp.getResponse().getStatus()) != 200) {
			pNode.fail("Audience Columns not updated, error, error: " + audienceResp.getResponse().getStatus());
		} else {
			pNode.pass("Audience Columns updated successfully with status code 200");
		}

		if (!"NBA Audience Columns Updated".equalsIgnoreCase(audienceResp.getResponse().getInstance())) {
			pNode.fail("Audience Columns not updated, error: " + audienceResp.getResponse().getInstance());
		} else {
			pNode.pass("Audience Columns updated successfully");
		}
	}
	
	public Response DelateAudienceAPI(DesignAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int audienceId = DatabaseUtil.getAudienceIDTobeDelatedForScenario(sc.audience_name);
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification audiencedelReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("workspaceId", workSpaceID).queryParam("audienceId", audienceId)
				.basePath("/nbadesigner-backend/nba/designAudience/scenarioId/" + scenarioID).log().all();
		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(audiencedelReq);
		System.out.println( "Request is:"+queryRequest.getURI());
		Response resp = audiencedelReq.when().delete();
		return resp;
	}

	public void validateAudienceDeleteAudience(Response resp, DesignAPIBean sc) {
		pNode.info("########  Validating the audience delete api response ###################");
		logToConsole.info("########  Validating the audience delete api response ###################");
		ResponseSpecification genericResp = initAPIResp();
		AudienceResp respAudience = resp.then().spec(genericResp).extract().as(AudienceResp.class);

		System.out.println(resp.prettyPrint());

		String expectedResponseDetail = "Scenario Audience updated";
		String actualResponseDetail = respAudience.getResponse().getDetail();
		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Scenario Audience not updated with expected, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Scenario Audience updated " + " expected response detail is: " + expectedResponseDetail
					+ "and  actual response detail is: " + actualResponseDetail);
		}

		if (!"Audience deleted successfully".equalsIgnoreCase(respAudience.getResponse().getInstance())) {
			pNode.fail("Audience could not be deleted, error: " + respAudience.getResponse().getInstance());
		} else {
			pNode.pass("Audience deleted successfully");
		}

		if (Integer.parseInt(respAudience.getResponse().getStatus()) != 200) {
			pNode.fail("Audience could not be delated, error: " + respAudience.getResponse().getStatus());
		} else {
			pNode.pass("Audience delated successfully with status code 200");
		}
	}

	public Response assignChannelsKeyTopicsToScenario(DesignAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int userID=DatabaseUtil.getUserID(ConfigInput.apiUserName);
		int brandId = DatabaseUtil.getValueFromColumn("brand_id", ConfigInput.dbAppSchemaName, "nba_brand",
				"brand_name='" + ConfigInput.api_brandName + "'");
		ChannelAssignReq channelObj = new ChannelAssignReq();
		List<String> arrChannels = Arrays.asList((sc.channel_name).split("\\|"));
		List<Integer> channelList = DatabaseUtil.getListOfChannelIds(arrChannels);
		channelObj.setChannel_id(channelList);
		Map<String,Object> queryMap= new HashMap<>();
		queryMap.put("wrkspcId",workSpaceID);
		queryMap.put("scnrId",scenarioID);
		queryMap.put("userId",userID);
		queryMap.put("brandId",brandId);
		RequestSpecification scenarioGetReq =getRequestSpecification("content-backend/cm/design/contentKeytopics", queryMap,channelObj);
		scenarioGetReq.log().all();

		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioGetReq);
		System.out.println( "Request is:"+queryRequest.getURI());

		Response resp = scenarioGetReq.when().post();
		return resp;
	}

	public void validatechannelKeyTopicsAssignmentToScenario(Response resp, DesignAPIBean sc) {
		pNode.info("########  Validating the created channel topics attached to Channel for scenario ###################");
		resp.prettyPrint();
		ResponseSpecification genericResp = initAPIResp();
		ChannelResp respChannel = resp.then().spec(genericResp).extract().as(ChannelResp.class);

		if (respChannel.getResponse().getStatus() == StatusCode.CODE_201.getCode()) {
			pNode.pass("Created keytopics assigned to channel successfully with status code 201");
		} else {
			pNode.fail("Could not assign created keytopics to channel, actual status code is: " + respChannel.getResponse().getStatus());

		}

		if (respChannel.getResponse().getDetail().equalsIgnoreCase("The Selected Channel Keytopics mapping has been created successfully")) {
			pNode.pass("The Selected Channel Keytopics mapping has been created successfully");
		} else {
			pNode.fail("Could not Create the Selected Channel Keytopics mapping");
		}
	}
}
