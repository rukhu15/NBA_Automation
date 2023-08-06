package application.apiPageObjects.publish;

import apiPojo.apiResp.GetarchiveConnection.GetarchiveConnections;
import apiPojo.apiResp.Getshowconnector.Resshowconnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import apiPojo.apiResp.GetConfiguredAPI.RespConfiguredConnection;
import com.aventstack.extentreports.ExtentTest;

import data.beans.api.PublishAPIBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

import static io.restassured.RestAssured.given;

public class GetConfiguredConnectionsAPI extends BaseAPI {

    private static ExtentTest pNode;
    private static final Logger logToConsole = LoggerFactory.getLogger(GetConfiguredConnectionsAPI.class);


    public GetConfiguredConnectionsAPI(ExtentTest pNode) {
        super();

    }

    public static GetConfiguredConnectionsAPI init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new GetConfiguredConnectionsAPI(pNode);
    }


    public Response GetConfiguredConnectionsRequest(PublishAPIBean vb) {
        int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        RequestSpecification genericReq = initAPIReq();

        RequestSpecification ConfiguredConnectionsreq = given().spec(genericReq).relaxedHTTPSValidation().basePath("nbadesigner-backend/nba/configuredConnections").queryParam("wrkspc_id", workSpaceID).log().all();

        pNode.info("########  The decorated api request is: " + ConfiguredConnectionsreq.log().body(true));
        Response resp = ConfiguredConnectionsreq.when().get();
        return resp;

    }


    public Response GetShowConnector(PublishAPIBean vb) {

        RequestSpecification genericReq = initAPIReq();

        RequestSpecification GetShowConnectorreq = given().spec(genericReq).relaxedHTTPSValidation().basePath("nbadesigner-backend/nba/showConnectorCards").log().all();

        pNode.info("########  The decorated api request is: " + GetShowConnectorreq.log().body(true));

        Response resp = GetShowConnectorreq.when().get();
        return resp;

    }


    public Response GetarchiveConnection(PublishAPIBean vb) {
        int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        RequestSpecification genericReq = initAPIReq();

        RequestSpecification GetarchiveConnectionreq = given().spec(genericReq).relaxedHTTPSValidation().basePath("nbadesigner-backend/nba/archiveConnection").log().all().queryParam("wrkspc_id", workSpaceID);

        pNode.info("########  The decorated api request is: " + GetarchiveConnectionreq.log().body(true));

        Response resp = GetarchiveConnectionreq.when().get();
        return resp;

    }

    public void validateConfiguredConnectionsResponse(Response resp) {

        pNode.info("Validate the Configured Connections details api response");

        ResponseSpecification genericResp = initAPIResp();
        RespConfiguredConnection respconfigcon = resp.then().log().all().spec(genericResp).extract().as(RespConfiguredConnection.class);

        pNode.info("########  The received api response is: " + resp.prettyPrint());

        String expectedResponseDetail = "Configured Connectors Data Fetched";
        String actualResponseDetail = respconfigcon.getResponse().getDetail();

        if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
            pNode.fail("Configured Connectors not fetched , {expected response detail is} : " + expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
        } else {
            pNode.pass("Record fetched for configured connectors " + " expected response detail is: " + expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
        }

        if (Integer.parseInt(respconfigcon.getResponse().getStatus()) != 200) {
            pNode.fail("Configured Connectors Data not fetched, error: " + respconfigcon.getResponse().getStatus());
        } else {
            pNode.pass("Configured Connectors Data fetched successfully with status code 200");
        }
    }


    public void validateShowconectorcards(Response resp) {

        pNode.info("Validate the show connector connections details api response");

        ResponseSpecification genericResp = initAPIResp();
        Resshowconnector reshowing = resp.then().log().all().spec(genericResp).extract().as(Resshowconnector.class);

        pNode.info("########  The received api response is: " + resp.prettyPrint());

        String expectedResponseDetail = "Connector Data Fetched";
        String actualResponseDetail = reshowing.getResponse().getDetail();

        if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
            pNode.fail("Show connector data not fetched , {expected response detail is} : " + expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
        } else {
            pNode.pass("Records fetched for show connector " + " expected response detail is: " + expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
        }

        if (Integer.parseInt(reshowing.getResponse().getStatus()) != 200) {
            pNode.fail("Show connector data not fetched, error: " + reshowing.getResponse().getStatus());
        } else {
            pNode.pass("Show connector data fetched successfully with status code 200");
        }


    }

    public void validatearchiveConnectionResponse(Response resp) {

        pNode.info("Validate the Configured Connections details api response");

        ResponseSpecification genericResp = initAPIResp();
        GetarchiveConnections resparchiveConnection = resp.then().log().all().spec(genericResp).extract().as(GetarchiveConnections.class);

        pNode.info("########  The received api response is: " + resp.prettyPrint());

        String expectedResponseDetail = "Archive Connection Fetched";
        String actualResponseDetail = resparchiveConnection.getResponse().getDetail();

        if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
            pNode.fail("Archive connections details not fetched , {expected response detail is} : " + expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
        } else {
            pNode.pass("Record fetched for Archive connections " + " expected response detail is: " + expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
        }

        if (Integer.parseInt(resparchiveConnection.getResponse().getStatus()) != 200) {
            pNode.fail("Archive connections data not fetched, error: " + resparchiveConnection.getResponse().getStatus());
        } else {
            pNode.pass("Archive Connection data fetched successfully with status code 200");
        }
    }

}
