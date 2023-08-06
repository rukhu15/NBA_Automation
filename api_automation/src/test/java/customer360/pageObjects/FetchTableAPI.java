package customer360.pageObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import com.google.gson.Gson;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import customer360.apiPojo.apiReq.fetchRecordReq.FetchRecordRequest;
import customer360.apiPojo.apiReq.fetchRecordReq.Filter;
import customer360.apiPojo.apiReq.fetchRecordReq.Search;
import customer360.apiPojo.apiReq.fetchRecordReq.SortModel;
import customer360.apiPojo.apiReq.fetchRecordReq.SubCategory;
import customer360.apiPojo.apiResp.fetchRecordResponse.FetchRecordRespInDefault;
import customer360.apiPojo.apiResp.fetchRecordResponse.FetchRecordResponse;
import customer360.data.beans.api.Customer360APIBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import scripts.BaseAPI;

public class FetchTableAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(FetchTableAPI.class);

	public FetchTableAPI(ExtentTest t3) {
	}

	public static FetchTableAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new FetchTableAPI(pNode);
	}

	public enum fetchTableRecords {
		DEFAULTSTATE, FILTERSELECTED, FILTERANDCARDSELECTED, CARDSELECTED
	}

	public Response fetchTable(Customer360APIBean fetch, fetchTableRecords records) {

		FetchRecordRequest fetchRecordObj = new FetchRecordRequest();
		List<SortModel> sortModelList = new ArrayList<SortModel>();
//		List<String> arrSort = Arrays.asList((fetch.columnId));
		if (fetch.columnId != null && !fetch.columnId.isEmpty()) {

			SortModel sort = new SortModel();
			sort.setSort(fetch.sort);
			sort.setColId(fetch.columnId);
			sortModelList.add(sort);
		}

		Search search = new Search();

		if (records.equals(fetchTableRecords.DEFAULTSTATE)) {
			fetchRecordObj.setOffset(Integer.parseInt(fetch.offset));
			fetchRecordObj.setLimit(Integer.parseInt(fetch.limit));
			fetchRecordObj.setSort_model(sortModelList);
			fetchRecordObj.setSearch(search);

		} else if (records.equals(fetchTableRecords.CARDSELECTED)) {
			fetchRecordObj.setOffset(Integer.parseInt(fetch.offset));
			fetchRecordObj.setLimit(Integer.parseInt(fetch.limit));
			fetchRecordObj.setCard_selected(fetch.card_selected);
			fetchRecordObj.setSort_model(sortModelList);
			fetchRecordObj.setSearch(search);
		} else if (records.equals(fetchTableRecords.FILTERSELECTED)) {
			search.setName(fetch.customer_name);
			Filter filter = new Filter();

			JSONObject filterJson = new JSONObject(fetch.filters);
			JSONObject filterObj = filterJson.getJSONObject("filters");
			JSONArray subCategoryArr = filterObj.getJSONArray("sub_category");
			List<SubCategory> subCategoryList = new ArrayList<>();

			for (int i = 0; i < subCategoryArr.length(); i++) {
				SubCategory subCategory = new SubCategory();
				JSONObject subCategoryObj = subCategoryArr.getJSONObject(i);
				String title = subCategoryObj.getString("title");
				subCategory.setTitle(title);

				List<String> childrenList = new ArrayList<>();
				JSONArray childrenArr = subCategoryObj.getJSONArray("children");

				if (childrenArr != null) {
					for (int j = 0; j < childrenArr.length(); j++) {
						childrenList.add(childrenArr.getString(j));
					}
				}
				subCategory.setChildren(childrenList);
				subCategoryList.add(subCategory);
				
				String category = subCategoryObj.getString("category");
				subCategory.setCategory(category);
			}
			filter.setSub_category(subCategoryList);

			fetchRecordObj.setOffset(Integer.parseInt(fetch.offset));
			fetchRecordObj.setLimit(Integer.parseInt(fetch.limit));
			fetchRecordObj.setFilters(filter);
			fetchRecordObj.setSort_model(sortModelList);
			fetchRecordObj.setSearch(search);
		} else if (records.equals(fetchTableRecords.FILTERANDCARDSELECTED)) {

			Filter filter = new Filter();

			JSONObject filterJson = new JSONObject(fetch.filters);
			JSONObject filterObj = filterJson.getJSONObject("filters");
			JSONArray subCategoryArr = filterObj.getJSONArray("sub_category");
			List<SubCategory> subCategoryList = new ArrayList<>();

			for (int i = 0; i < subCategoryArr.length(); i++) {
				SubCategory subCategory = new SubCategory();
				JSONObject subCategoryObj = subCategoryArr.getJSONObject(i);
				String title = subCategoryObj.getString("title");
				subCategory.setTitle(title);

				List<String> childrenList = new ArrayList<>();
				JSONArray childrenArr = subCategoryObj.getJSONArray("children");

				if (childrenArr != null) {
					for (int j = 0; j < childrenArr.length(); j++) {
						childrenList.add(childrenArr.getString(j));
					}
				}
				subCategory.setChildren(childrenList);
				subCategoryList.add(subCategory);
				
				String category = subCategoryObj.getString("category");
				subCategory.setCategory(category);
			}
			filter.setSub_category(subCategoryList);

			fetchRecordObj.setOffset(Integer.parseInt(fetch.offset));
			fetchRecordObj.setLimit(Integer.parseInt(fetch.limit));
			fetchRecordObj.setCard_selected(fetch.card_selected);
			fetchRecordObj.setFilters(filter);
			fetchRecordObj.setSort_model(sortModelList);
			fetchRecordObj.setSearch(search);
		}

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);

		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("projectId", projectID);
		queryMap.put("brandName", ConfigInput.api_brandName);
		queryMap.put("companyId", companyId);

		RequestSpecification fetchTableReq = getRequestSpecification("/nbadesigner-backend/nba/agGrid", queryMap,
				fetchRecordObj).log().all();

		pNode.info("URI for the fetch table api request is:" + fetchTableReq.log().body(true));
		logToConsole.info("URI for the fetch table api request is: " + fetchTableReq.log().body(true));

		Response resp = fetchTableReq.when().post();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());

		return resp;
	}

	public void validateFetchedRecordsResponseInDefaultState(Response resp) {

		pNode.info("########  Validating the fetched record api response ###################");
		logToConsole.info("########  Validating the fetched record api response ###################");
		FetchRecordRespInDefault fetchRecordResp = new Gson().fromJson(resp.asString(), FetchRecordRespInDefault.class);

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = fetchRecordResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Records have been not Fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Records have been Fetched. " + " expected response detail is: " + expectedResponseDetail
					+ "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(fetchRecordResp.getResponse().getStatus()) != 200) {
			pNode.fail("Records have not been fetched, error: " + fetchRecordResp.getResponse().getStatus());
		} else {
			pNode.pass("Records have been fetched successfully with status code 200");
		}

	}

	public void validateFetchedRecordsResponse(Response resp) {

		pNode.info("########  Validating the fetched record api response ###################");
		logToConsole.info("########  Validating the fetched record api response ###################");
		FetchRecordResponse fetchRecordResp = new Gson().fromJson(resp.asString(), FetchRecordResponse.class);

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = fetchRecordResp.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Records have been not Fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Records have been Fetched. " + " expected response detail is: " + expectedResponseDetail
					+ "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(fetchRecordResp.getResponse().getStatus()) != 200) {
			pNode.fail("Records have not been fetched, error: " + fetchRecordResp.getResponse().getStatus());
		} else {
			pNode.pass("Records have been fetched successfully with status code 200");
		}

	}

}
