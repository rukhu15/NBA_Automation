package tagManagement.apiTestScript;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.AddBulkTagAPI;
import tagManagement.pageObjects.SaveBulkTagAPI;

public class SaveBulkTagAPI_Test extends TestInit {
    @Test(priority = 1, groups = {
            "TagManagement_API","api"}, enabled = true, dataProvider = "saveBulkTag", dataProviderClass = APIDP.class)
    public void saveBulkTag(TagManagementBean tag) {
        ExtentTest t3 = pNode.createNode("saveBulkTag", "PUT API for save Bulk Tag");
        try {

            SaveBulkTagAPI saveTag = SaveBulkTagAPI.init(t3);
            Response resp = saveTag.saveBulkTag(tag);
            saveTag.validateBulkTagResponse(resp,tag);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }
}
