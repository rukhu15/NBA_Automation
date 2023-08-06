package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiReq.filterReq.Category;
import customer360.apiPojo.apiReq.filterReq.Child;
import customer360.apiPojo.apiReq.filterReq.Filter;
import customer360.apiPojo.apiReq.filterReq.FilterRequest;
import customer360.apiPojo.apiReq.filterReq.Filter_Value;
import customer360.apiPojo.apiReq.filterReq.SubCategory;
import customer360.apiPojo.apiResp.filterResp.FilterResponse;
import customer360.data.beans.api.Customer360APIBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class FilterAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(FilterAPI.class);

	public FilterAPI(ExtentTest t3) {
	}

	public static FilterAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new FilterAPI(pNode);
	}

	public enum filters {
		SAVEDALLFILTERS, SELECTEDFILTERS
	}

	public void deleteExistingFilter(String filterName) {
		System.out.println("Going to delete existing filters");
		String query = "delete from " + ConfigInput.dbSchemaName + ".filter_360 filter where filter.filter_name = '"
				+ filterName + "';";
		DatabaseUtil.runUpdateQuery(query);
		System.out.println(filterName + " filter is deleted");
	}

	public Response createFilter(Customer360APIBean filter360) {

		FilterRequest filterRequestObj = new FilterRequest();
		Filter_Value filterValueObj = new Filter_Value();
		Filter filterObj = new Filter();
		Category categoryObjSegment = new Category();
		Category categoryObjCustType = new Category();
		SubCategory segmentSubCategory = new SubCategory();
		SubCategory custTypeSubCategory = new SubCategory();

		JSONObject filter360Json = new JSONObject(filter360.filter_value);
		JSONObject filterValueJson = filter360Json.getJSONObject("filter_value");

		JSONObject filter = filterValueJson.getJSONObject("filter");
		JSONObject segmentObj = filter.getJSONObject("Segment");
		JSONObject custTypeObj = filter.getJSONObject("Customer Type");

		JSONObject childObj = segmentObj.getJSONObject("child");
		JSONObject childObje = custTypeObj.getJSONObject("child");

		JSONObject rxSegmentObj = childObj.getJSONObject("Rx Segment");
		JSONObject clientTypeObj = childObje.getJSONObject("Client Type");

		JSONArray segmentArr = rxSegmentObj.getJSONArray("child");
		JSONArray custTypeArr = clientTypeObj.getJSONArray("child");

		Child childSegment = new Child();
		Child childCustType = new Child();

		List<Child> segmentChildList = new ArrayList<>();
		List<Child> custTypeChildList = new ArrayList<>();

		for (int i = 0; i < segmentArr.length(); i++) {

			Child segmentChild = new Child();
			JSONObject segmentJson = segmentArr.getJSONObject(i);

			String segmentValue = segmentJson.getString("value");
			boolean segmentSelected = segmentJson.getBoolean("selected");

			segmentChild.setValue(segmentValue);
			segmentChild.setSelected(String.valueOf(segmentSelected));

			segmentChildList.add(segmentChild);
		}

		segmentSubCategory.setChild(segmentChildList);

		for (int j = 0; j < custTypeArr.length(); j++) {
			Child custTypeChild = new Child();
			JSONObject custTypeJson = custTypeArr.getJSONObject(j);

			String custTypeValue = custTypeJson.getString("value");
			boolean custTypeSelected = custTypeJson.getBoolean("selected");
			custTypeChild.setValue(custTypeValue);
			custTypeChild.setSelected(String.valueOf(custTypeSelected));

			custTypeChildList.add(custTypeChild);
		}
		custTypeSubCategory.setChild(custTypeChildList);

		childCustType.setClientType(custTypeSubCategory);
		childSegment.setRxSegment(segmentSubCategory);

		categoryObjSegment.setChild(childSegment);
		categoryObjCustType.setChild(childCustType);

		filterObj.setSegment(categoryObjSegment);
		filterObj.setCustomerType(categoryObjCustType);

		filterValueObj.setFilter(filterObj);

		filterRequestObj.setFilter_name(filter360.filter_name);
		filterRequestObj.setFilter_value(filterValueObj);
		filterRequestObj.setIs_enabled(filter360.is_enabled);

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);

		RequestSpecification genericReq = initAPIReq();

		RequestSpecification filterReq = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("nbadesigner-backend/nba/filter").queryParam("workspace_id", workSpaceID)
				.body(filterRequestObj).log().all();

		pNode.info("URI for the filter api request is:" + filterReq.log().body(true));
		logToConsole.info("URI for the filter api request is: " + filterReq.log().body(true));

		Response resp = filterReq.when().post();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateResponseInFilter(Response resp, Customer360APIBean filter360) {
		pNode.info("Validate the Filter api response");
		logToConsole.info("Validate the Filter api response");
		ResponseSpecification genericResp = initAPIResp();
		FilterResponse filterResponse = resp.then().spec(genericResp).extract().as(FilterResponse.class);
		System.out.println("The api response is: " + resp.asString());

		if (Integer.parseInt(filterResponse.getResponse().getStatus()) != 201) {
			pNode.fail("Filter could not be created, error: " + filterResponse.getResponse().getStatus());
		} else {
			pNode.pass("Filter created successfully with status code 201");
		}
	}

	public Response getFilters(Customer360APIBean filter360, filters filter) {

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		String filterName = filter360.filter_name;

		RequestSpecification genericReq = initAPIReq();
		RequestSpecification getFilterReq = null;
		if (filter.equals(filters.SAVEDALLFILTERS)) {
			getFilterReq = given().spec(genericReq).relaxedHTTPSValidation().queryParam("workspace_id", workSpaceID)
					.basePath("/nbadesigner-backend/nba/filter").log().all();
		} else if (filter.equals(filters.SELECTEDFILTERS)) {
			getFilterReq = given().spec(genericReq).relaxedHTTPSValidation().queryParam("workspace_id", workSpaceID)
					.queryParam("filter_name", filterName).basePath("/nbadesigner-backend/nba/filter").log().all();
		}

		Response resp = getFilterReq.when().get();
		return resp;
	}

	public void getResponse(Response resp) {

		String data = resp.getBody().asString();
		System.out.println("The response is" + data);

		JSONObject respObj = new JSONObject(data);
		Object responseObj = respObj.get("response");

		JSONObject objResp = (JSONObject) responseObj;

		String expectedResponseDetail = "Filter Name Fetched";
		String actualResponseDetail = objResp.getString("detail");

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Filter Name not Fetched, {expected response detail is} : " + expectedResponseDetail
					+ "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Filter Name Fetched. " + " expected response detail is: " + expectedResponseDetail
					+ "and  actual response detail is: " + actualResponseDetail);
		}

		if ((objResp.getInt("status")) != 200) {
			pNode.fail("Did not Fetch Details, as per Filter Criteria, error: " + objResp.getInt("status"));
		} else {
			pNode.pass("Fetched Details, as per Filter Criteria successfully with status code 200");
		}
	}

	public void getValidateResponseInFilter(Response resp) {
		pNode.info("Validate the Filter api response");
		logToConsole.info("Validate the Filter api response");
		getResponse(resp);

	}

}
