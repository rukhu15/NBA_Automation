package tagManagement.pageObjects;

import static io.restassured.RestAssured.given;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import java.util.*;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiReq.createTag.BrandDetails;
import tagManagement.apiPojo.apiReq.createTag.CreateTagReq;
import tagManagement.apiPojo.apiResp.createTag.CreateTagResp;
import tagManagement.data.beans.api.TagManagementBean;

public class CreateTagAPI extends BaseAPI {

	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(CreateTagAPI.class);

	public CreateTagAPI(ExtentTest t3) {
	}

	public static CreateTagAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new CreateTagAPI(pNode);
	}

	public void deleteExistingTag(String tagName) throws SQLException {
		int tag_seq_id = 0;
		System.out.println("Going to delete existing tag");
		String query = "select tag_seq_id from content_management_qa.cm_tag where tag_name ='" + tagName + "';";
		ResultSet resultSet = DatabaseUtil.runSelectQuery(query);
		if (resultSet.next()) {
			tag_seq_id = resultSet.getInt(1);
		}

		String query1 = "delete from content_management_qa.cm_xref_tag_brand where tag_seq_id=' " + tag_seq_id + "';";
		DatabaseUtil.runUpdateQuery(query1);

		String query2 = "delete from content_management_qa.cm_xref_tag_channel where tag_seq_id ='" + tag_seq_id + "';";
		DatabaseUtil.runUpdateQuery(query2);

		String query3 = "delete from content_management_qa.cm_tag where tag_name =  '" + tagName + "';";
		DatabaseUtil.runUpdateQuery(query3);
		System.out.println(tagName + " tag is deleted");
	}

	public Response createTag(TagManagementBean tagManag) {

		CreateTagReq tag = new CreateTagReq();

		JSONObject brandJson = new JSONObject(tagManag.brand_details);
		JSONArray brandArr = brandJson.getJSONArray("brand_details");
		List<BrandDetails> brandList = new ArrayList<>();

		for (int i = 0; i < brandArr.length(); i++) {
			BrandDetails brandDetail = new BrandDetails();
			JSONObject brandObj = brandArr.getJSONObject(i);

			int brand_id = brandObj.getInt("brand_id");
			brandDetail.setBrand_id(brand_id);

			String brand_name = brandObj.getString("brand_name");
			brandDetail.setBrand_name(brand_name);

			brandList.add(brandDetail);
		}

		tag.setTag_name(tagManag.tag_name);
		tag.setTag_id(tagManag.tag_id);
		tag.setTag_desc(tagManag.tag_desc);
		tag.setIs_deleted(tagManag.is_deleted);
		tag.setPermission(tagManag.permission);
		tag.setBrand_details(brandList);

		int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");

		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("wrkspcId", workSpaceID);
		queryMap.put("nmspcId", nameSpaceID);
		queryMap.put("companyId", companyId);

		RequestSpecification tagReq = getRequestSpecification("content-backend/cm/tag", queryMap, tag).log().all();

		pNode.info("URI for the Tag api request is:" + tagReq.log().body(true));
		logToConsole.info("URI for the Tag api request is: " + tagReq.log().body(true));

		Response resp = tagReq.when().post();
		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateTagResponse(Response resp, TagManagementBean tag) {
		pNode.info("Validate the Tag api response");
		logToConsole.info("Validate the Tag api response");
		ResponseSpecification genericResp = initAPIResp();
		CreateTagResp tagResponse = resp.then().spec(genericResp).extract().as(CreateTagResp.class);
		System.out.println("The api response is: " + resp.asString());

		if (Integer.parseInt(tagResponse.getResponse().getStatus()) != 201) {
			pNode.fail("Tag could not be created, error: " + tagResponse.getResponse().getStatus());
		} else {
			pNode.pass("Tag created successfully with status code 201");
		}
	}

}
