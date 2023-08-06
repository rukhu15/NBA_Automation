package apiTestScript;

import application.apiPageObjects.ChannelAPI;
import application.apiPageObjects.ChannelAffinityAPI;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.ChannelAPIBean;
import dataProvidersPackage.APIDP;
import io.restassured.response.Response;
import scripts.TestInit;

public class ChannelAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"api" }, enabled = true, dataProvider = "getsearchChannel", dataProviderClass = APIDP.class)
	public void GetSearchChannelApi_Tc(ChannelAPIBean Channel) {
		ExtentTest t3 = pNode.createNode("GetSearchChannelApi_Tc", Channel.testDescription);
		try {
			ChannelAPI channelapi = ChannelAPI.init(t3);
			pNode.info("Going to send the API GET req for search channel:  " + Channel.scenarioName);
			Response resp = channelapi.getsearchchannel(Channel);
			channelapi.validatesearchchannel(resp, Channel);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"api" }, enabled = true, dataProvider = "getselectedChannellist", dataProviderClass = APIDP.class)
	public void GetSelectedChannelListApi_Tc(ChannelAPIBean adj) {
		ExtentTest t3 = pNode.createNode("GetSelectedChannelListApi_Tc", adj.testDescription);
		try {
			ChannelAPI channelapi = ChannelAPI.init(t3);
			pNode.info("Going to send the API GET req for selected channel list :  " + adj.scenarioName);
			Response resp = channelapi.getchchannellist(adj);
			channelapi.validateselctedchannellistResponse(resp, adj);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
	
	@Test(priority = 3, groups = {
	"api" }, enabled = true, dataProvider = "getChannelAffinityScore", dataProviderClass = APIDP.class)
	public void getChannelAffinityAPI(ChannelAPIBean channelAff) {
		ExtentTest t3 = pNode.createNode("GetChannelAffinityAPI_TC", channelAff.testDescription);
		try {
			ChannelAffinityAPI channelAffinityAPI = ChannelAffinityAPI.init(t3);
			pNode.info("Going to send the API GET req for Channel Affinity Score :  " + channelAff.scenarioName);
			Response resp = channelAffinityAPI.getChannelAffinityScore(channelAff);
			channelAffinityAPI.validateChannelAffinityAPIResponse(resp, channelAff);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
