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
import tagManagement.apiPojo.apiReq.saveBulkTag.ContentDetails;
import tagManagement.apiPojo.apiReq.saveBulkTag.SaveBulkTagReq;
import tagManagement.apiPojo.apiReq.saveBulkTag.TagDetails;
import tagManagement.apiPojo.apiResp.saveBulkTag.SaveBulkTagResp;
import tagManagement.data.beans.api.TagManagementBean;

import java.util.*;


public class SaveBulkTagAPI extends BaseAPI {

    private static ExtentTest pNode;
    private static final Logger logToConsole = LoggerFactory.getLogger(SaveBulkTagAPI.class);

    public SaveBulkTagAPI(ExtentTest t3) {
    }

    public static SaveBulkTagAPI init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new SaveBulkTagAPI(pNode);
    }

    public Response saveBulkTag(TagManagementBean tag){
        SaveBulkTagReq saveTagObj = new SaveBulkTagReq();

        List<ContentDetails> contentDetailsList = new ArrayList<>();
        List<TagDetails> tagDetailsList = new ArrayList<>();
        List<String> arrContent = Arrays.asList((tag.content_name).split("\\|"));
        List<String> arrTag = Arrays.asList((tag.tag_name).split("\\|"));
        for (String contentName : arrContent) {
            ContentDetails contentDetails = new ContentDetails();
            int content_id = DatabaseUtil.getValueFromColumn("content_id", ConfigInput.dbContentSchemaName,
                    "cm_content", "content_name='" + contentName + "'");



            for (String tagName : arrTag) {
                TagDetails tagDetails = new TagDetails();
                int tag_seq_id = DatabaseUtil.getValueFromColumn("tag_seq_id", ConfigInput.dbContentSchemaName, "cm_tag",
                        "tag_name='" + tagName + "' and is_deleted = false");

                tagDetails.setTag_seq_id(tag_seq_id);
                tagDetails.setIs_active(tag.is_active);
                tagDetails.setTag_name(tag.tag_name);

                tagDetailsList.add(tagDetails);
            }
            contentDetails.setContent_id(content_id);
            contentDetails.setTag_details(tagDetailsList);
            contentDetailsList.add(contentDetails);
        }
        saveTagObj.setContent_details(contentDetailsList);

        int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int nameSpaceID = DatabaseUtil.getNamespaceID(ConfigInput.nameSpaceName);
        int userId = DatabaseUtil.getUserID(tag.userName);

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("wrkspcId", workSpaceID);
        queryMap.put("nmspcId", nameSpaceID);
        queryMap.put("userId", userId);

        RequestSpecification bulkTagReq = getRequestSpecification("content-backend/cm/bulkUpdateCntTag",
                queryMap, saveTagObj).log().all();

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
        SaveBulkTagResp saveBulkResp = resp.then().spec(genericResp).extract().as(SaveBulkTagResp.class);
        System.out.println("The api response is: " + resp.asString());

        if (Integer.parseInt(saveBulkResp.getResponse().getStatus()) != 201) {
            pNode.fail("Tag could not be created, error: " + saveBulkResp.getResponse().getStatus());
        } else {
            pNode.pass("Tag created successfully with status code 201");
        }
    }
}
