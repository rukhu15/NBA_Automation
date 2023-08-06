package application.apiPageObjects;

import apiPojo.apiReq.workflow.*;
import apiPojo.apiResp.audience.AudienceResp;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.WorkflowAPIBean;
import framework.utility.common.Assertion;
import framework.utility.common.AthenaDataUtil;
import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalConst.StatusCode;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scripts.BaseAPI;

import java.io.IOException;
import java.util.*;

/**
 * @author Prateek Sethi
 */
public class ModelAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(ModelAPI.class);

	public ModelAPI(ExtentTest t3) {
	}

	public static ModelAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new ModelAPI(pNode);
	}

	public Rule_Expr decorateRuleObj(String ruleExpr) {
		Rule_Expr rule_exprObj = new Rule_Expr();
		List<Object> rulesList = new ArrayList<>();
		JSONObject ruleJsonObject = new JSONObject(ruleExpr);
		rule_exprObj.setNot(ruleJsonObject.getBoolean("not"));
		JSONArray ruleJsonArr = ruleJsonObject.getJSONArray("rules");
		int count = 0;
		for (int index = 0; index < ruleJsonArr.length(); index++) {
			count = index;
			JSONObject ruleJsonObj = ruleJsonArr.getJSONObject(index);


			if (ruleJsonObj.has("rule_type") && ruleJsonObj.getInt("rule_type") == 2) {
				int kpi_id=DatabaseUtil.getKPiId(ruleJsonObj.getString("kpi_name"));
				AttributeRule attributeRuleObj = new AttributeRule();
				
				JSONArray valueJsonArr = ruleJsonObj.getJSONArray("value");
				List<Object> valueArr = new ArrayList<>();
				for (int j = 0; j < valueJsonArr.length(); j++) {
					valueArr.add(valueJsonArr.get(j));
				}
				attributeRuleObj.setValue(valueArr);
				
				attributeRuleObj.setKpi_id(kpi_id);
				attributeRuleObj.setRule_id(kpi_id);
				attributeRuleObj.setKpi_type(ruleJsonObj.getString("kpi_type"));
				
				JSONArray operatorJsonArr = ruleJsonObj.getJSONArray("operator");
				List<String> operatorArr = new ArrayList<>();
				for (int k = 0; k < operatorJsonArr.length(); k++) {
					operatorArr.add((String) operatorJsonArr.get(k));
				}
				attributeRuleObj.setOperator(operatorArr);
				
				attributeRuleObj.setRule_type(ruleJsonObj.getInt("rule_type"));
				attributeRuleObj.setRule_seqno(ruleJsonObj.getInt("rule_seqno"));
				attributeRuleObj.setColumn_name("K"+kpi_id+"_R"+kpi_id);
				attributeRuleObj.setDisplay_name(ruleJsonObj.getString("display_name"));
				attributeRuleObj.setIs_kpi_groupby_disabled(ruleJsonObj.getBoolean("is_kpi_groupby_disabled"));
				rulesList.add(attributeRuleObj);
			} else if (ruleJsonObj.has("rule_type") && ruleJsonObj.getInt("rule_type") == 3) {
				int kpi_id=DatabaseUtil.getKPiId(ruleJsonObj.getString("kpi_name"));
				BaseRule baseRuleObj = new BaseRule();
				JSONArray valueJsonArr = ruleJsonObj.getJSONArray("value");
				List<Object> valueArr = new ArrayList<>();
				for (int j = 0; j < valueJsonArr.length(); j++) {
					valueArr.add(valueJsonArr.get(j));
				}
				baseRuleObj.setValue(valueArr);
				
				baseRuleObj.setKpi_id(kpi_id);
				baseRuleObj.setRule_id(kpi_id);
				baseRuleObj.setKpi_type(ruleJsonObj.getString("kpi_type"));

				JSONArray operatorJsonArr = ruleJsonObj.getJSONArray("operator");
				List<String> operatorArr = new ArrayList<>();
				for (int k = 0; k < operatorJsonArr.length(); k++) {
					operatorArr.add((String) operatorJsonArr.get(k));
				}
				baseRuleObj.setOperator(operatorArr);
				
				baseRuleObj.setRule_type(ruleJsonObj.getInt("rule_type"));
				baseRuleObj.setRule_seqno(ruleJsonObj.getInt("rule_seqno"));
				baseRuleObj.setTime_grain(ruleJsonObj.getString("time_grain"));
				baseRuleObj.setTime_range(ruleJsonObj.getInt("time_range"));
				baseRuleObj.setColumn_name("K"+kpi_id+"_R"+kpi_id);
				baseRuleObj.setDisplay_name(ruleJsonObj.getString("display_name"));
				baseRuleObj.setTime_recency(ruleJsonObj.getInt("time_recency"));
				baseRuleObj.setIs_kpi_groupby_disabled(ruleJsonObj.getBoolean("is_kpi_groupby_disabled"));
				rulesList.add(baseRuleObj);
			} else if (ruleJsonObj.has("rule_type") && ruleJsonObj.getInt("rule_type") == 4) {
				int kpi_id=DatabaseUtil.getKPiId(ruleJsonObj.getString("kpi_name"));
				MetricRule metricRulesObj = new MetricRule();
				JSONArray valueJsonArr = ruleJsonObj.getJSONArray("value");
				List<Object> valueArr = new ArrayList<>();
				for (int j = 0; j < valueJsonArr.length(); j++) {
					valueArr.add(valueJsonArr.get(j));
				}
				metricRulesObj.setValue(valueArr);
				
				metricRulesObj.setKpi_id(kpi_id);
				metricRulesObj.setRule_id(kpi_id);
				metricRulesObj.setKpi_type(ruleJsonObj.getString("kpi_type"));

				JSONArray operatorJsonArr = ruleJsonObj.getJSONArray("operator");
				List<String> operatorArr = new ArrayList<>();
				for (int k = 0; k < operatorJsonArr.length(); k++) {
					operatorArr.add((String) operatorJsonArr.get(k));
				}
				metricRulesObj.setOperator(operatorArr);
				
				metricRulesObj.setRule_type(ruleJsonObj.getInt("rule_type"));
				metricRulesObj.setRule_seqno(ruleJsonObj.getInt("rule_seqno"));
				metricRulesObj.setTime_grain(ruleJsonObj.getString("time_grain"));
				metricRulesObj.setTime_range(ruleJsonObj.getInt("time_range"));
				metricRulesObj.setColumn_name("K"+kpi_id+"_R"+kpi_id);
				metricRulesObj.setDisplay_name(ruleJsonObj.getString("display_name"));
				metricRulesObj.setTime_recency(ruleJsonObj.getInt("time_recency"));
				metricRulesObj.setPrev_time_type(ruleJsonObj.getString("prev_time_type"));
				metricRulesObj.setIs_kpi_groupby_disabled(ruleJsonObj.getBoolean("is_kpi_groupby_disabled"));
				rulesList.add(metricRulesObj);
			} else {
				rulesList.add(decorateRuleObj(ruleJsonObj.toString()));
			}
		}
		rule_exprObj.setCombinator(ruleJsonObject.getString("combinator"));
		rule_exprObj.setGroup_name(ruleJsonObject.getString("group_name"));
		//if (ruleJsonObject.optJSONObject("rule_seqno") == null) {
		if (ruleJsonObject.isNull("rule_seqno")) {
			rule_exprObj.setRule_seqno(null);
		} else {
			rule_exprObj.setRule_seqno(ruleJsonObject.getInt("rule_seqno"));
		}
		rule_exprObj.setRules(rulesList);
		return rule_exprObj;
	}

	public Response createWorkflowInScenario(WorkflowAPIBean wb) {
		int scenarioID = DatabaseUtil.getScenarioID(wb.scenarioName);
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		//int kpi_id=DatabaseUtil.getKPiId(wb.kpi_name);
		WorkflowReq workflowReqObj = new WorkflowReq();
		workflowReqObj.setWorkflow_name(wb.workflow_name);
		workflowReqObj.setWorkflow_desc(wb.workflow_desc);
		System.out.println(" Setting Trigger");

		Rule_Expr triggerRuleObj = decorateRuleObj(wb.trigger_expr);
		workflowReqObj.setTrigger_expr(triggerRuleObj);

		System.out.println(" Setting Rules");
		Rule_Expr rule_exprObj = decorateRuleObj(wb.rule_expr);
		workflowReqObj.setRule_expr(rule_exprObj);

		System.out.println(" Setting Action");
		Action_Expr action_exprObj = decorateActionObj(wb.action_expr);
		workflowReqObj.setAction_expr(action_exprObj);

		Map<String,Object> queryParamsMap = new HashMap<String,Object>();
		queryParamsMap.put("workspaceId",workSpaceID);
		queryParamsMap.put("scenarioId",scenarioID);

		RequestSpecification modelReq = getRequestSpecification("/nbadesigner-backend/nba/model/workflow",queryParamsMap,workflowReqObj);

		pNode.info("########  The decorated api request is: " + modelReq.log().body(true));
		logToConsole.info("########  The decorated api request is: " + modelReq.log().body(true));

		Response resp = modelReq.when().post();
		pNode.info("########  The received api response is: " + resp.prettyPrint());
		logToConsole.info("########  The received api response is: " + resp.prettyPrint());
		return resp;

	}

	private Action_Expr decorateActionObj(String action_expr) {

		Action_Expr action_exprObj = new Action_Expr();
		List<Actions> actionsList = new ArrayList<>();
		JSONObject actionExprObj = new JSONObject(action_expr);
		JSONArray jsonArr = actionExprObj.getJSONArray("actions");
		for (int index = 0; index < jsonArr.length(); index++) {
			JSONObject actionJsonObj = jsonArr.getJSONObject(index);
			Actions actionObj = new Actions();
			String channel_name = actionJsonObj.getString("channel_name");
			int channel_id = DatabaseUtil.getChannelID(channel_name);
			actionObj.setChannel_id(channel_id);
			actionObj.setSequence_id(actionJsonObj.getInt("sequence_id"));
			actionObj.setSchedule_type(actionJsonObj.getString("schedule_type"));
			/*actionObj.setContent_level_1(actionJsonObj.getString("content_level_1"));
			actionObj.setContent_level_2(actionJsonObj.getString("content_level_2"));*/
			actionObj.setSchedule_offset(actionJsonObj.getInt("schedule_offset"));
			actionObj.setAction_priority_key(actionJsonObj.getInt("action_priority_key"));
			actionObj.setOverride_automated_scoring(actionJsonObj.getBoolean("override_automated_scoring"));
			//T-Do: To add different combinations of actions data as per content change
			actionObj.setConfigure(actionJsonObj.getBoolean("configure"));
			actionObj.setOverride_key_topics(actionJsonObj.getBoolean("override_key_topics"));
			actionObj.setKey_topics(actionJsonObj.getString("key_topics"));
			actionObj.setSuggestion_title(actionJsonObj.getString("suggestion_title"));
			actionObj.setSuggestion_reason(actionJsonObj.getString("suggestion_reason"));
			actionsList.add(actionObj);
		}
		action_exprObj.setActions(actionsList);
		return action_exprObj;

	}

	public void validateWorkflowInScenario(Response resp, WorkflowAPIBean wb) throws IOException {
		ResponseSpecification genericResp = initAPIResp();
		AudienceResp respWorkflow = resp.then().spec(genericResp).extract().as(AudienceResp.class);

		if (respWorkflow.getResponse().getStatus().equalsIgnoreCase(String.valueOf(StatusCode.CODE_200.getCode()))) {
			pNode.pass("Workflow has been created successfully: " + wb.workflow_name);
		} else {
			Assertion.raiseExceptionAndStop(new Exception("Workflow has not been created successfully: " + wb.workflow_name),pNode);
		}

		if (respWorkflow.getResponse().getTitle().equalsIgnoreCase("Completed")) {
			pNode.pass("Actual Workflow title in response matches with expected");
		} else {
			Assertion.raiseExceptionAndStop(new Exception("Actual Workflow title in response not matches with expected"),pNode);
		}

		if (respWorkflow.getResponse().getType().equalsIgnoreCase("Created")) {
			pNode.pass("Actual Workflow type in response matches with expected");
		} else {
			Assertion.raiseExceptionAndStop(new Exception("Actual Workflow type in response not matches with expected"),pNode);
		}
	}

    public Response fetchValidModelsForChannel(WorkflowAPIBean wb) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int user_id=DatabaseUtil.getUserID("nba_automation");

		createChannel_Insight_ScoreReqObj(wb,workSpaceID,user_id,null);
		ActionChannelInsightRequest Channel_Insight_ScoreReqObj=createChannel_Insight_ScoreReqObj(wb, workSpaceID, user_id,null);
		RequestSpecification requestObj=getRequestSpecification("/nbadesigner-backend/nba/modelName",
				null,Channel_Insight_ScoreReqObj);
		requestObj.log().all();
		Response resp=requestObj.when()
				.post();
		return resp;
    }

	private ActionChannelInsightRequest createChannel_Insight_ScoreReqObj(WorkflowAPIBean wb, int workSpaceID, int user_id,String modelName) {
		ActionChannelInsightRequest ActionChannelInsightRequestObj = new ActionChannelInsightRequest();
		ActionChannelInsightRequestObj.setChannel(wb.channelName);
		if(workSpaceID!=0)
		ActionChannelInsightRequestObj.setWorkspaceId(workSpaceID);
		if(user_id !=0)
		ActionChannelInsightRequestObj.setUserId(user_id);
		if(modelName!=null)
			ActionChannelInsightRequestObj.setModel_name(modelName);

		return ActionChannelInsightRequestObj;


	}

	public void validateModelsForChannel(Response resp, WorkflowAPIBean wb) throws IOException {
		logToConsole.info("########  The received api response is: " + resp.prettyPrint());
		ResponseSpecification genericResp = initAPIResp();
		Channel_Insight_Score respChannel_Insight_Score = resp.then().spec(genericResp).extract().as(Channel_Insight_Score.class);
		int actualStatus=respChannel_Insight_Score.getResponse().getStatus();

		if (actualStatus!=StatusCode.CODE_200.getCode()) {
			Assertion.raiseExceptionAndStop(new Exception("Response status code in model api not matching expected} : "
					+ StatusCode.CODE_200.getCode() + "and {actual response status code is} : " + actualStatus),pNode);
		} else {
			pNode.pass("Response status code in model api matching expected");
		}

		String modelArr[] =wb.expectedModelsForChannel.split("\\|");
		List<String> modelList=Arrays.asList(modelArr);
		List<DataObject> dataList=respChannel_Insight_Score.getData().getDataPoints();
		int counter=0;

		for(int index=0;index<dataList.size();index++) {
			DataObject dataObj = dataList.get(index);
			counter++;
			if(dataObj.getModel_name()==null & counter<=3) {

				if ( dataObj.getCount() >= 0 ) {
					pNode.pass("Model score count is present in response");
				} else if ( dataObj.getCount() < 0 ) {
					pNode.fail("Incorrect Model score count present in response, even though model data exists for channel");
				} /*else if ( dataObj.getX() ) {
					pNode.fail("Incorrect Model score value (x) present in response, even though model data exists for channel");
				}*/
			}


			if (modelList.size() > 0 && dataObj.getModel_name()!=null) {
				if (modelList.contains(dataObj.getModel_name())) {
					pNode.pass("expected model name matches with expected");
				} else {
					pNode.fail("expected model name not matches with expected");
				}
			}
		}

		if(respChannel_Insight_Score.getData().getAvailableAudience()!=null && !respChannel_Insight_Score.getData().getAvailableAudience().isEmpty()){
			pNode.pass("Available audience is present in the response and it's value is: "+respChannel_Insight_Score.getData().getAvailableAudience());
		}else{
			Assertion.raiseExceptionAndStop(new Exception("Available audience is not present in the " +
					"response and it's value is: "+respChannel_Insight_Score.getData().getAvailableAudience()),pNode);
		}
	}

	public Response fetchScatterDataForChannelforGivenModel(WorkflowAPIBean wb,String modelName) {
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		ActionChannelInsightRequest Channel_Insight_ScoreReqObj=createChannel_Insight_ScoreReqObj(wb, 0, 0,modelName);
		RequestSpecification requestObj=getRequestSpecification("/nbadesigner-backend/nba/scatterData",
				Collections.singletonMap("workspace_id",workSpaceID),Channel_Insight_ScoreReqObj);
		requestObj.log().all();
		Response resp=requestObj.when()
				.post();
		return resp;
	}

	public Response callRulesGraphAPI(WorkflowAPIBean wb) {
		int userID = DatabaseUtil.getUserID("nba_automation");
		int workSpaceID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int kpi_id=DatabaseUtil.getKPiId(wb.kpi_name);
		int scenarioId=DatabaseUtil.getScenarioID(wb.scenarioName);

		Map<String,? super Object> queryMap= new HashMap<>();
		queryMap.put("workspaceId",workSpaceID);
		queryMap.put("userId",userID);

		RuleGraph ruleGraphObj = new RuleGraph();

		ruleGraphObj.setKpi_id(kpi_id);
		ruleGraphObj.setScenario_id(scenarioId);
		if(wb.prev_time_type!=null && !wb.prev_time_type.isEmpty()) {
			ruleGraphObj.setPrev_time_type(wb.prev_time_type);
		}
		ruleGraphObj.setType(wb.kpi_type);
		if(wb.time_range!=null && !wb.time_range.isEmpty()) {
			ruleGraphObj.setTime_range(Integer.valueOf(wb.time_range));
		}

		RequestSpecification requestObj=getRequestSpecification("/nbadesigner-backend/nba/model/" +
						"rule/rulesGraph",
				queryMap,ruleGraphObj);

		requestObj.log().uri();
		requestObj.log().body(true);

		Response resp=requestObj.when()
				.post();
		return resp;
	}


	public void validateRuleGraphResp(Response resp, WorkflowAPIBean wb) {
		double kpiOutputOnXAxisNumerical=0.0;
		String kpiOutputOnXAxisStr=null;

		Map<Object,Integer> customerKPIMap = new HashMap<>();
		Object kpiOutputOnXAxis;

		resp.prettyPrint();

		JSONObject graphRespObj= new JSONObject(resp.getBody().asString());
		JSONObject respObj=graphRespObj.getJSONObject("response");
		String actualType=respObj.getString("type");
		int actualStatus=respObj.getInt("status");
		String title=respObj.getString("title");
		String detail=respObj.getString("detail");
		String instance=respObj.getString("instance");
		JSONArray dataArr= graphRespObj.getJSONArray("data");

		try {
		if(actualType.equalsIgnoreCase("Created")){
			pNode.pass("Rule graph created as value of created attribute in response is: "
					+ actualType);
		}else {
			Assertion.raiseExceptionAndStop(new Exception(
					"Rule graph could not be created as value of created attribute in response is: "
							+ actualType), pNode);
		}

			if(actualStatus==StatusCode.CODE_200.getCode()){
				pNode.pass("Rule graph created as value of actualStatus attribute in response is: "
						+ actualStatus);
			}else {
				Assertion.raiseExceptionAndStop(new Exception(
						"Rule graph could not be created as value of actualStatus attribute in response is: "
								+ actualStatus), pNode);
			}

			if(title.equalsIgnoreCase("Completed")){
				pNode.pass("Rule graph created as value of title attribute in response is: "
						+ title);
			}else {
				Assertion.raiseExceptionAndStop(new Exception(
						"Rule graph could not be created as value of title attribute in response is: "
								+ title), pNode);
			}

			if(detail.equalsIgnoreCase("Records have been fetched")){
				pNode.pass("Rule graph created as value of detail attribute in response is: "
						+ detail);
			}else {
				Assertion.raiseExceptionAndStop(new Exception(
						"Rule graph could not be created as value of detail attribute in response is: "
								+ detail), pNode);
			}

			if(instance.equalsIgnoreCase("Records have been fetched")){
				pNode.pass("Rule graph created as value of instance attribute in response is: "
						+ instance);
			}else {
				Assertion.raiseExceptionAndStop(new Exception(
						"Rule graph could not be created as value of instance attribute in response is: "
								+ instance), pNode);
			}

			for(int index=0; index<=dataArr.length()-1;index++){
				int customerExpectedCount=0;
				String queryExecutionId;
				JSONObject dataIndividualObj=dataArr.getJSONObject(index);
				if(wb.kpi_type.equalsIgnoreCase("Categorical")){
					kpiOutputOnXAxis=dataIndividualObj.getString("x");
					System.out.println("kpiOutputOnXAxisStr is: "+kpiOutputOnXAxis);
					queryExecutionId= AthenaDataUtil.submitAthenaQuery(wb.customerCountQuery.replace("xAxisVariable","'"+kpiOutputOnXAxis+"'"));
				}else{
					kpiOutputOnXAxis=dataIndividualObj.getDouble("x");
					System.out.println("kpiOutputOnXAxisNumerical is: "+kpiOutputOnXAxis);
					queryExecutionId= AthenaDataUtil.submitAthenaQuery(wb.customerCountQuery.replace("xAxisVariable",String.valueOf(kpiOutputOnXAxis)));
				}
				List<Object[]> list=AthenaDataUtil.processResultRows(queryExecutionId);
				customerExpectedCount= Integer.parseInt((String) list.get(1)[0]);

				int customerActualCount=dataIndividualObj.getInt("count");
				System.out.println("customerActualCount is: "+customerActualCount);
				System.out.println("customerExpectedCount is: "+customerExpectedCount);

				if(customerActualCount!=customerExpectedCount){
					/*Assertion.raiseExceptionAndStop(new Exception("Actual customer count not matches with expected customer count for if numerical "
							+ kpiOutputOnXAxisNumerical + " else if string then " + kpiOutputOnXAxisStr), pNode);*/
					customerKPIMap.put(kpiOutputOnXAxis,customerActualCount);
					customerKPIMap.forEach((o, integer) -> {System.out.println("Key is:"+o); System.out.println("Value is: "+integer);});
				}

			}
			pNode.pass("Customer count matches for all different KPI's output");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}