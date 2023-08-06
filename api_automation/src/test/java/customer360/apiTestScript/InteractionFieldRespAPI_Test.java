package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.InteractionFieldsResponseAPI;
import customer360.pageObjects.InteractionFieldsResponseAPI.fieldResponseGraph;
import io.restassured.response.Response;
import scripts.TestInit;

public class InteractionFieldRespAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionField", dataProviderClass = APIDP.class)
	public void last3MonthResponse(Customer360APIBean interaction) {
		ExtentTest t3 = pNode.createNode("interactionField", interaction.testDescription);
		try {

			InteractionFieldsResponseAPI fieldResp = InteractionFieldsResponseAPI.init(t3);
			Response resp = fieldResp.interactionFieldResponseGraph(fieldResponseGraph.LAST3MONTH, interaction);
			fieldResp.validateInteractionFieldResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "interactionField", dataProviderClass = APIDP.class)
	public void prior3MonthResponse(Customer360APIBean interaction) {
		ExtentTest t3 = pNode.createNode("interactionField", interaction.testDescription);
		try {

			InteractionFieldsResponseAPI fieldResp = InteractionFieldsResponseAPI.init(t3);
			Response resp = fieldResp.interactionFieldResponseGraph(fieldResponseGraph.PRIOR3MONTH, interaction);
			fieldResp.validateInteractionFieldResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
