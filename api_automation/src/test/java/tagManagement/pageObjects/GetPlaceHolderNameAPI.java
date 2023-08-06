package tagManagement.pageObjects;
import apiPojo.apiPostResponseAdjudicate.PostDataResponse;
import com.aventstack.extentreports.ExtentTest;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.poi.xslf.usermodel.Placeholder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripts.BaseAPI;
import tagManagement.apiPojo.apiResp.getPlaceHolder.PlaceHolderResp;
import static io.restassured.RestAssured.*;
import static org.checkerframework.checker.units.UnitsTools.s;

public class GetPlaceHolderNameAPI extends BaseAPI {

    private static ExtentTest pNode;
    private static final Logger logToConsole = LoggerFactory.getLogger(GetPlaceHolderNameAPI.class);

    public GetPlaceHolderNameAPI(ExtentTest t3) {
    }

    public static GetPlaceHolderNameAPI init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new GetPlaceHolderNameAPI(pNode);
    }

    public Response getPlaceHolder() {

        int workspaceId = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);


        RequestSpecification genericReq = initAPIReq();
        RequestSpecification PlaceHolderReq = given().spec(genericReq).relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/model/suggestionPlaceholders").queryParam("workspaceId",workspaceId).log().all();

        Response resp = PlaceHolderReq.when().get();

        pNode.info("Response for the api request is:" + resp.prettyPrint());
        logToConsole.info("Response for the api request is:" + resp.prettyPrint());
        return resp;
    }

    public void validatePlaceHolder(Response resp) {

        pNode.info("Validate the placeholder api response");
        logToConsole.info("Validate the placeholder api response");
        ResponseSpecification genericResp = initAPIResp();
        PlaceHolderResp placeHolder = resp.then().spec(genericResp).extract().as(PlaceHolderResp.class);

        System.out.println("The api response is: " + resp.asString());

        String expectedResponseDetail = "Content Recommendation Key Topic channel permission checked"; //to verify
        String actualResponseDetail = placeHolder.getResponse().getDetail();

        if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
            pNode.fail("Record have not been fetched for placeholder response, {expected response detail is} : "
                    + expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
        } else {
            pNode.pass("Record fetched for placeholder response. " + " expected response detail is: "
                    + expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
        }

        if (Integer.parseInt( placeHolder.getResponse().getStatus()) != 200) {
            pNode.fail("Record is not fetched for placeholder response, error: " + placeHolder.getResponse().getStatus());
        } else {
            pNode.pass("Content Recommendation Key Topic channel permission checked with status code 200");
        }
    }
}




