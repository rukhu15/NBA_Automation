package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.AddBulkTagAPI;

public class AddBulkTagAPI_Test extends TestInit {
	@Test(priority = 1, groups = {
			"TagManagement_API","api" }, enabled = true, dataProvider = "addBulkTag", dataProviderClass = APIDP.class)
	public void addBulkTag(TagManagementBean tag) {
		ExtentTest t3 = pNode.createNode("addBulkTag", "PUT API to add Bulk Tag");
		try {

			AddBulkTagAPI bulkTag = AddBulkTagAPI.init(t3);

			Response resp = bulkTag.addBulkTag(tag);
			bulkTag.validateBulkTagResponse(resp, tag);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}

}
