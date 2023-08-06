package application.apiPageObjects.publish;

import apiPojo.apiReq.Publish.SFMCTest;
import apiPojo.apiReq.Publish.VeevaTest;
import apiPojo.apiReq.SFMC.SFMCPublish;
import apiPojo.apiReq.Publish.login_expr;
import apiPojo.apiReq.Publish.Veevapublish;
import apiPojo.apiResp.PublishResponse.TestConnectionResponse.TestConnectionResponse;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.PublishAPIBean;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.StatusCode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripts.BaseAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class PostPublishReqs extends BaseAPI {

    private static ExtentTest pNode;
    private static final Logger logToConsole = LoggerFactory.getLogger(PostPublishReqs.class);


    public PostPublishReqs(ExtentTest pNode) {
        super();

    }

    public static PostPublishReqs init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new PostPublishReqs(pNode);
    }


    public Veevapublish generateveevaObject(PublishAPIBean vb) {


        List<login_expr> listloginexpr = new ArrayList<>();
        Veevapublish veevreq = new Veevapublish();
        login_expr Login_expr = new login_expr();
        Login_expr.setUsername(ConfigInput.username);
        Login_expr.setEnv_url(ConfigInput.env_url);
        Login_expr.setPassword(ConfigInput.password);
        Login_expr.setSecurity_token(ConfigInput.security_token);
        listloginexpr.add(Login_expr);
        veevreq.setLogin_expr(listloginexpr);
        veevreq.setDescription(vb.description);
        veevreq.setConnection_name(vb.connection_name);
        veevreq.setConnector_type(vb.connector_type);
        veevreq.setIs_verified(vb.is_verified);
        veevreq.setWrkspc_id(vb.wrkspc_id);
        veevreq.setConnector(vb.connector);
        List<String> arrChannels = Arrays.asList((vb.channel).split("\\|"));
        veevreq.setChannel(arrChannels);
        veevreq.setLogo_path(vb.logo_path);
        return veevreq;


    }


    public SFMCPublish genrateSfmcobject(PublishAPIBean vb) {
        List<apiPojo.apiReq.SFMC.login_expr> listSFMCloginexpr = new ArrayList<>();

        SFMCPublish sfmcreq = new SFMCPublish();
        apiPojo.apiReq.SFMC.login_expr Loginexpsfmc = new apiPojo.apiReq.SFMC.login_expr();
        Loginexpsfmc.setClientid(ConfigInput.clientid);
        Loginexpsfmc.setClientsecret(ConfigInput.clientsecret);
        Loginexpsfmc.setUseOAuth2Authentication(ConfigInput.useOAuth2Authentication);
        Loginexpsfmc.setAccountMID(ConfigInput.accountMID);
        Loginexpsfmc.setWebserviceurl(ConfigInput.webserviceurl);
        Loginexpsfmc.setPassword(ConfigInput.sfmcpassword);
        Loginexpsfmc.setUsername(ConfigInput.username);
        Loginexpsfmc.setApi_scopes(ConfigInput.api_scopes);
        Loginexpsfmc.setApi_service(ConfigInput.api_service);
        Loginexpsfmc.setFt_server_host_address(ConfigInput.ft_server_host_address);
        listSFMCloginexpr.add(Loginexpsfmc);
        sfmcreq.setLogin_expr(listSFMCloginexpr);
        sfmcreq.setDescription(vb.description);
        sfmcreq.setConnection_name(vb.connection_name);
        sfmcreq.setConnector_type(vb.connector_type);
        sfmcreq.setIs_verified(vb.is_verified);
        sfmcreq.setWrkspc_id(vb.wrkspc_id);
        sfmcreq.setConnector(vb.connector);
        List<String> arrsfmcChannels = Arrays.asList((vb.channel).split("\\|"));
        sfmcreq.setChannel(arrsfmcChannels);
        sfmcreq.setLogo_path(vb.logo_path);
        return sfmcreq;

    }

    public VeevaTest Testveevaconnection() {


        VeevaTest veevaTest = new VeevaTest();
        veevaTest.setUsername(ConfigInput.username);
        veevaTest.setPassword(ConfigInput.password);
        veevaTest.setEnv_url(ConfigInput.env_url);
        veevaTest.setSecurity_token(ConfigInput.security_token);

        return veevaTest;


    }

    public SFMCTest SfcmTestconnection() {


        SFMCTest sfmcTest = new SFMCTest();
        sfmcTest.setClientid(ConfigInput.clientid);
        sfmcTest.setClientsecret(ConfigInput.clientsecret);
        sfmcTest.setAccountMID(ConfigInput.accountMID);
        sfmcTest.setUseOAuth2Authentication(ConfigInput.useOAuth2Authentication);

        return sfmcTest;


    }

    public Response Testconnectionresp(PublishAPIBean vb) {
        VeevaTest veevaTest = Testveevaconnection();
        RequestSpecification genericReq = initAPIReq();
        RequestSpecification veevaposttestconnection = given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/testVeevaConnection")
                .body(veevaTest);
        pNode.info("########  The decorated api request is: " + veevaposttestconnection.log().all());

        Response resp = veevaposttestconnection.when()
                .post();

        return resp;

    }

    public Response Testsfmcconnectionresp(PublishAPIBean vb) {
        SFMCTest sfmcTest = SfcmTestconnection();
        RequestSpecification genericReq = initAPIReq();
        RequestSpecification sfmcaposttestconnection = given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/testSFMCConnection")
                .body(sfmcTest);
        pNode.info("########  The decorated api request is: " + sfmcaposttestconnection.log().all());

        Response resp = sfmcaposttestconnection.when()
                .post();

        return resp;

    }


    public Response createveevareq(PublishAPIBean vb) {
        Veevapublish Veevapublishobj = generateveevaObject(vb);
        RequestSpecification genericReq = initAPIReq();

        RequestSpecification veevapostReqspec = given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/connectionDetails")
                .body(Veevapublishobj);
        pNode.info("########  The decorated api request is: " + veevapostReqspec.log().all());


        Response resp = veevapostReqspec.when()
                .post();
        return resp;
    }

    public void validateveeva(Response resp) {

        pNode.info("########  The received api response is: ##################### ");
        pNode.info("########  The received api response is: " + resp.prettyPrint());
        if (resp.getStatusCode() == StatusCode.CODE_200.getCode()) {
            pNode.pass("Veeva connection created successfully");
        } else {
            pNode.fail("Veeva connection could not be created successfully");
        }
    }


    public Response createsfmcreq(PublishAPIBean vb) {
        SFMCPublish sfmcreq = genrateSfmcobject(vb);
        RequestSpecification genericReq = initAPIReq();

        RequestSpecification sfmcpostReqspec = given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/connectionDetails")
                .body(sfmcreq);
        pNode.info("########  The decorated api request is: " + sfmcpostReqspec.log().all());


        Response resp = sfmcpostReqspec.when()
                .post();
        return resp;
    }

    public void validatsfmc(Response resp) {

        pNode.info("########  The received api response is: ##################### ");
        pNode.info("########  The received api response is: " + resp.prettyPrint());
        if (resp.getStatusCode() == StatusCode.CODE_200.getCode()) {
            pNode.pass("SFMC  connection created successfully");
        } else {
            pNode.fail("SFMC connection could not be created successfully");
        }
    }

    public void validatvevvatestconnection(Response resp) {

        pNode.info("########  The received api response is: ##################### ");
        pNode.info("########  The received api response is: " + resp.prettyPrint());

        ResponseSpecification genericResp = initAPIResp();

        TestConnectionResponse veevaTestResponse = resp.then().spec(genericResp).extract().as(TestConnectionResponse.class);

        boolean actualconn_status = veevaTestResponse.getData().isConn_status();
        boolean expectedconn_status = true;

        if (actualconn_status == expectedconn_status) {

            pNode.pass("Veeva connection credentials verified successfully");

        } else {
            pNode.fail("Veeva connection credentials are not correct, Please check");
        }


        if (resp.getStatusCode() == StatusCode.CODE_200.getCode()) {
            pNode.pass("Veeva connection utility ran successfully");
        } else {
            pNode.fail("Veeva connection utility not ran successfully");
        }
    }

    public void validatsfmctestconnection(Response resp) {

        pNode.info("########  The received api response is: ##################### ");
        pNode.info("########  The received api response is: " + resp.prettyPrint());

        ResponseSpecification genericResp = initAPIResp();

        TestConnectionResponse SfmcTestResponse = resp.then().spec(genericResp).extract().as(TestConnectionResponse.class);

        boolean actualconn_status = SfmcTestResponse.getData().isConn_status();
        boolean expectedconn_status = true;

        if (actualconn_status == expectedconn_status) {

            pNode.pass("SFMC connection credentials verified successfully");

        } else {
            pNode.fail("SFMC connection credentials are not correct, Please check");
        }


        if (resp.getStatusCode() == StatusCode.CODE_200.getCode()) {
            pNode.pass("SFMC connection utility ran successfully");
        } else {
            pNode.fail("SFMC connection utility not ran successfully");
        }
    }
}
