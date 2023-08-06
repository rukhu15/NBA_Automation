package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.DeleteTagAPI;

public class DeleteTagAPI_Test extends TestInit {
	@Test(priority = 1, groups = {
			"TagManagement_API","clean" }, enabled = true, dataProvider = "deleteTag", dataProviderClass = APIDP.class)
	public void deleteTag(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("deleteTag", "DELETE Request for Tag");
		try {

			DeleteTagAPI tag = DeleteTagAPI.init(t3);

			pNode.info("Going to send the API DELETE req for Tag Name");
			Response resp = tag.deleteTag(tagMgmt);
			tag.validateDeleteTagResponse(resp, tagMgmt);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}

}
