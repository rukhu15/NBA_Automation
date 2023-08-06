package tagManagement.pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiReq.searchAPI.Details;
import tagManagement.apiPojo.apiReq.searchAPI.Filters;
import tagManagement.apiPojo.apiReq.searchAPI.SearchContentReq;
import tagManagement.data.beans.api.TagManagementBean;

public class SearchContentAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(SearchContentAPI.class);

	public SearchContentAPI(ExtentTest t3) {
	}

	public static SearchContentAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new SearchContentAPI(pNode);
	}

	public void getColumnData(Response resp, TagManagementBean content) {
		String data = resp.getBody().asString();

		System.out.println("The response is" + data);

		JSONObject respObj = new JSONObject(data);
		Object dataObj = respObj.get("data");
		Object responseObj = respObj.get("response");
		JSONObject objResp = (JSONObject) responseObj;
		if (dataObj instanceof JSONArray) {

			String expectedResponseDetail = "No Data Present for Selected";
			String actualResponseDetail = objResp.getString("detail");

			if (!(actualResponseDetail).startsWith(expectedResponseDetail)) {
				pNode.fail("Record have not been fetched for filter response, {expected response detail is} : "
						+ expectedResponseDetail + " and {actual response detail is} : " + actualResponseDetail);
			} else {
				pNode.pass("Record fetched for filter response. " + " expected response detail is: "
						+ expectedResponseDetail + " and  actual response detail is: " + actualResponseDetail);
			}

			if ((objResp.getInt("status")) != 200) {
				pNode.fail("Did not Fetch Details, as per Filter Criteria, error: " + objResp.getInt("status"));
			} else {
				pNode.pass("Fetched Details, as per Filter Criteria successfully with status code 200");
			}

		} else if (dataObj instanceof JSONObject) {

			Set<String> expectedSet = new HashSet<>();
			Set<String> keys = new HashSet<>();
			keys.add(content.addField);
			boolean showBool = true;
			Set<String> removedCol = new HashSet<>((Arrays.asList((content.removedField).split("\\|"))));
			System.out.println("Removed column are" + removedCol);

			JSONObject objData = (JSONObject) dataObj;
			JSONArray column_dataArr = objData.getJSONArray("column_data");
			JSONArray rowDataArr = objData.getJSONArray("rowData");
			for (int rowIndex = 0; rowIndex <= rowDataArr.length() - 1; rowIndex++) {
				for (int index = 0; index <= column_dataArr.length() - 1; index++) {
					JSONObject colObj = column_dataArr.getJSONObject(index);
					JSONObject rowObj = rowDataArr.getJSONObject(rowIndex);
					keys.addAll(rowObj.keySet());
					keys.removeAll(removedCol);
					String fildStr = colObj.getString("field");
					expectedSet.add(fildStr);
					showBool = colObj.getBoolean("show");
				}

			}

			if (keys.containsAll(expectedSet)) {
				pNode.pass("column_data field matched");
			} else {
				pNode.fail("column_data field did not match, {expected column_data is} : " + expectedSet
						+ "and {actual column_data field is} : " + keys);
			}
			if (showBool) {
				pNode.pass("Show in column_data is true");
			} else {
				pNode.fail("Show in column_data is false");
			}

			String expectedResponseDetail = "Fetched Details, as per Filter Criteria";
			String actualResponseDetail = objResp.getString("detail");

			if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
				pNode.fail("Record have not been fetched for filter response, {expected response detail is} : "
						+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
			} else {
				pNode.pass("Record fetched for filter response. " + " expected response detail is: "
						+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
			}

			if ((objResp.getInt("status")) != 200) {
				pNode.fail("Did not Fetch Details, as per Filter Criteria, error: " + objResp.getInt("status"));
			} else {
				pNode.pass("Fetched Details, as per Filter Criteria successfully with status code 200");
			}
		}

	}

	public Response searchContent(TagManagementBean content) {

		SearchContentReq searchObj = new SearchContentReq();
		Filters filter = new Filters();

		List<Details> channelList = new ArrayList<Details>();
		List<Details> keyTopicsList = new ArrayList<Details>();
		List<Details> importDateList = new ArrayList<Details>();
		List<Details> contentList = new ArrayList<Details>();

		Details importDateDetails = new Details();

		List<String> arrContent = Arrays.asList((content.content_type_name).split("\\|"));
		if (arrContent.size() > 1) {
			for (String contentName : arrContent) {
				Details contentDetails = new Details();
				int content_type_id = DatabaseUtil.getValueFromColumn("cnt_type_id", ConfigInput.dbContentSchemaName,
						"cm_cnt_type", "cnt_type_name='" + contentName + "'");

				contentDetails.setContent_type_id(content_type_id);
				contentDetails.setContent_type_name(contentName);

				contentList.add(contentDetails);
			}
		}

		List<String> arrChannel = Arrays.asList((content.channel_name).split("\\|"));

		if (arrChannel.size() > 1) {
			for (String channelName : arrChannel) {
				Details channelDetails = new Details();
				int channel_id = DatabaseUtil.getChannelID(channelName);
				channelDetails.setChannel_id(channel_id);
				channelDetails.setChannel_name(channelName);

				channelList.add(channelDetails);
			}
		}

		List<String> arrKeyTopics = Arrays.asList((content.tag_name).split("\\|"));
		if (arrKeyTopics.size() > 1) {
			for (String tagName : arrKeyTopics) {
				Details KeyTopicsDetails = new Details();
				int tag_seq_id = DatabaseUtil.getTagSeqID(tagName);
				KeyTopicsDetails.setTag_name(tagName);
				KeyTopicsDetails.setTag_seq_id(tag_seq_id);

				keyTopicsList.add(KeyTopicsDetails);
			}
		}
		if (content.start_date != null && !content.start_date.isEmpty()) {
			importDateDetails.setStart_date(content.start_date);
			importDateDetails.setEnd_date(content.end_date);
			importDateList.add(importDateDetails);
		}
		filter.setContent_type_details(contentList);
		filter.setChannel_details(channelList);
		filter.setKey_topics_details(keyTopicsList);
		filter.setImport_date_details(importDateList);

		searchObj.setFilters(filter);
		searchObj.setSearch_string(content.search_string);

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
		int userId = DatabaseUtil.getUserID(content.userName);
		int brand_id = DatabaseUtil.getValueFromColumn("brand_id", ConfigInput.dbAppSchemaName, "nba_brand",
				"brand_name='" + content.brandName + "'");

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("wrkspcId", workSpaceID);
		queryMap.put("nmspcId", nameSpaceID);
		queryMap.put("userId", userId);
		queryMap.put("brandId", brand_id);
		queryMap.put("brandName", content.brandName);

		RequestSpecification contentFilterReq = getRequestSpecification("content-backend/cm/searchFilter", queryMap,
				searchObj).log().all();

		pNode.info("URI for the Tag api request is:" + contentFilterReq.log().body(true));
		logToConsole.info("URI for the Tag api request is: " + contentFilterReq.log().body(true));

		Response resp = contentFilterReq.when().put();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateFilterResponse(Response resp, TagManagementBean content) {
		pNode.info("Validate the content filter api response");
		logToConsole.info("Validate the content filter api response");

		getColumnData(resp, content);
	}
}
