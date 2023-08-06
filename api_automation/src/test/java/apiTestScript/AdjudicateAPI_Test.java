package apiTestScript;

import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import application.apiPageObjects.AdjChannelAPI;
import application.apiPageObjects.AdjClientChannelAPI;
import application.apiPageObjects.AdjClientAPI;
import data.beans.api.AdjudicateAPIBean;
import dataProvidersPackage.APIDP;
import io.restassured.response.Response;
import scripts.TestInit;

public class AdjudicateAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"api" }, enabled = true, dataProvider = "addAdjudicateChannel", dataProviderClass = APIDP.class)
	public void PostAdjudictaeChannelApi_Tc(AdjudicateAPIBean adj) {
		ExtentTest t3 = pNode.createNode("PostAdjudictaeChannelApi_Tc", adj.testDescription);
		try {
			AdjChannelAPI adjudicateAPI = AdjChannelAPI.init(t3);
			pNode.info("Going to send the API POST req for adjudicate channel");
			Response resp = adjudicateAPI.addAdjudicateChannel(adj);
			adjudicateAPI.validateRseponseInAdjChannel(resp, adj);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"api" }, enabled = true, dataProvider = "addAdjudicateclient", dataProviderClass = APIDP.class)
	public void PostAdjudictaeClientApi_Tc(AdjudicateAPIBean adj) {
		ExtentTest t3 = pNode.createNode("PostAdjudictaeClientApi_Tc", adj.testDescription);
		try {
			AdjClientAPI adjAPI = AdjClientAPI.init(t3);
			pNode.info("Going to send the API POST req for adjudicate client :  " + adj.scenarioName);
			Response resp = adjAPI.CreateAdjudicateClient(adj);
			adjAPI.ValidateClientPost(resp, adj);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 3, groups = {
			"api" }, enabled = true, dataProvider = "addAdjClientChannel", dataProviderClass = APIDP.class)
	public void PostAdjudictaeClientChannelApi_Tc(AdjudicateAPIBean adj) {
		ExtentTest t3 = pNode.createNode("PostAdjudictaeClientChannelApi_Tc", adj.testDescription);
		try {
			AdjClientChannelAPI adjudicateAPI = AdjClientChannelAPI.init(t3);
			pNode.info("Going to send the API POST req for adjudicate client-channel");
			Response resp = adjudicateAPI.addClientChannel(adj);
			adjudicateAPI.validateClientChannelResponse(resp, adj);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 4, groups = {
			"api" }, enabled = true, dataProvider = "getAdjudicateAPI", dataProviderClass = APIDP.class)
	public void GetAdjudicateChannelApi_Tc(AdjudicateAPIBean adj) {
		ExtentTest t3 = pNode.createNode("GetAdjudicateChannelApi_Tc", adj.testDescription);
		try {
			AdjChannelAPI adjAPI = AdjChannelAPI.init(t3);
			pNode.info("Going to send the API GET req for adjudicate channel :  " + adj.scenarioName);
			Response resp = adjAPI.GetAdjudicateChannel(adj);
			adjAPI.validateAdjChannelInResponse(resp, adj);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 5, groups = {
			"api" }, enabled = true, dataProvider = "getAdjudicateAPI", dataProviderClass = APIDP.class)
	public void GetAdjudicateClientApi_Tc(AdjudicateAPIBean adj) {
		ExtentTest t3 = pNode.createNode("GetAdjudicateClientApi_Tc", adj.testDescription);
		try {
			AdjClientAPI adjAPI = AdjClientAPI.init(t3);
			pNode.info("Going to send the API GET req for adjudicate client :  " + adj.scenarioName);
			Response resp = adjAPI.GetAdjudicateClient(adj);
			adjAPI.validateAdjClientInResponse(resp, adj);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 6, groups = {
			"api" }, enabled = true, dataProvider = "getAdjudicateAPI", dataProviderClass = APIDP.class)
	public void GetAdjudicateClientChannelApi_Tc(AdjudicateAPIBean adj) {
		ExtentTest t3 = pNode.createNode("GetAdjudicateClientChannelApi_Tc", adj.testDescription);
		try {
			AdjClientChannelAPI adjAPI = AdjClientChannelAPI.init(t3);
			pNode.info("Going to send the API GET req for adjudicate client-channel:  " + adj.scenarioName);
			Response resp = adjAPI.GetAdjudicateClient_Channel(adj);
			adjAPI.validateAdjClient_ChanneltInResponse(resp, adj);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
