package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.GetNameSpaceNameAPI;

public class GetNameSpaceAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "getNameSpaceName", dataProviderClass = APIDP.class)
	public void getNameSpace(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("getNameSpaceName", tagMgmt.testDescription);
		try {

			GetNameSpaceNameAPI nameSpace = GetNameSpaceNameAPI.init(t3);
			Response resp = nameSpace.getNameSpace();
			nameSpace.validateNameSpace(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}
}