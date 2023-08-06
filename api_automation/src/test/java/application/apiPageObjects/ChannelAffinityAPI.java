package application.apiPageObjects;

import com.aventstack.extentreports.ExtentTest;

import apiPojo.apiResp.channelAffinity.Data;
import apiPojo.apiResp.channelAffinity.channelAffinityScore;
import data.beans.api.ChannelAPIBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import scripts.BaseAPI;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ChannelAffinityAPI extends BaseAPI {
	
	private static ExtentTest pNode;

	public ChannelAffinityAPI(ExtentTest t3) {
	}

	public static ChannelAffinityAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new ChannelAffinityAPI(pNode);
	}
	
	public Response getChannelAffinityScore(ChannelAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		int audienceID = DatabaseUtil.getCurrentActiveAudienceIDForScenario(scenarioID,workSpaceID);
		System.out.println("Getting Channel Affinity Score based on below input received");
		System.out.println("Workspace ID is "+workSpaceID);
		System.out.println("Scenario ID is "+scenarioID);
		System.out.println("Audience ID is " +audienceID);
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification gettingChannelAffinityScore = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("audienceId", audienceID).queryParam("wrkspcId", workSpaceID).queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/channel/score");

		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(gettingChannelAffinityScore);
		System.out.println( "Request is:"+queryRequest.getURI());

		Response resp = gettingChannelAffinityScore.when().get();

		return resp;
	}
	
	public static void containsEngagementScore(Data jsonData) {
		if(jsonData.getModel_type_x().equalsIgnoreCase("CHANNEL ENGAGEMENT") & null != String.valueOf(jsonData.getAffinity()) ) {
			pNode.pass("Channel "+jsonData.getChannel_id()+" : "+jsonData.getChannel_name()+" having Affinity Engagement score "+jsonData.getAffinity());
			return;
		}
		pNode.fail("Channel "+jsonData.getChannel_id()+" : "+jsonData.getChannel_name()+" having Affinity Engagement score "+jsonData.getAffinity());		
	}
	
	public static void containsImpactScore(Data jsonData) {
		if(jsonData.getModel_type_y().equalsIgnoreCase("CHANNEL IMPACT") & null != String.valueOf(jsonData.getImpact()) ) {
			pNode.pass("Channel "+jsonData.getChannel_id()+" : "+jsonData.getChannel_name()+" having Affinity Impact score "+jsonData.getImpact());
			return;
		}
		pNode.fail("Channel "+jsonData.getChannel_id()+" : "+jsonData.getChannel_name()+" having Affinity Impact score"+jsonData.getImpact());		
	}
	
	public void validateChannelAffinityAPIResponse(Response resp, ChannelAPIBean chAf) {
		ResponseSpecification genericResp = initAPIResp();
		pNode.info("########  Validating the Channel Affinity api response ###################");
		channelAffinityScore respChAfResponse = resp.then().spec(genericResp).extract().as(channelAffinityScore.class);

		System.out.println(resp.prettyPrint());

		String expectedResponseDetail = "channel with score fetched";
		String actualResponseDetail = respChAfResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Selected Channel Affinity score not fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Selected Channel Affinity score fetched matches with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(respChAfResponse.getResponse().getStatus()) != 200) {
			pNode.fail("Selected Channel Affinity score not fetched, error: " + respChAfResponse.getResponse().getStatus());
		} else {
			pNode.pass("Selected Channel Affinity score fetched successfully with status code 200");		
			for ( Data data : respChAfResponse.getData() )
			{
				if (data.getChannel_id() == 1 || data.getChannel_id() == 8) {
					containsImpactScore(data);		
				}
				else if (data.getChannel_id()==10 || data.getChannel_id()==11 || data.getChannel_id()==13 || data.getChannel_id()==15 || data.getChannel_id()==16) {
					containsImpactScore(data);	
					containsEngagementScore(data);
				}
			}
		}
	}

}
