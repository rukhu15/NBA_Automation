package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.FilterValueAPI;
import io.restassured.response.Response;
import scripts.TestInit;

public class FilterValueAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "fetchDataInFilter", dataProviderClass = APIDP.class)
	public void createFilterValue(Customer360APIBean filter) {
		ExtentTest t3 = pNode.createNode("fetchDataInFilter", filter.testDescription);
		try {
			FilterValueAPI filterValueAPI = FilterValueAPI.init(t3);

			pNode.info("Going to send the GET API req for filter values");
			Response resp = filterValueAPI.getFilterValue();
			filterValueAPI.validateFilterResponse(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
