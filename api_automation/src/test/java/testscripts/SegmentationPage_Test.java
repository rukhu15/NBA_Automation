package testscripts;

import application.pageObjects.Segmentation;
import data.beans.SegmentationPageBean;
import dataProvidersPackage.UIDP;
import org.json.JSONObject;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.Assertion;
import scripts.TestInit;

public class SegmentationPage_Test extends TestInit{

    @Test(priority = 1, groups = {"SANITY"}, enabled = true, dataProvider = "createSegment", dataProviderClass = UIDP.class)
    public void Segment_Creation(SegmentationPageBean segmentationBean) {
        ExtentTest t1 = pNode.createNode("Segment_Creation", "Create rule based segment");
        JSONObject segmentDetails = null;
        try {

            Segmentation segmentationObject = Segmentation.init(t1);

            //Get the details of segment to create
            segmentDetails = segmentationObject.getSegmentDetails(segmentationBean,"rule_based_segment");

            //Open segmentation check if segment is created delete that
            segmentationObject.openSegmentationTabBrowse(segmentDetails);

            //Create rule based segment
            segmentationObject.createSegment(segmentDetails);

            //Activate segment
            segmentationObject.activateSegment(segmentDetails);

            //Use as filter segment
            segmentationObject.useAsFilterSegment();

        } catch (Exception e) {
            markTestAsFailure(e, t1);
        }
        finally {
            Assertion.finalizeSoftAsserts();
        }
    }

}
