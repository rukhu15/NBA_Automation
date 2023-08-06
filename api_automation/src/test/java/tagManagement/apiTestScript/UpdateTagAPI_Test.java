package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.UpdateTagName;

public class UpdateTagAPI_Test extends TestInit {
	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "updateTagName", dataProviderClass = APIDP.class)
	public void updateTagName(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("updateTagName", "PUT Request for Tag");
		try {

			UpdateTagName tag = UpdateTagName.init(t3);
			pNode.info("Going to edit Tag Name");

			Response resp = tag.updateTag(tagMgmt);
			tag.validateTagResponse(resp, tagMgmt);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}

}
