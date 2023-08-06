package tagManagement.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import io.restassured.response.Response;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.SearchContentAPI;

public class SearchAPI_Test extends TestInit {
	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "searchContent", dataProviderClass = APIDP.class)
	public void searchContent(TagManagementBean content) {
		ExtentTest t3 = pNode.createNode("searchContent", "PUT API for search functionality");
		try {

			SearchContentAPI search = SearchContentAPI.init(t3);
			pNode.info("Going to edit Tag Name");

			Response resp = search.searchContent(content);
			search.validateFilterResponse(resp, content);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}

	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "applyFilter", dataProviderClass = APIDP.class)
	public void applyFilter(TagManagementBean content) {
		ExtentTest t3 = pNode.createNode("applyFilter", "PUT API for apply selected filters");
		try {

			SearchContentAPI search = SearchContentAPI.init(t3);
			pNode.info("Going to edit Tag Name");

			Response resp = search.searchContent(content);
			search.validateFilterResponse(resp, content);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}

	@Test(priority = 1, groups = {
			"TagManagement_API" }, enabled = true, dataProvider = "search_ApplyFilter", dataProviderClass = APIDP.class)
	public void search_ApplyFilter(TagManagementBean content) {
		ExtentTest t3 = pNode.createNode("search_ApplyFilter", "PUT API for search and apply selected filters");
		try {

			SearchContentAPI search = SearchContentAPI.init(t3);
			pNode.info("Going to edit Tag Name");

			Response resp = search.searchContent(content);
			search.validateFilterResponse(resp, content);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}
}
