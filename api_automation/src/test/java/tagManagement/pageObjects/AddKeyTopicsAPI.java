package tagManagement.pageObjects;

import com.aventstack.extentreports.ExtentTest;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripts.BaseAPI;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.apiPojo.apiReq.addKeyTopics.TagDetails;
import tagManagement.apiPojo.apiReq.addKeyTopics.AddKeyTopicsReq;
import tagManagement.apiPojo.apiResp.addKeyTopics.AddKeyTopicsResp;

import java.util.*;

public class AddKeyTopicsAPI extends BaseAPI {

    private static ExtentTest pNode;
    private static final Logger logToConsole = LoggerFactory.getLogger(AddKeyTopicsAPI.class);

    public AddKeyTopicsAPI(ExtentTest t3) {
    }

    public static AddKeyTopicsAPI init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new AddKeyTopicsAPI(pNode);
    }

    public Response addKeyTopics(TagManagementBean tag) {

        AddKeyTopicsReq addTagObj = new AddKeyTopicsReq();
        List<TagDetails> tagDetailsList = new ArrayList<>();
        List<String> arrTag = Arrays.asList((tag.tag_name).split("\\|"));

        for (String tagName : arrTag) {
            TagDetails tagDetails = new TagDetails();
            int tag_seq_id = DatabaseUtil.getValueFromColumn("tag_seq_id", ConfigInput.dbContentSchemaName, "cm_tag",
                    "tag_name='" + tagName + "' and is_deleted = false");

            tagDetails.setTag_seq_id(tag_seq_id);
            tagDetails.setIs_active(Boolean.parseBoolean(tag.is_active));

            tagDetailsList.add(tagDetails);
        }

        int channel_id = DatabaseUtil.getValueFromColumn("channel_id", ConfigInput.dbContentSchemaName, "cm_content",
                "content_name='" + tag.content_name + "'");

        addTagObj.setChannel_id(channel_id);
        addTagObj.setTag_details(tagDetailsList);

         int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int content_id = DatabaseUtil.getValueFromColumn("content_id", ConfigInput.dbContentSchemaName, "cm_content",
                "content_name='" + tag.content_name + "'");
        int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
        int userId = DatabaseUtil.getUserID(tag.userName);
        int brand_id = DatabaseUtil.getValueFromColumn("brand_id", ConfigInput.dbAppSchemaName, "nba_brand",
                "brand_name='" + tag.brandName + "'");

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("contentId", content_id);
        queryMap.put("nmspcId", nameSpaceID);
        queryMap.put("userId", userId);
        queryMap.put("brandId", brand_id);
        queryMap.put("wrkspcId", workSpaceID);

        RequestSpecification addTagReq = getRequestSpecification("content-backend/cm/cntTag", queryMap, addTagObj);

        pNode.info("URI for the Tag api request is:" + addTagReq.log().body(true));
        logToConsole.info("URI for the Tag api request is: " + addTagReq.log().body(true));

        Response resp = addTagReq.when().put();
        pNode.info("Response for the api request is:" + resp.prettyPrint());
        logToConsole.info("Response for the api request is:" + resp.prettyPrint());
        return resp;

    }

    public void validateAddKeyTopicsResponse(Response resp) {
        pNode.info("Validate the Add keyTopic api response");
        logToConsole.info("Validate the Add keyTopic api response");
        ResponseSpecification genericResp = initAPIResp();
        AddKeyTopicsResp addKeyTopicsResp = resp.then().spec(genericResp).extract().as(AddKeyTopicsResp.class);
        System.out.println("The api response is: " + resp.asString());

        if (Integer.parseInt(addKeyTopicsResp.getResponse().getStatus()) != 201) {
            pNode.fail("Tag could not be added to Content api: " + addKeyTopicsResp.getResponse().getStatus());
        } else {
            pNode.pass("Tag could be  added to content with status code 201");
        }
    }

}
