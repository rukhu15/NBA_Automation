package application.apiPageObjects;

import apiPojo.apiReq.scenario.ScenarioReq;
import apiPojo.apiReq.scenario.ScenarioUpdateReq;
import apiPojo.apiResp.scenario.ScenarioResp;
import apiTestScript.ScenarioAPI_Test;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.ScenarioAPIBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripts.BaseAPI;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;


/**
 * @author Prateek Sethi
 */
public class ScenarioAPI extends BaseAPI {
    private static ExtentTest pNode;
    private static final Logger logToConsole = LoggerFactory.getLogger(ScenarioAPI_Test.class);

    public ScenarioAPI(ExtentTest pNode) {
    }

    public static ScenarioAPI init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new ScenarioAPI(pNode);
    }

        public Response createScenario(ScenarioAPIBean sc){
            List<Integer> ownerList= new ArrayList<Integer>();
            int userId=DatabaseUtil.getUserID(ConfigInput.apiUserName);
            int brandId=DatabaseUtil.getValueFromColumn("brand_id",ConfigInput.dbAppSchemaName,"nba_brand","brand_name='"+sc.brand_name+"'");
            ownerList.add(userId);

            List<String> scenarioMemberList= new ArrayList<String>();

            List<String> tagsList= new ArrayList<String>();
            tagsList.add(sc.tags);

        ScenarioReq scObj = ScenarioReq.builder()
                        .scenario_name(sc.scenarioName)
                                .scenario_desc(sc.scenarioDescription)
                .scenario_owner(ownerList)
                        .scenario_member(scenarioMemberList)
                                .scenario_member(scenarioMemberList)
           
                                .tags(tagsList)
                                                .created_by(userId)
                                                        .is_active(Boolean.parseBoolean(sc.isActive))
                                                                .is_deleted(Boolean.parseBoolean(sc.isActive))
                                                                        .brand_id(brandId)
                .build();
            
           

        RequestSpecification genericReq = initAPIReq();
        int workSpaceID= DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        RequestSpecification scenarioReq=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .queryParam("workspaceId",workSpaceID)
                .basePath("/nbadesigner-backend/nba/scenario")
                .body(scObj);

            QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioReq);
            System.out.println( "Request is:"+queryRequest.getURI());
            System.out.println( "Request is:"+queryRequest.getBody());
            
		Response resp = scenarioReq.when().post();
            
		// calling API related to Scenario Simulation setup
		int scenarioID = DatabaseUtil.getScenarioID(sc.scenarioName);
		given().spec(genericReq).relaxedHTTPSValidation().basePath("/nbadesigner-backend/nba/scenarioSimulation")
				.body("{ \"scenario_id\": " + scenarioID + "}").when().post();

        return resp;
    }

    public void validateScenarioCreation(Response resp,ScenarioAPIBean sc){
        pNode.info("########  Validating the api response ###################");
        logToConsole.info("########  Validating the api response ###################");
        ResponseSpecification genericResp = initAPIResp();
        ScenarioResp respScenario=resp.then().
                spec(genericResp)
                .extract()
                .as(ScenarioResp.class);

        if(respScenario.getScenario_name().equalsIgnoreCase(sc.scenarioName)){
            pNode.pass("Created Scenario matches with expected");
        }else{
            pNode.fail("Created Scenario name not matches with expected");
        }

        if(respScenario.getScenario_desc().equalsIgnoreCase(sc.scenarioDescription)){
            pNode.pass("The Scenario Desc from response matches with expected");
        }else{
            pNode.fail("The Scenario Desc from response does not matches with expected");
        }

        if(respScenario.isIs_active()==Boolean.parseBoolean( sc.isActive)){
            pNode.pass("The Scenario status from response matches with expected");
        }else{
            pNode.fail("The Scenario status from response does not matches with expected");
        }
    }

    public Response updateScenario(ScenarioAPIBean sc) {
        int workSpaceID= DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int userID=DatabaseUtil.getUserID("nba_automation");
        int scenarioID=DatabaseUtil.getScenarioID(sc.scenarioName);

        ScenarioUpdateReq sceUpdateReqObj = new ScenarioUpdateReq();
        sceUpdateReqObj.setScenario_name(sc.scenarioName);
        sceUpdateReqObj.setScenario_desc(sc.scenarioDescription);

        List<Integer> ownerList= new ArrayList<Integer>();
        ownerList.add(Integer.parseInt(sc.scenarioOwner));
        sceUpdateReqObj.setScenario_owner(ownerList);
        sceUpdateReqObj.setIs_active(Boolean.parseBoolean(sc.isActive));
        sceUpdateReqObj.setIs_deleted(Boolean.parseBoolean(sc.isActive));
        sceUpdateReqObj.setUser_id(userID);
        sceUpdateReqObj.setWrkspc_id(workSpaceID);

        RequestSpecification genericReq = initAPIReq();
        RequestSpecification scenarioReq=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .queryParam("workspaceId",workSpaceID)
                .basePath("/nbadesigner-backend/nba/scenario/"+scenarioID)
                .body(sceUpdateReqObj);

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioReq);
        System.out.println( "Request is:"+queryRequest.getURI());

        Response resp=scenarioReq.when()
                .put();
        return resp;

    }

    public void validateScenarioInResponse(Response resp, ScenarioAPIBean sc) {
        ResponseSpecification genericResp = initAPIResp();
        ScenarioResp respScenario=resp.then().
                spec(genericResp)
                .extract()
                .as(ScenarioResp.class);
        System.out.println("The api response is: "+resp.asString());
        if(sc.scenarioName.equalsIgnoreCase(respScenario.getScenario_name())){
            pNode.pass("Updated Scenario name matches with expected");
        }else{
            pNode.fail("Updated Scenario name not matches with . Actual value is : "+ respScenario.getScenario_name() +" and expected: "+sc.scenarioName);
        }

        if(sc.scenarioDescription.equalsIgnoreCase(respScenario.getScenario_desc())){
            pNode.pass("Updated Scenario name matches with expected");
        }else{
            pNode.fail("Updated Scenario desc not matches with expected. Actual value is : "+ respScenario.getScenario_desc() +" and expected: "+sc.scenarioDescription);
        }

        if(respScenario.isIs_active()== Boolean.parseBoolean( sc.isActive)){
            pNode.pass("Updated Scenario status matches with expected");
        }else{
            pNode.fail("Updated Scenario status not matches with expected. Actual value is : "+ respScenario.isIs_active() +" and expected: "+sc.isActive);
        }

    }

    public Response getScenario(ScenarioAPIBean sc) {
        int workSpaceID= DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int scenarioID=DatabaseUtil.getScenarioID(sc.scenarioName);

        RequestSpecification genericReq = initAPIReq();
        RequestSpecification scenarioGetReq=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .queryParam("workspaceId",workSpaceID)
                .basePath("/nbadesigner-backend/nba/scenario/"+scenarioID);

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioGetReq);
        System.out.println( "Request is:"+queryRequest.getURI());

        Response resp=scenarioGetReq.when().get();
        return resp;
    }

    public void deleteExistingAutomationScenario(){
        System.out.println("Going to delete all scenarios for given user");
        String query="UPDATE "+ ConfigInput.dbSchemaName+".nba_scenario a\n" +
                "SET is_deleted=true \n" +
                "FROM "+ConfigInput.dbAppSchemaName+".user b\n" +
                "WHERE a.created_by =b.user_id\n" +
                "and b.user_name ='"+ConfigInput.apiUserName+"'\n"+
                "and a.scenario_name  like 'HCP_Level_Suggestions';";
        System.out.println(query);
        DatabaseUtil.runUpdateQuery(query);
    }

    public Response deleteScenario(ScenarioAPIBean sc) {
        int workSpaceID= DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        int scenarioID=DatabaseUtil.getScenarioID(sc.scenarioName);
        int userID=DatabaseUtil.getUserID("nba_automation");
        RequestSpecification genericReq = initAPIReq();
        RequestSpecification scenarioDeleteReq=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .queryParam("workspaceId",workSpaceID)
                .queryParam("user_id",userID)
                .basePath("/nbadesigner-backend/nba/scenario/"+scenarioID);

        QueryableRequestSpecification queryRequest = SpecificationQuerier.query(scenarioDeleteReq);
        System.out.println( "Request is:"+queryRequest.getURI());

        Response resp=scenarioDeleteReq.when().delete();
        return resp;
    }
}