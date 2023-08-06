package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.GetTagNameAPI;
import tagManagement.pageObjects.GetTagNameAPI.tags;

public class GetTagNameAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "getSavedTagDetails", dataProviderClass = APIDP.class)
	public void getSavedTagDetails(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("getSavedTagDetails", tagMgmt.testDescription);
		try {

			GetTagNameAPI tag = GetTagNameAPI.init(t3);
			Response resp = tag.getTagDetails(tagMgmt, tags.SELECTEDTAGS);
			tag.validateTagDetails(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "getTagDetails", dataProviderClass = APIDP.class)
	public void getTagDetails(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("getTagDetails", tagMgmt.testDescription);
		try {

			GetTagNameAPI tag = GetTagNameAPI.init(t3);
			Response resp = tag.getTagDetails(tagMgmt, tags.SAVEDALLTAGS);
			tag.validateTagDetails(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
