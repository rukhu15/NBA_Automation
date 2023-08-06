package testscripts;

import application.pageObjects.DeleteAutomationObjectsPage;
import com.aventstack.extentreports.ExtentTest;
import data.beans.DeleteObjectPageBean;
import dataProvidersPackage.UIDP;
import framework.utility.common.Assertion;
import org.testng.annotations.Test;
import scripts.TestInit;

public class DeleteAutomation_Objects extends TestInit {

    @Test(priority = 1, groups = {"SANITY"}, enabled = true, dataProvider = "deleteObject", dataProviderClass = UIDP.class)
    public void Delete_Automation_Objects(DeleteObjectPageBean deleteBean) {
        ExtentTest t1 = pNode.createNode("Delete_Automation_Objects", "Delete objects created through automation");
        try {

            DeleteAutomationObjectsPage deleteObject = DeleteAutomationObjectsPage.init(t1);

            //Delete scenario
            deleteObject.deleteScenario(deleteBean);

            //Delete filter
            deleteObject.deleteFilter(deleteBean);

            //Delete segment
            deleteObject.deleteSegment(deleteBean);

            //Delete kpi created through automation
            deleteObject.deleteKpi(deleteBean);

            //Delete tag
            deleteObject.deleteTag(deleteBean);


        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }
        finally {
            Assertion.finalizeSoftAsserts();
        }
    }


}
