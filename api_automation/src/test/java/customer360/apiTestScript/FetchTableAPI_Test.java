package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.FetchTableAPI;
import customer360.pageObjects.FetchTableAPI.fetchTableRecords;
import io.restassured.response.Response;
import scripts.TestInit;

public class FetchTableAPI_Test extends TestInit {
	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "fetchTableRecordsInDefaultState", dataProviderClass = APIDP.class)
	public void fetchRecordsInDefaultState(Customer360APIBean fetch) {
		ExtentTest t3 = pNode.createNode("fetchTableRecordsByDefault", fetch.testDescription);
		try {
			FetchTableAPI fetchAPI = FetchTableAPI.init(t3);

			pNode.info("Going to send the API POST req for filter");
			Response resp = fetchAPI.fetchTable(fetch, fetchTableRecords.DEFAULTSTATE);
			fetchAPI.validateFetchedRecordsResponseInDefaultState(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "fetchRecordsWithFilters", dataProviderClass = APIDP.class)
	public void fetchRecordsWithFilters(Customer360APIBean fetch) {
		ExtentTest t3 = pNode.createNode("fetchRecordsWithFilters", fetch.testDescription);
		try {
			FetchTableAPI fetchAPI = FetchTableAPI.init(t3);

			pNode.info("Going to send the API POST req for filter");
			Response resp = fetchAPI.fetchTable(fetch, fetchTableRecords.FILTERSELECTED);
			fetchAPI.validateFetchedRecordsResponse(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 3, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "fetchRecordsWithFiltersAndCards", dataProviderClass = APIDP.class)
	public void fetchRecordsWithFiltersAndCards(Customer360APIBean fetch) {
		ExtentTest t3 = pNode.createNode("fetchRecordsWithFiltersAndCards", fetch.testDescription);
		try {
			FetchTableAPI fetchAPI = FetchTableAPI.init(t3);

			pNode.info("Going to send the API POST req for filter");
			Response resp = fetchAPI.fetchTable(fetch, fetchTableRecords.FILTERANDCARDSELECTED);
			fetchAPI.validateFetchedRecordsResponse(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 4, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "fetchRecordsWithCards", dataProviderClass = APIDP.class)
	public void fetchRecordsWithCards(Customer360APIBean fetch) {
		ExtentTest t3 = pNode.createNode("fetchRecordsWithCards", fetch.testDescription);
		try {
			FetchTableAPI fetchAPI = FetchTableAPI.init(t3);

			pNode.info("Going to send the API POST req for filter");
			Response resp = fetchAPI.fetchTable(fetch, fetchTableRecords.CARDSELECTED);
			fetchAPI.validateFetchedRecordsResponse(resp);
		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

}
