package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.InteractionSummaryAPI;
import customer360.pageObjects.InteractionSummaryAPI.TimeFrame;
import io.restassured.response.Response;
import scripts.TestInit;

public class InteractionSummaryAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionSummary", dataProviderClass = APIDP.class)
	public void currentWeekInteractionSummaryResponse(Customer360APIBean interaction) {
		ExtentTest t3 = pNode.createNode("interactionSummary", interaction.testDescription);
		try {

			InteractionSummaryAPI interactionSummaryResp = InteractionSummaryAPI.init(t3);
			Response resp = interactionSummaryResp.interactionSummaryResponse(TimeFrame.CURRENTWEEK, interaction);
			interactionSummaryResp.validateInteractionSummary(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionSummary", dataProviderClass = APIDP.class)
	public void last13WeekInteractionSummaryResponse(Customer360APIBean interaction) {
		ExtentTest t3 = pNode.createNode("interactionSummary", interaction.testDescription);
		try {

			InteractionSummaryAPI interactionSummaryResp = InteractionSummaryAPI.init(t3);
			Response resp = interactionSummaryResp.interactionSummaryResponse(TimeFrame.LAST13WEEKS, interaction);
			interactionSummaryResp.validateInteractionSummary(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionSummary", dataProviderClass = APIDP.class)
	public void last4WeekInteractionSummaryResponse(Customer360APIBean interaction) {
		ExtentTest t3 = pNode.createNode("interactionSummary", interaction.testDescription);
		try {

			InteractionSummaryAPI interactionSummaryResp = InteractionSummaryAPI.init(t3);
			Response resp = interactionSummaryResp.interactionSummaryResponse(TimeFrame.LAST4WEEKS, interaction);
			interactionSummaryResp.validateInteractionSummary(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
