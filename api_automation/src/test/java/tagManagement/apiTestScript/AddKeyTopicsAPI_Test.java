package tagManagement.apiTestScript;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.AddKeyTopicsAPI;

public class AddKeyTopicsAPI_Test extends TestInit {

        @Test(priority = 1, groups = {
                "TagManagement_API","api"}, enabled = true, dataProvider = "addKeyTopics", dataProviderClass = APIDP.class)
        public void AddKeyTopics(TagManagementBean tag) {
            ExtentTest t3 = pNode.createNode("addKeyTopics", "PUT API to add Key Topics in Content");
            try {

                AddKeyTopicsAPI addTag = AddKeyTopicsAPI.init(t3);

                Response resp = addTag.addKeyTopics(tag);
                addTag.validateAddKeyTopicsResponse(resp);

            } catch (Exception e) {
                markTestAsFailure(e, t3);
            }

        }

    }

