package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.GetFilterAPI;

public class GetFilterAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "getFilters", dataProviderClass = APIDP.class)
	public void getFilters(TagManagementBean tagMgmt) {
		ExtentTest t3 = pNode.createNode("getFilters", tagMgmt.testDescription);
		try {

			GetFilterAPI filter = GetFilterAPI.init(t3);

			Response resp = filter.getFilter(tagMgmt);
			filter.validateFilterResponse(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}
}
