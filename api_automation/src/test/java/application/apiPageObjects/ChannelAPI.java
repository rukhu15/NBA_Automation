package application.apiPageObjects;

import apiPojo.apiResp.GetSearchChannel.Categories;
import apiPojo.apiResp.GetSearchChannel.Channels;
import apiPojo.apiResp.GetSearchChannel.GetSearchChannelResponse;
import apiPojo.apiResp.SelectedChannelList.SelectedChannelList;

import com.aventstack.extentreports.ExtentTest;

import data.beans.api.ChannelAPIBean;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import io.restassured.specification.SpecificationQuerier;
import scripts.BaseAPI;
import java.util.ArrayList;

import java.util.List;
import static io.restassured.RestAssured.given;

public class ChannelAPI extends BaseAPI {

	private static ExtentTest pNode;

	public ChannelAPI(ExtentTest t3) {
	}

	public static ChannelAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new ChannelAPI(pNode);
	}

	public Response getsearchchannel(ChannelAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);

		String search_parameter = sc.search_channel;

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification searchchanneloGetReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("scenarioId", scenarioID).queryParam("search", search_parameter)
				.basePath("/nbadesigner-backend/nba/searchChannel");

		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(searchchanneloGetReq);
		System.out.println( "Request is:"+queryRequest.getURI());

		Response resp = searchchanneloGetReq.when().get();

		return resp;
	}

	public void validatesearchchannel(Response resp, ChannelAPIBean sc) {

		ResponseSpecification genericResp = initAPIResp();
		GetSearchChannelResponse respGetSearchChannelResponse = resp.then().spec(genericResp).extract()
				.as(GetSearchChannelResponse.class);

		System.out.println("The api response is: " + resp.prettyPrint());

		String expectedResponseDetail = "Channel details fetched";
		String actualResponseDetail = respGetSearchChannelResponse.getResponse().getDetail();

		List<Categories> categories = respGetSearchChannelResponse.getData().getCategories();

		for (int i = 0; i < categories.size(); i++)

		{

			List<Channels> channels = categories.get(i).getChannels();

			if (channels != null && !channels.isEmpty())

			{

				for (int j = 0; j < channels.size(); j++)

				{
					String name = channels.get(j).getChannel_name();

					List<String> chennel_name = new ArrayList<String>();

					chennel_name.add(name);

					if (!(chennel_name).contains(name)) {
						pNode.fail("Channel List not contains searched channel, {expected response detail is} : "
								+ sc.search_channel + chennel_name + "and {actual response detail is} : " + name);
					} else {
						pNode.pass("Channel List contains searched channel" + " expected response detail is: "
								+ sc.search_channel + "and  actual response detail is: " + name);
					}

				}

			} else {

				break;

			}

		}

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Channel details not fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Channel details fetched" + " expected response detail is: " + expectedResponseDetail
					+ "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(respGetSearchChannelResponse.getResponse().getStatus()) != 200) {
			pNode.fail(
					"AChannel details not fetched, error: " + respGetSearchChannelResponse.getResponse().getStatus());
		} else {
			pNode.pass("Channel details fetched successfully with status code 200");
		}

		if (!"Selected Channel details fetched"
				.equalsIgnoreCase(respGetSearchChannelResponse.getResponse().getInstance())) {
			pNode.fail("Selected Channel details not fetched , error: "
					+ respGetSearchChannelResponse.getResponse().getInstance());
		} else {
			pNode.pass("Selected Channel details fetched");

		}
	}

	public Response getchchannellist(ChannelAPIBean sc) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);

		System.out.println(workSpaceID);
		System.out.println(scenarioID);
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification SelectedChannellistGetReq = given().spec(genericReq).relaxedHTTPSValidation()
				.queryParam("workspaceId", workSpaceID).queryParam("scenarioId", scenarioID)
				.basePath("/nbadesigner-backend/nba/channel");

		QueryableRequestSpecification queryRequest = SpecificationQuerier.query(SelectedChannellistGetReq);
		System.out.println( "Request is:"+queryRequest.getURI());

		Response resp = SelectedChannellistGetReq.when().get();

		return resp;
	}

	public void validateselctedchannellistResponse(Response resp, ChannelAPIBean adj) {
		ResponseSpecification genericResp = initAPIResp();
		SelectedChannelList respAdjResponse = resp.then().spec(genericResp).extract().as(SelectedChannelList.class);

		System.out.println(resp.prettyPrint());

		String expectedResponseDetail = "Selected Channel details fetched";
		String actualResponseDetail = respAdjResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Selected Channel details not fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Selected Channel details fetched matches with expected. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(respAdjResponse.getResponse().getStatus()) != 200) {
			pNode.fail("Selected Channel details not fetched, error: " + respAdjResponse.getResponse().getStatus());
		} else {
			pNode.pass("Selected Channel details fetched successfully with status code 200");
		}

		if (!"Selected Channel details fetched".equalsIgnoreCase(respAdjResponse.getResponse().getInstance())) {
			pNode.fail("Selected Channel details not fetched, error: " + respAdjResponse.getResponse().getInstance());
		} else {
			pNode.pass("Selected Channel details fetched");
		}

		if (!"Selected Channel details fetched".equalsIgnoreCase(respAdjResponse.getResponse().getInstance())) {
			pNode.fail("Selected Channel details not fetched, error: " + respAdjResponse.getResponse().getInstance());
		} else {
			pNode.pass("Selected Channel details fetched");
		}

	}
}
