package tagManagement.pageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiReq.updateTag.BrandDetails;
import tagManagement.apiPojo.apiReq.updateTag.ChannelDetails;
import tagManagement.apiPojo.apiReq.updateTag.UpdateTagReq;
import tagManagement.apiPojo.apiResp.updateTag.UpdateTagResp;
import tagManagement.data.beans.api.TagManagementBean;

public class UpdateTagName extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(UpdateTagName.class);

	public UpdateTagName(ExtentTest t3) {
	}

	public static UpdateTagName init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new UpdateTagName(pNode);
	}

	public Response updateTag(TagManagementBean tagManag) {

		UpdateTagReq tag = new UpdateTagReq();

		JSONObject brandJson = new JSONObject(tagManag.brand_details);
		JSONArray brandArr = brandJson.getJSONArray("brand_details");
		List<BrandDetails> brandList = new ArrayList<>();

		for (int i = 0; i < brandArr.length(); i++) {
			BrandDetails brandDetail = new BrandDetails();
			JSONObject brandObj = brandArr.getJSONObject(i);

			int brand_id = brandObj.getInt("brand_id");
			brandDetail.setBrand_id(brand_id);

			Boolean is_active = brandObj.getBoolean("is_active");
			brandDetail.setIs_active(is_active);

			brandList.add(brandDetail);
		}

		List<ChannelDetails> channelList = new ArrayList<>();
		JSONObject channelJson = new JSONObject(tagManag.channel_details);
		JSONArray channelArr = channelJson.getJSONArray("channel_details");
		for (int j = 0; j < channelArr.length(); j++) {
			ChannelDetails channelDetail = new ChannelDetails();
			JSONObject channelObj = channelArr.getJSONObject(j);

			int channel_id = channelObj.getInt("channel_id");
			channelDetail.setChannel_id(channel_id);

			Boolean is_active = channelObj.getBoolean("is_active");
			channelDetail.setIs_active(is_active);

			channelList.add(channelDetail);
		}
		tag.setDesc_change(tagManag.desc_change);
		tag.setTag_name(tagManag.new_tag_name);
		tag.setTag_desc(tagManag.tag_desc);
		tag.setBrand_details(brandList);
		tag.setChannel_details(channelList);

		int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
		int wrkspcId = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int tagSeqId = DatabaseUtil.getTagSeqID(tagManag.tag_name);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("wrkspcId", wrkspcId);
		queryMap.put("nmspcId", nameSpaceID);
		queryMap.put("companyId", companyId);
		queryMap.put("tagSeqId", tagSeqId);

		RequestSpecification tagReq = getRequestSpecification("content-backend/cm/tag", queryMap, tag).log().all();

		pNode.info("URI for the Tag api request is:" + tagReq.log().body(true));
		logToConsole.info("URI for the Tag api request is: " + tagReq.log().body(true));

		Response resp = tagReq.when().put();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateTagResponse(Response resp, TagManagementBean tag) {
		pNode.info("Validate the Tag api response");
		logToConsole.info("Validate the Tag api response");
		ResponseSpecification genericResp = initAPIResp();
		UpdateTagResp tagResponse = resp.then().spec(genericResp).extract().as(UpdateTagResp.class);
		System.out.println("The api response is: " + resp.asString());

		if (Integer.parseInt(tagResponse.getResponse().getStatus()) != 201) {
			pNode.fail("Tag could not be created, error: " + tagResponse.getResponse().getStatus());
		} else {
			pNode.pass("Tag created successfully with status code 201");
		}
	}
}
