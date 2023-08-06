package tagManagement.pageObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiReq.addBulkTag.BulkTagReq;
import tagManagement.apiPojo.apiReq.addBulkTag.Details;
import tagManagement.apiPojo.apiResp.addBulkTag.AddBulkTagResp;
import tagManagement.data.beans.api.TagManagementBean;

public class AddBulkTagAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(AddBulkTagAPI.class);

	public AddBulkTagAPI(ExtentTest t3) {
	}

	public static AddBulkTagAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new AddBulkTagAPI(pNode);
	}

	public Response addBulkTag(TagManagementBean tag) {
		BulkTagReq bulkTagObj = new BulkTagReq();

		List<Details> contentDetailsList = new ArrayList<>();
		List<Details> tagDetailsList = new ArrayList<>();

		List<String> arrContent = Arrays.asList((tag.content_name).split("\\|"));
		if (arrContent.size() > 1) {
			for (String contentName : arrContent) {
				Details contentDetails = new Details();
				int content_id = DatabaseUtil.getValueFromColumn("content_id", ConfigInput.dbContentSchemaName,
						"cm_content", "content_name='" + contentName + "'");

				int channel_id = DatabaseUtil.getValueFromColumn("channel_id", ConfigInput.dbContentSchemaName,
						"cm_content", "content_name='" + contentName + "'");

				contentDetails.setChannel_id(channel_id);
				contentDetails.setContent_id(content_id);

				contentDetailsList.add(contentDetails);
			}
		}

		List<String> arrTag = Arrays.asList((tag.tag_name).split("\\|"));
		for (String tagName : arrTag) {
			Details tagDetails = new Details();
			int tag_seq_id = DatabaseUtil.getValueFromColumn("tag_seq_id", ConfigInput.dbContentSchemaName, "cm_tag",
					"tag_name='" + tagName + "' and is_deleted = false");

			tagDetails.setTag_seq_id(tag_seq_id);
			tagDetails.setIs_active(tag.is_active);

			tagDetailsList.add(tagDetails);
		}
		bulkTagObj.setContent_details(contentDetailsList);
		bulkTagObj.setTag_details(tagDetailsList);

		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
		int userId = DatabaseUtil.getUserID(tag.userName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("wrkspcId", workSpaceID);
		queryMap.put("nmspcId", nameSpaceID);
		queryMap.put("userId", userId);
		queryMap.put("companyId", companyId);

		RequestSpecification bulkTagReq = getRequestSpecification("content-backend/cm/bulkUpdateCntTagValidate",
				queryMap, bulkTagObj).log().all();

		pNode.info("URI for the Tag api request is:" + bulkTagReq.log().body(true));
		logToConsole.info("URI for the Tag api request is: " + bulkTagReq.log().body(true));

		Response resp = bulkTagReq.when().put();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateBulkTagResponse(Response resp, TagManagementBean tag) {
		pNode.info("Validate the Tag api response");
		logToConsole.info("Validate the Tag api response");
		ResponseSpecification genericResp = initAPIResp();
		AddBulkTagResp addBulkResp = resp.then().spec(genericResp).extract().as(AddBulkTagResp.class);
		System.out.println("The api response is: " + resp.asString());

		if (Integer.parseInt(addBulkResp.getResponse().getStatus()) != 201) {
			pNode.fail("Tag could not be created, error: " + addBulkResp.getResponse().getStatus());
		} else {
			pNode.pass("Tag created successfully with status code 201");
		}
	}

}
