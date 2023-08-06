package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.PermissionAPI;

public class PermissionAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "getPermissionDetails", dataProviderClass = APIDP.class)
	public void getPermission(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("getPermissionDetails", tagMgmt.testDescription);
		try {

			PermissionAPI permission = PermissionAPI.init(t3);
			Response resp = permission.getPermission(tagMgmt);
			permission.validatePermission(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}
}