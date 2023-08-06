package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.ChannelInteractionAPI;
import customer360.pageObjects.ChannelInteractionAPI.Channel;
import io.restassured.response.Response;
import scripts.TestInit;

public class ChannelInteractionAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "channelInteraction", dataProviderClass = APIDP.class)
	public void last3MonthResponse(Customer360APIBean channel) {
		ExtentTest t3 = pNode.createNode("channelInteraction", channel.testDescription);
		try {

			ChannelInteractionAPI channelResp = ChannelInteractionAPI.init(t3);
			Response resp = channelResp.interactionChannelResponse(Channel.LAST3MONTH, channel);
			channelResp.validateChannelInteraction(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "channelInteraction", dataProviderClass = APIDP.class)
	public void prior3MonthResponse(Customer360APIBean channel) {
		ExtentTest t3 = pNode.createNode("channelInteraction", channel.testDescription);
		try {

			ChannelInteractionAPI channelResp = ChannelInteractionAPI.init(t3);
			Response resp = channelResp.interactionChannelResponse(Channel.PRIOR3MONTH, channel);
			channelResp.validateChannelInteraction(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
