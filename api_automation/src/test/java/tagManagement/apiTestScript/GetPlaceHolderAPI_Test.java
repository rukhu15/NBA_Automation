package tagManagement.apiTestScript;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;
import tagManagement.data.beans.api.TagManagementBean;
import tagManagement.dataProvidersPackage.APIDP;
import tagManagement.pageObjects.GetPlaceHolderNameAPI;



        public class GetPlaceHolderAPI_Test extends TestInit {

        @Test(priority = 1, groups = {
                "TagManagement_API"}, enabled = true, dataProvider = "getPlaceHolderName", dataProviderClass = APIDP.class)

        public void getPlaceHolder(TagManagementBean tagMgmt) {
            ExtentTest t3 = pNode.createNode("getPlaceHolder", tagMgmt.testDescription);
            try {

                GetPlaceHolderNameAPI placeHolder = GetPlaceHolderNameAPI.init(t3);
                Response resp = placeHolder.getPlaceHolder();
                placeHolder.validatePlaceHolder(resp);

            } catch (Exception e) {
                markTestAsFailure(e, t3);

            }
        }

    }

