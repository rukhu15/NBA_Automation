package application.apiPageObjects;

import apiPojo.apiReq.ScenarioSchedule.ScenarioScheduleReq;
import apiPojo.apiReq.ScenarioSchedule.ScheduleExpr;
import apiPojo.apiReq.scenarioExecution.CallAirflowDag;
import apiPojo.apiReq.scenarioExecution.CallDagsterGraph;
import apiPojo.apiReq.scenarioExecution.Conf;
import apiPojo.apiReq.scenarioExecution.EngineNotebookParameter;
import apiPojo.apiReq.scenarioExecution.Params;

import com.aventstack.extentreports.ExtentTest;
import data.beans.api.ExecuteScenarioBean;
import framework.utility.common.DatabaseUtil;
import framework.utility.common.Utils;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.StatusCode;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import scripts.BaseAPI;

import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;

/**
 * @author Prateek Sethi
 */
public class ExecuteScenarioAPI extends BaseAPI {

    private static ExtentTest pNode;
    private static String zeppelinHostIP;
    private static String zeppelinNotebookID;
    private static final Logger logToConsole = LoggerFactory.getLogger(ExecuteScenarioAPI.class);
    public static String dag_run_id;
    public ExecuteScenarioAPI(ExtentTest t3) {
    }
    public static ExecuteScenarioAPI init(ExtentTest t3) throws Exception {
        pNode = t3;
        return new ExecuteScenarioAPI(pNode);
    }


    public Response createScenarioSchedule(ExecuteScenarioBean es) {

        int scenarioID= DatabaseUtil.getScenarioID(es.scenarioName);
        int user_id=DatabaseUtil.getUserID("nba_automation");

        ScenarioScheduleReq scenarioScheduleReq = new ScenarioScheduleReq();
        scenarioScheduleReq.setScenario_id(scenarioID);
        scenarioScheduleReq.setSchedule_name("New Schedule");
        ScheduleExpr schduleExpr= new ScheduleExpr();

        String start_date=Utils.setDateInFormat(es.start_date,"yyyy/MM/dd","yyyy-MM-dd");
        schduleExpr.setStart_date(start_date);

        String end_date=Utils.setDateInFormat(es.end_date,"yyyy/MM/dd","yyyy-MM-dd");
        schduleExpr.setEnd_date(end_date);

        schduleExpr.setSchedule_interval("");
        scenarioScheduleReq.setSchedule_expr(schduleExpr);

        scenarioScheduleReq.setUser_id(user_id);
        scenarioScheduleReq.setIs_active(true);
        scenarioScheduleReq.setIs_enabled(false);
        scenarioScheduleReq.setIs_deleted(false);

        RequestSpecification genericReq = initAPIReq();
        RequestSpecification scenarioScheduleReqSpec=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("/nbadesigner-backend/nba/scenarioSchedule/"+scenarioID)
                .queryParam("unscheduled",true)
                .body(scenarioScheduleReq);
        pNode.info("########  The decorated api request is: "+scenarioScheduleReqSpec.log().body(true));
        logToConsole.info("########  The decorated api request is: "+scenarioScheduleReqSpec.log().body(true));

        Response resp=scenarioScheduleReqSpec.when()
                .post();
        pNode.info("########  The received api response is: "+resp.prettyPrint());
        logToConsole.info("########  The received api response is: "+resp.prettyPrint());
        return resp;

    }

    /*public void validateScenarioSchedule(Response resp, ExecuteScenarioBean es) {
        ResponseSpecification genericResp = initAPIResp();
        ScenarioExecutionResp respScenarioSchedule=resp.then().
                spec(genericResp)
                .extract()
                .as(ScenarioExecutionResp.class);

        if(respScenarioSchedule.getResponse().getStatus()==201){
            pNode.pass("The schedule created for scenario, "+es.scenarioName);
        }else{
            pNode.fail("The schedule could not be created for scenario, "+es.scenarioName);
        }

        if(respScenarioSchedule.isIs_enabled()==true){
            pNode.pass("The schedule created for scenario, "+es.scenarioName+" is enabled" );
        }else{
            pNode.fail("The schedule could not be enabled for scenario, "+es.scenarioName);
        }

    }
*/
    public Response executeScenario(ExecuteScenarioBean es) {
        int scenarioID= DatabaseUtil.getScenarioID(es.scenarioName);
        int workSpaceID= DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
        
        int company_id = DatabaseUtil.getValueFromColumn("company_id",ConfigInput.dbAppSchemaName,
				"multitenant_config","project_id="+workSpaceID);
        String companyWorkspaceWhereClause = "company_id = " + company_id + " AND project_id = " + workSpaceID;
		String client_id=DatabaseUtil.getValueFromColumn("company_alias",ConfigInput.dbAppSchemaName,
				"multitenant_config",companyWorkspaceWhereClause);
		String ops_dist_list=DatabaseUtil.getValueFromColumn("ops_dist_list",ConfigInput.dbAppSchemaName,
				"multitenant_config",companyWorkspaceWhereClause);
		String metastore_db_name=DatabaseUtil.getValueFromColumn("glue_db_name",ConfigInput.dbAppSchemaName,
				"multitenant_config",companyWorkspaceWhereClause);
		String secret_key=DatabaseUtil.getValueFromColumn("secret_key",ConfigInput.dbAppSchemaName,
				"multitenant_config",companyWorkspaceWhereClause);
		String wrkspc_dir_path=DatabaseUtil.getValueFromColumn("wrkspc_dir_path",ConfigInput.dbAppSchemaName,
				"multitenant_config",companyWorkspaceWhereClause);
		String zeppelin_host=DatabaseUtil.getValueFromColumn("generic_cluster",ConfigInput.dbAppSchemaName,
				"multitenant_config",companyWorkspaceWhereClause);

        CallDagsterGraph dagsterObj= new CallDagsterGraph();
        dagsterObj.setScenario_id(scenarioID);
        dagsterObj.setScenario_name(es.scenarioName);
        dagsterObj.setDebug_mode(true);
		dagsterObj.setOps_dist_list(ops_dist_list);
		dagsterObj.setCompany_alias(client_id);
		dagsterObj.setCompany_id(company_id);
		dagsterObj.setMetastore_db_name(metastore_db_name);
		dagsterObj.setSecret_key(secret_key);
		dagsterObj.setWrkspc_dir_path(wrkspc_dir_path);
		dagsterObj.setZeppelin_host(zeppelin_host);
		
		//Setting ZeppelinIP to use for expectedNotebookExecution related task
		zeppelinHostIP = zeppelin_host;

		RequestSpecification genericReq = initAPIReq(ConfigInput.baseURI);
        RequestSpecification scenarioExecuteReqSpec=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/dagsterCall")
                .queryParam("workspaceId",workSpaceID)
                .queryParam("status","manual")
                .body(dagsterObj);
        pNode.info("########  The decorated api request is: "+scenarioExecuteReqSpec.log().body(true));
        logToConsole.info("########  The decorated api request is: "+scenarioExecuteReqSpec.log().body(true));

        Response resp=scenarioExecuteReqSpec.when()
                .post();
        pNode.info("########  The received api response is: "+resp.prettyPrint());
        logToConsole.info("########  The received api response is: "+resp.prettyPrint());
        return resp;
    }

    public void validateDagStatus(ExecuteScenarioBean es, Response response) {
    	if (response.getStatusCode() == StatusCode.CODE_200.getCode()
				&& response.jsonPath().getInt("response.status") == StatusCode.CODE_200.getCode()) {
			pNode.pass("Scenario execution initiated successfully as dagsterCall API responded as status code "
					+ response.getStatusCode());
		} else {
			pNode.fail(
					"Dagster not accepted or or failed scenario execution request as dagsterCall API status code or response status is not 200, marking the build failed");
			Assert.fail(
					"Dagster not accepted or or failed scenario execution request as dagsterCall API status code or response status is not 200, marking the build failed");
		}
        int scenarioID=DatabaseUtil.getScenarioID(es.scenarioName);
        RequestSpecification genericReq = initAPIReq(ConfigInput.baseURI);
        RequestSpecification scenarioGetReqSpec=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("nbadesigner-backend/nba/scenarioExecutionStatus")
                .queryParam("workspaceId",DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName))
                .queryParam("user_id",DatabaseUtil.getUserID("nba_automation"));

        int count=0;
                while(count<=7){
                    try {

                        Thread.sleep(120000);
                        Response resp=scenarioGetReqSpec
                                .relaxedHTTPSValidation()
                                .when().get();
                        String status=getScenarioExecutionStatusFromResp(resp.asString(),scenarioID);
                        if(status.equalsIgnoreCase("success")){
                            pNode.pass("Dagster Graph executed successfully!!");
                            return;
                        }else if(status.equalsIgnoreCase("Failed") || status.equalsIgnoreCase("Validation Failed")){
                            pNode.fail("Dagster Graph execution Failed!!");
                            Assert.fail("Dagster Graph execution Failed, marking the build failed");
                        }else{
                            logToConsole.info("Current execution state is: "+status);
                            logToConsole.info("Going to wait for 2 mins...., and then will check for graph scenario execution status again.....");
                        }
                        count++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                logToConsole.error("Dagster on demand graph failed or timed out!!");
                pNode.fail("Dagster on demand graph failed or timed out!!");
                Assert.fail("Dagster Graph execution Failed or timed out, marking the build failed");
    }

    public String getScenarioExecutionStatusFromResp(String jsonStr, int scenarioID){
        String status_desc="";
        JSONObject jsonObj = new JSONObject(jsonStr);
        JSONArray jsonArr=jsonObj.getJSONArray("data");
        for(int i=0;i<jsonArr.length();i++){
            JSONObject individualJsonObj=jsonArr.getJSONObject(i);
            int scenario_id=individualJsonObj.getInt("scenario_id");
            if(scenario_id==scenarioID){
                status_desc=individualJsonObj.getString("status_desc");
                break;
            }
        }
        return status_desc;
    }

    public void deleteExpectedNotebookIfPresent(String NotebookName) {
		String NotebkNameWithSlash = "/" + NotebookName;
		RequestSpecification genericReq = initAPIReq(zeppelinHostIP);
		RequestSpecification clusterNotebookGetReqSpec = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("/notebook");
		Response resp = clusterNotebookGetReqSpec.when().get();
		if (resp.getStatusCode() == 200) {
			JSONObject response = new JSONObject(resp.asString());
			JSONArray getAllNotebookDetails = response.getJSONArray("body");
			for (int index = 0; index < getAllNotebookDetails.length(); index++) {
				if (getAllNotebookDetails.getJSONObject(index).getString("path")
						.equalsIgnoreCase(NotebkNameWithSlash)) {
					RequestSpecification notebookDeleteReqSpec = given().spec(genericReq).relaxedHTTPSValidation()
							.basePath("/notebook/" + getAllNotebookDetails.getJSONObject(index).getString("id"));
					Response deleteresp = notebookDeleteReqSpec.when().delete();
					if (deleteresp.getStatusCode() == 200) {
						pNode.pass("Existing engine notebook deleted successfully!!");
						logToConsole.info("Existing engine notebook deleted successfully!!");
						return;
					} else {
						logToConsole.error("Existing engine notebook not deleted successfully!!");
						pNode.fail("Existing engine notebook not deleted successfully!!");
						Assert.fail("Existing engine notebook not deleted successfully!!");
					}
				}
			}
		} else {
			logToConsole.warn("Unable to fetch all the existing notebook details to delete previous engine notebook before uploading new notebook!!");
			pNode.fail(
					"Unable to fetch all the existing notebook details to delete previous engine notebook before uploading new notebook!!");
			Assert.fail(
					"Unable to fetch all the existing notebook details to delete previous engine notebook before uploading new notebook!!");
		}

	}

	public void uploadExpectedNoteBook(ExecuteScenarioBean es) {
		String zepNotebookName = ConfigInput.zeppelinNotebookName;
		deleteExpectedNotebookIfPresent(zepNotebookName);
		JSONParser parser = new JSONParser();
		JSONObject uploadExpJSON = new JSONObject();
		try {
			logToConsole.info("Absolute Path for notebook"+new File("").getAbsolutePath());
			String filePath = new File("").getAbsolutePath() + "/src/main/resources/Zeppelin_Notebook/"
					+ zepNotebookName + ".json";
			logToConsole.info("Full Path for notebook"+filePath);
			Object obj = parser.parse(new FileReader(filePath));
			uploadExpJSON = new JSONObject(obj.toString());
			//logToConsole.info("Expected Notebook upload JSON "+uploadExpJSON.toString());
		} catch (Exception e) {
			logToConsole.info("expected notebook not loaded successfully!!");
			pNode.fail("expected notebook not loaded successfully!!");
			Assert.fail("expected notebook not loaded successfully!!");
		}

		RequestSpecification genericReq = initAPIReq(zeppelinHostIP);
		RequestSpecification notebookPostReqSpec = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("/notebook/import").body(uploadExpJSON.toString());
		Response resp = notebookPostReqSpec.when().post();
		if (resp.getStatusCode() == 201 || resp.getStatusCode() == 200) {
			pNode.pass("expected notebook uploaded successfully on zeppelin server!!");
			logToConsole.info("expected notebook uploaded successfully on zeppelin server!!");
			JSONObject response = new JSONObject(resp.asString());
			zeppelinNotebookID = response.getString("body");
		} else {
			logToConsole.error("expected notebook could not be uploaded successfully on zeppelin server!!");
			pNode.fail("expected notebook could not be uploaded successfully on zeppelin server!!");
			Assert.fail("expected notebook could not be uploaded successfully on zeppelin server!!");
		}
	}

    public void executeExpectedNoteBook(ExecuteScenarioBean es) {
		int scenarioID = DatabaseUtil.getScenarioID(es.scenarioName);
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		EngineNotebookParameter notebookObj = getEngineNotebookJSON(scenarioID, workSpaceID);
		restartNotebookInterpreter();
		clearNotebookPreviousResults();
		logToConsole.info("Expected Notebook parameter JSON "+notebookObj.toString());
		RequestSpecification genericReq = initAPIReq(zeppelinHostIP);
		RequestSpecification executeNotebookPostReqSpec = given().spec(genericReq).relaxedHTTPSValidation()
				.basePath("/notebook/job/" + zeppelinNotebookID).body(notebookObj);
        Response resp=executeNotebookPostReqSpec.when().post();
		if(resp.getStatusCode()==200){
            pNode.pass("expected notebook execution started successfully!!");
            logToConsole.info("expected notebook execution started successfully!!");
        }else{
            pNode.fail("expected notebook execution could not be started successfully!!");
            Assert.fail("expected notebook execution could not be started successfully!!");
            logToConsole.error("expected notebook execution could not be started successfully!!");
        }
    }

    public void validateTestCases() {

        List<String> testCasesList=generateListOfTestCases();
        Response resp;
        String currentlyExecutingCellID="";
        String paragraphStatus="";
        String respStr="";
        int counter=0;
        boolean flag=false;
        resp=getNoteBookExecutionStatus();
        respStr= resp.asString();
        while(counter<=5){
            try {
                Thread.sleep(60000);
                resp=getNoteBookExecutionStatus();
                respStr=resp.asString();
                JSONObject notebookExecutionJsonObj = new JSONObject(respStr);
                JSONObject bodyObj=notebookExecutionJsonObj.getJSONObject("body");
                boolean isRunningFlag=bodyObj.getBoolean("isRunning");
                if(isRunningFlag==false){
                    break;
                }
                counter++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONObject notebookExecutionJsonObj = new JSONObject(respStr);
        JSONObject bodyObj=notebookExecutionJsonObj.getJSONObject("body");
        JSONArray paraGraphArr= bodyObj.getJSONArray("paragraphs");

        for(int index =0;index<=paraGraphArr.length()-1;index++){
            JSONObject paraGraphJson=paraGraphArr.getJSONObject(index);
            paragraphStatus=paraGraphJson.getString("status");
            String paraGraphId=paraGraphJson.getString("id");
            if(paragraphStatus.equalsIgnoreCase("ERROR")
                    || paragraphStatus.equalsIgnoreCase("ABORT")){
                logResultInReport(paragraphStatus,paraGraphId);
                flag=true;
                break;
            }
        }
if(!flag) {
    for (String str : testCasesList) {
        pNode.pass("Successfully validated : " + str);
        logToConsole.info("Successfully validated : " + str);
    }
}
restartNotebookInterpreter();
    }

        public void clearNotebookPreviousResults(){
            System.out.println("Clearing the previous notebook execution results...");
            RequestSpecification genericReq = initAPIReq(zeppelinHostIP);
            RequestSpecification previousResultPutReqSpec=given().spec(genericReq)
                    .relaxedHTTPSValidation()
                    .basePath("/notebook/"+zeppelinNotebookID+"/clear");
            Response respFromAPI=previousResultPutReqSpec.when().put();
            if(respFromAPI.getStatusCode()==200){
                pNode.pass("Notebook previous results deleted");
                logToConsole.info("Notebook previous results deleted");
            }else{
                pNode.fail("Notebook previous results could not be deleted");
                Assert.fail("Notebook previous results could not be deleted");
                logToConsole.error("Notebook previous results could not be deleted");
            }
        }

        public void restartNotebookInterpreter(){
            System.out.println("Restarting the notebook spark Interpreter...");
            RequestSpecification genericReq = initAPIReq(zeppelinHostIP);
            RequestSpecification restartNtbkPutReqSpec=given().spec(genericReq)
                    .relaxedHTTPSValidation()
                    .basePath("/interpreter/setting/restart/spark").body("{noteId:"+zeppelinNotebookID+"}");
            Response respFromAPI=restartNtbkPutReqSpec.when().put();
            if(respFromAPI.getStatusCode()==200){
                pNode.pass("Notebook Interpreter restarted successfully");
                logToConsole.info("Notebook Interpreter restarted successfully");
            }else{
                pNode.fail("Notebook Interpreter not restarted due to some reason");
                Assert.fail("Notebook Interpreter not restarted due to some reason");
                logToConsole.error("Notebook Interpreter not restarted due to some reason");
            }
        }

    public List<String> generateListOfTestCases(){
        List<String> arrList = new ArrayList<>();
        Response respForTestCases=getNoteBookExecutionStatus();
        String respStr= respForTestCases.asString();
        JSONObject notebookExecutionJsonObj = new JSONObject(respStr);
        JSONObject bodyObj=notebookExecutionJsonObj.getJSONObject("body");
        JSONArray paraGraphArr= bodyObj.getJSONArray("paragraphs");
        for(int index =0;index<=paraGraphArr.length()-1;index++){
            JSONObject paraGraphJson=paraGraphArr.getJSONObject(index);
            String paragraphIdStr=paraGraphJson.getString("id");
            arrList.add(paragraphIdStr);
        }
        return arrList;
    }

    public void logResultInReport(String status,String currentlyExecutingCellID){
        if(status.equalsIgnoreCase("FINISHED")){
            pNode.pass("The test case "+currentlyExecutingCellID+" is passed successfully");
            logToConsole.info("The test case "+currentlyExecutingCellID+" is passed successfully");
        }else if(status.equalsIgnoreCase("ERROR")){
            pNode.fail("The test case "+currentlyExecutingCellID+" is failed");
            Assert.fail("The test case "+currentlyExecutingCellID+" is failed");
            logToConsole.error("The test case "+currentlyExecutingCellID+" is failed");
        }
    }
    public Response getNoteBookExecutionStatus(){
    	RequestSpecification genericReq = initAPIReq(zeppelinHostIP);
        RequestSpecification scenarioGetReqSpec=given().spec(genericReq)
                .relaxedHTTPSValidation()
                .basePath("/notebook/job/"+zeppelinNotebookID);
       return scenarioGetReqSpec.when().get();
    }

    public String parseJsonRespAndGetIdOfExecutingPara(Response resp){
        String paragraphIdStr="";
        String respStr= resp.asString();
        JSONObject notebookExecutionJsonObj = new JSONObject(respStr);
        JSONObject bodyObj=notebookExecutionJsonObj.getJSONObject("body");
        JSONArray paraGraphArr= bodyObj.getJSONArray("paragraphs");
        for(int index =0;index<=paraGraphArr.length()-1;index++){
            JSONObject paraGraphJson=paraGraphArr.getJSONObject(index);
            if(paraGraphJson.getString("status").equalsIgnoreCase("RUNNING")){
                paragraphIdStr=paraGraphJson.getString("id");
                break;
            }
        }
        System.out.println("Currently Executing paragraph is: "+paragraphIdStr);
        logToConsole.info("Currently Executing paragraph is: "+paragraphIdStr);
        return paragraphIdStr;
	}

	public static EngineNotebookParameter getEngineNotebookJSON(int scenarioID, int workSpaceID) {
		EngineNotebookParameter notebookObj = new EngineNotebookParameter();
		Params scenarioDetails = new Params();
		    
		String glueDB=DatabaseUtil.getValueFromColumn("glue_db_name",ConfigInput.dbAppSchemaName,
				"multitenant_config","project_id="+workSpaceID);
		scenarioDetails.setDb_name(glueDB);
		
		scenarioDetails.setWide_base_table(ConfigInput.wideBaseTable);
		scenarioDetails.setClient_ref(ConfigInput.client_id);
		scenarioDetails.setForPublishHistTable(ConfigInput.forPublishHistTable);
		//scenarioID = 3456;
		scenarioDetails.setAud_where_clause(getAudienceWhereClause(scenarioID, workSpaceID));
		scenarioDetails.setScenario_id(scenarioID);
		List<Integer> workflowIDsList = DatabaseUtil.getWorkflowIDsForScenario(scenarioID, workSpaceID);
		scenarioDetails.setWorkflow_count(workflowIDsList.size());

		scenarioDetails.setTrigger_where_clause(getTriggerRuleWhereClause("trigger_expr", workflowIDsList));
		scenarioDetails.setRule_where_clause(getTriggerRuleWhereClause("rule_expr", workflowIDsList));

		String workflowIDs = "";
		for (Integer workflowID : workflowIDsList) {
			if (workflowIDs != "") {
				workflowIDs += ":";
			}
			workflowIDs += workflowID;
		}
		scenarioDetails.setWorkflowIDs(workflowIDs);

		String actions_details = getActionChannelDetails(workflowIDsList);
		scenarioDetails.setAction_channel_details(actions_details);
		scenarioDetails.setChannelIDNameList(getChannelIDName(actions_details));
		scenarioDetails.setAdjudicate_channel(getAdjudicateChannel(scenarioID, actions_details));
		scenarioDetails.setAdjudicate_client(getAdjudicateClient(scenarioID));
		scenarioDetails.setAdjudicate_clientChannel(getAdjudicateClientChannel(scenarioID, actions_details));

		notebookObj.setParams(scenarioDetails);
		return notebookObj;
	}

	public static String getAudienceWhereClause(int scenarioID, int workSpaceID) {
		int audienceID = DatabaseUtil.getCurrentActiveAudienceIDForScenario(scenarioID, workSpaceID);
		String query = "select filter_expr from " + ConfigInput.dbSchemaName + ".nba_audience where audience_id = "
				+ audienceID + ";";
		ResultSet aud_Expr = DatabaseUtil.runSelectQuery(query);
		String expr = convertResultSetToString(aud_Expr);
		return getWhereClause(expr);
	}

	public static String getTriggerRuleWhereClause(String column, List<Integer> workflowIDs) {
		String whereCondition = "";
		for (Integer workflowID : workflowIDs) {
			String query = "select " + column + " from " + ConfigInput.dbSchemaName
					+ ".nba_workflow where workflow_id = " + workflowID + ";";
			ResultSet triggerRule_Expr = DatabaseUtil.runSelectQuery(query);
			String expr = convertResultSetToString(triggerRule_Expr);
			if (whereCondition != "") {
				whereCondition += ":";
			}
			whereCondition += getWhereClause(expr);
		}
		return whereCondition;
	}

	public static String getActionChannelDetails(List<Integer> workflowIDsList) {
		String actionDetails = "";
		for (Integer workflowID : workflowIDsList) {
			String query = "select action_expr from " + ConfigInput.dbSchemaName + ".nba_workflow where workflow_id = "
					+ workflowID + ";";
			ResultSet action_Expr = DatabaseUtil.runSelectQuery(query);
			String expr = convertResultSetToString(action_Expr);
			if (actionDetails != "") {
				actionDetails += ":";
			}
			JSONObject action_expr = new JSONObject(expr);
			JSONArray action_arr = action_expr.getJSONArray("actions");
			actionDetails += action_arr.length() + "-";
			for (int i = 0; i < action_arr.length(); i++) {
				JSONObject singleAction = action_arr.getJSONObject(i);
				if (i != 0) {
					actionDetails += ",";
				}
				actionDetails += singleAction.get("channel_id");
			}
		}
		return actionDetails;
	}

	// Get whole scenario configured channels thru Actions String which we generated
	// for Action section
	public static Set<String> getScenarioChannels(String actions_details) {
		Set<String> channelSet = new HashSet<String>();
		String action[] = actions_details.split(":");
		for (String a : action) {
			String channels[] = a.split("-")[1].split(",");
			for (String channel : channels) {
				channelSet.add(channel);
			}
		}
		return channelSet;
	}

	public static String getChannelIDName(String actions_details) {
		String channelIDName = "";
		Set<String> channelSet = getScenarioChannels(actions_details);

		int i = 0;
		for (String channel : channelSet) {
			channelIDName += channel + "-";
			String query = "select channel_name from " + ConfigInput.dbSchemaName + ".ciq_channel "
					+ "where channel_id = " + channel + ";";
			ResultSet channelName = DatabaseUtil.runSelectQuery(query);
			channelIDName += convertResultSetToString(channelName);
			if (i < channelSet.size() - 1) {
				i++;
				channelIDName += ":";
			}
		}
		return channelIDName;
	}

	public static String getAdjudicateChannel(int scenarioID, String actions_details) {
		String adjChannel = "";
		Set<String> channelSet = getScenarioChannels(actions_details);

		int i = 0;
		for (String channel : channelSet) {
			adjChannel += channel + "-";
			String query = "select max_limit from " + ConfigInput.dbSchemaName + ".nba_adj_channel where scenario_id = "
					+ scenarioID + " " + " AND channel_id = " + channel + " And is_enabled = true;";
			ResultSet max_limit = DatabaseUtil.runSelectQuery(query);
			adjChannel += convertResultSetToString(max_limit);
			if (i < channelSet.size() - 1) {
				i++;
				adjChannel += ":";
			}
		}
		return adjChannel;
	}

	public static String getAdjudicateClient(int scenarioID) {
		String adjClientDetails = "";
		String query = "select max_sug_limit from " + ConfigInput.dbSchemaName + ".nba_adj_hcp where scenario_id = "
				+ scenarioID + " " + " And is_enabled = true;";
		ResultSet sug_limit = DatabaseUtil.runSelectQuery(query);
		adjClientDetails += convertResultSetToString(sug_limit);

		query = "select min_interval_days from " + ConfigInput.dbSchemaName + ".nba_adj_hcp where scenario_id = "
				+ scenarioID + " " + " And is_enabled = true;";
		sug_limit = DatabaseUtil.runSelectQuery(query);
		adjClientDetails += ":" + convertResultSetToString(sug_limit);
		return adjClientDetails;
	}

	public static String getAdjudicateClientChannel(int scenarioID, String actions_details) {
		String adjClientChannel = "";
		Set<String> channelSet = getScenarioChannels(actions_details);

		int i = 0;
		for (String channel : channelSet) {
			adjClientChannel += channel + "#";
			String query = "select max_sug_limit from " + ConfigInput.dbSchemaName
					+ ".nba_adj_hcp_channel where scenario_id = " + scenarioID + " " + " AND channel_id = " + channel
					+ " And is_enabled = true;";
			ResultSet sug_limit = DatabaseUtil.runSelectQuery(query);
			adjClientChannel += convertResultSetToString(sug_limit);

			query = "select min_interval_days from " + ConfigInput.dbSchemaName
					+ ".nba_adj_hcp_channel where scenario_id = " + scenarioID + " " + " AND channel_id = " + channel
					+ " And is_enabled = true;";
			sug_limit = DatabaseUtil.runSelectQuery(query);
			adjClientChannel += "," + convertResultSetToString(sug_limit);

			if (i < channelSet.size() - 1) {
				i++;
				adjClientChannel += ":";
			}
		}
		return adjClientChannel;
	}

	public static String convertResultSetToString(ResultSet expr) {
		String strExpr = null;
		try {
			if (expr.next()) {
				strExpr = expr.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (strExpr == null) {
			strExpr = "0";
		}
		return strExpr;
	}

	public static String getWhereClause(String expr) {
		String completeWhereClause = "";
		JSONObject exprJSON = new JSONObject(expr);
		String operator = exprJSON.getString("combinator");
		JSONArray ruleJsonArr = exprJSON.getJSONArray("rules");
		for (int index = 0; index < ruleJsonArr.length(); index++) {
			if (index == 0) {
				completeWhereClause += "(";
			}
			JSONObject ruleJsonObj = ruleJsonArr.getJSONObject(index);
			if (ruleJsonObj.has("column_name")) {
				String subWhereClause = "";
				subWhereClause += ruleJsonObj.getString("column_name");
				if (ruleJsonObj.has("time_range") && ruleJsonObj.getInt("time_range") > 0) {
					subWhereClause += "_0" + ruleJsonObj.get("time_grain") + ruleJsonObj.get("time_range");
					if (ruleJsonObj.has("prev_time_type")) {
						subWhereClause += "_" + ruleJsonObj.getString("prev_time_type");
					}
				} else if (ruleJsonObj.has("is_kpi_groupby_disabled")
						&& !ruleJsonObj.getBoolean("is_kpi_groupby_disabled")) {
					subWhereClause += "_MINPERIODTO_MAX";
				}
				if (ruleJsonObj.get("operator").equals("IN")) {
					subWhereClause += " " + ruleJsonObj.get("operator") + " (";
				} else {
					JSONArray operatorJsonArr = ruleJsonObj.getJSONArray("operator");
					for (int i = 0; i < operatorJsonArr.length(); i++) {
						subWhereClause += " " + operatorJsonArr.get(i) + " ";
					}
				}
				JSONArray valueJsonArr = ruleJsonObj.getJSONArray("value");
				for (int i = 0; i < valueJsonArr.length(); i++) {
					try {
						subWhereClause += valueJsonArr.getDouble(i);
					} catch (JSONException e) {
						subWhereClause += "\"" + valueJsonArr.get(i) + "\"";
					}
					if (ruleJsonObj.get("operator").equals("IN")) {
						if (i == valueJsonArr.length() - 1) {
							subWhereClause += ")";
						} else {
							subWhereClause += ",";
						}
					}
				}
				if (index == ruleJsonArr.length() - 1) {
					completeWhereClause += subWhereClause + ")";
				} else {
					completeWhereClause += subWhereClause + " " + operator + " ";
				}
			} else {
				if (index == ruleJsonArr.length() - 1) {
					completeWhereClause += getWhereClause(ruleJsonObj.toString()) + ")";
				} else {
					completeWhereClause += getWhereClause(ruleJsonObj.toString()) + " " + operator + " ";
				}
			}
		}
		return completeWhereClause;
	}
}