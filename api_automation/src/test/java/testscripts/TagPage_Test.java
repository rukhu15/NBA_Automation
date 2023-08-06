package testscripts;

import application.businessrules.TopMenu;
import com.aventstack.extentreports.ExtentTest;
import application.pageObjects.TagPage;
import data.beans.TagPageBean;
import dataProvidersPackage.UIDP;
import framework.utility.common.Assertion;
import framework.utility.globalConst.ConfigInput;
import org.testng.annotations.Test;
import scripts.TestInit;

public class TagPage_Test extends TestInit {

    @Test(priority = 1, groups = {"SANITY"}, enabled = true, dataProvider = "createTag",dataProviderClass = UIDP.class)
    public void TagPage_TC_Create(TagPageBean tg) {
        ExtentTest t1 = pNode.createNode("TagPage_TC_Create","Testing");
        try {
            //following tag creation
            TagPage testPage = TagPage.init(t1);
            testPage.sideClickTags();
            testPage.sideMenuClose();
            testPage.createNewTag(tg);
            testPage.waitTillDisappearingOfSpinner();
        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }
        Assertion.finalizeSoftAsserts();
    }
}
