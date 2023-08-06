package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.CreateTagAPI;

public class CreateTagAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"TagManagement_API","api" }, enabled = true, dataProvider = "createTag", dataProviderClass = APIDP.class)
	public void createTag(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("createTag", "POST Request for Tag");
		try {

			CreateTagAPI tag = CreateTagAPI.init(t3);

			pNode.info("Going to send the API POST req for Tag Name");

			Response resp = tag.createTag(tagMgmt);
			tag.validateTagResponse(resp, tagMgmt);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}

}
