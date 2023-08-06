package apiTestScript;

import application.apiPageObjects.AudienceAPI;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.DesignAPIBean;
import dataProvidersPackage.APIDP;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;

/**
 * @author Prateek Sethi
 */
public class DesignAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"api" }, enabled = true, dataProvider = "createDesignAPI", dataProviderClass = APIDP.class)
	public void AudienceAPI_TC_Create(DesignAPIBean sc) {
		ExtentTest t3 = pNode.createNode("DesignAPI_TC_Create", sc.testDescription);
		try {
			AudienceAPI audienceAPI = AudienceAPI.init(t3);
			pNode.info("Going to send the API POST req for audience :  " + sc.audience_name);
			Response resp = audienceAPI.createAudienceInScenario(sc);
			audienceAPI.validateAudienceInScenario(resp, sc);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"api" }, enabled = true, dataProvider = "addChannelsToScenario", dataProviderClass = APIDP.class)
	public void AudienceAPI_TC_ChannelAssign(DesignAPIBean sc) {
		ExtentTest t3 = pNode.createNode("AudienceAPI_TC_ChannelAssign", sc.testDescription);
		try {
			AudienceAPI audienceAPI = AudienceAPI.init(t3);
			pNode.info("Going to send the API Post req for assigning channel in Scenario :  " + sc.scenarioName);
			Response resp = audienceAPI.assignChannelsToScenario(sc);
			audienceAPI.validatechannelAssignmentToScenario(resp, sc);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 3, groups = {
			"api" }, enabled = true, dataProvider = "getAudienceAPI", dataProviderClass = APIDP.class)
	public void AudienceAPI_TC_Get(DesignAPIBean sc) {
		ExtentTest t3 = pNode.createNode("AudienceAPI_TC_Get", sc.testDescription);
		try {
			AudienceAPI audienceAPI = AudienceAPI.init(t3);
			Response resp = audienceAPI.getAudienceForGivenScenario(sc);
			audienceAPI.validateAudienceResponse(resp, sc);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 4, groups = {
			"api" }, enabled = true, dataProvider = "updateAudienceAPI", dataProviderClass = APIDP.class)
	public void AudienceAPI_TC_Update(DesignAPIBean sc) {
		ExtentTest t3 = pNode.createNode("AudienceAPI_TC_Update", sc.testDescription);
		try {
			AudienceAPI audienceAPI = AudienceAPI.init(t3);
			Response resp = audienceAPI.updateAudience(sc);
			audienceAPI.validateUpdatedAudienceResponse(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 5, groups = "api", enabled = true, dataProvider = "deleteDesignAPI",
			dataProviderClass = APIDP.class)

	public void DeleteAudienceAPI_TC(DesignAPIBean sc) {
		ExtentTest t3 = pNode.createNode("DeleteAudienceAPI_TC", sc.testDescription);
		try {
			AudienceAPI audienceAPI = AudienceAPI.init(t3);
			pNode.info("Going to send the API Delete req for delating audience :  " + sc.scenarioName);
			Response resp = audienceAPI.DelateAudienceAPI(sc);
			audienceAPI.validateAudienceDeleteAudience(resp, sc);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 6, groups = {
			"api" }, enabled = true, dataProvider = "addContentKeyTopicsForScenario",
			dataProviderClass = APIDP.class)
	public void AudienceAPI_TC_ContentKeyTopicsPost(DesignAPIBean sc) {
		ExtentTest t3 = pNode.createNode("AudienceAPI_TC_ContentKeyTopicsPost", sc.testDescription);
		try {
			AudienceAPI audienceAPI = AudienceAPI.init(t3);
			pNode.info("Going to send the API Post req for assigning channel key topics in Scenario :  " + sc.scenarioName);
			Response resp = audienceAPI.assignChannelsKeyTopicsToScenario(sc);
			audienceAPI.validatechannelKeyTopicsAssignmentToScenario(resp, sc);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
