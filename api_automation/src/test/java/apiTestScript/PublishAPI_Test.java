package apiTestScript;

import application.apiPageObjects.publish.GetConfiguredConnectionsAPI;
import application.apiPageObjects.publish.PostPublishReqs;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.PublishAPIBean;
import dataProvidersPackage.APIDP;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;

public class PublishAPI_Test extends TestInit {

    @Test(priority = 1, groups = {"api"}, enabled = true, dataProvider = "getshowconnector", dataProviderClass = APIDP.class)
    public void getshowconnector_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("getconfiguredconnections", vb.testDescription);
        try {

            GetConfiguredConnectionsAPI Fontconfig = GetConfiguredConnectionsAPI.init(t3);

            Response resp = Fontconfig.GetShowConnector(vb);

            Fontconfig.validateShowconectorcards(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }


    @Test(priority = 2, groups = {"api"}, enabled = false, dataProvider = "postveevaconnection", dataProviderClass = APIDP.class)
    public void postveevaconnection_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("postveevaconnection", vb.testDescription);
        try {


            PostPublishReqs postpublishreq = PostPublishReqs.init(t3);

            Response resp = postpublishreq.createveevareq(vb);

            postpublishreq.validateveeva(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }

    @Test(priority = 3, groups = {"api"}, enabled = false, dataProvider = "postsfmcconnection", dataProviderClass = APIDP.class)
    public void postsfmcconnection_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("postsfmcconnection", vb.testDescription);
        try {


            PostPublishReqs postpublishreq = PostPublishReqs.init(t3);

            Response resp = postpublishreq.createsfmcreq(vb);

            postpublishreq.validatsfmc(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }

    @Test(priority = 4, groups = {"api"}, enabled = true, dataProvider = "postveevatestconnection", dataProviderClass = APIDP.class)
    public void postveevatestconnection_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("postveevatestconnection", vb.testDescription);
        try {


            PostPublishReqs postpublishreq = PostPublishReqs.init(t3);

            Response resp = postpublishreq.Testconnectionresp(vb);

            postpublishreq.validatvevvatestconnection(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }


    }

    @Test(priority = 5, groups = {"api"}, enabled = true, dataProvider = "postsfmctestconnection", dataProviderClass = APIDP.class)
    public void postsfmctestconnection_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("postsfmctestconnection", vb.testDescription);
        try {


            PostPublishReqs postpublishreq = PostPublishReqs.init(t3);

            Response resp = postpublishreq.Testsfmcconnectionresp(vb);

            postpublishreq.validatsfmctestconnection(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }


    @Test(priority = 6, groups = {"api"}, enabled = true, dataProvider = "getconfiguredconnections", dataProviderClass = APIDP.class)
    public void getconfiguredconnections_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("getconfiguredconnections", vb.testDescription);
        try {

            GetConfiguredConnectionsAPI Gconfig = GetConfiguredConnectionsAPI.init(t3);

            Response resp = Gconfig.GetConfiguredConnectionsRequest(vb);

            Gconfig.validateConfiguredConnectionsResponse(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }

    @Test(priority = 7, groups = {"api"}, enabled = true, dataProvider = "getarchiveConnection", dataProviderClass = APIDP.class)
    public void getarchiveConnection_Tc(PublishAPIBean vb) {
        ExtentTest t3 = pNode.createNode("getarchiveConnection", vb.testDescription);
        try {

            GetConfiguredConnectionsAPI archive = GetConfiguredConnectionsAPI.init(t3);

            Response resp = archive.GetarchiveConnection(vb);

            archive.validatearchiveConnectionResponse(resp);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }

    }

}
