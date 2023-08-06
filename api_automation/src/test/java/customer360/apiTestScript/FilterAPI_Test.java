package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.FilterAPI;
import customer360.pageObjects.FilterAPI.filters;
import io.restassured.response.Response;
import scripts.TestInit;

public class FilterAPI_Test extends TestInit {
	private static boolean isFilterNameDeleted = false;

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "createFilterAPI", dataProviderClass = APIDP.class)
	public void createFilter(Customer360APIBean filter) {
		ExtentTest t3 = pNode.createNode("createFilter", filter.testDescription);
		try {
			FilterAPI filterAPI = FilterAPI.init(t3);

			if (!isFilterNameDeleted) {
				filterAPI.deleteExistingFilter(filter.filter_name);
				isFilterNameDeleted = true;
			}

			pNode.info("Going to send the API POST req for filter");
			Response resp = filterAPI.createFilter(filter);
			filterAPI.validateResponseInFilter(resp, filter);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "getSavedFilterAPI", dataProviderClass = APIDP.class)
	public void getFilter(Customer360APIBean filter) {
		ExtentTest t3 = pNode.createNode("getFilter", filter.testDescription);
		try {
			FilterAPI filterAPI = FilterAPI.init(t3);

			Response resp = filterAPI.getFilters(filter, filters.SAVEDALLFILTERS);
			filterAPI.getValidateResponseInFilter(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 3, groups = {
			"Customer360_API" }, enabled = false, dataProvider = "getSelectedFilterAPI", dataProviderClass = APIDP.class)
	public void getSelectedFilter(Customer360APIBean filter) {
		ExtentTest t3 = pNode.createNode("getSelectedFilter", filter.testDescription);
		try {
			FilterAPI filterAPI = FilterAPI.init(t3);

			Response resp = filterAPI.getFilters(filter, filters.SELECTEDFILTERS);
			filterAPI.getValidateResponseInFilter(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
