package apiTestScript;

import application.apiPageObjects.ModelAPI;
import com.aventstack.extentreports.ExtentTest;
import data.beans.api.WorkflowAPIBean;
import dataProvidersPackage.APIDP;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import scripts.TestInit;

/**
 * @author Prateek Sethi
 */
public class ModelAPI_Test extends TestInit {

    @Test(priority = 1, groups = {"api"}, enabled = true,dataProvider="createWorkflowAPI",
            dataProviderClass = APIDP.class)
    public void ModelAPI_TC_Create(WorkflowAPIBean wb) {
        ExtentTest t3 = pNode.createNode("ModelAPI_TC_Create", wb.testDescription);
        try {
            ModelAPI modelAPI=ModelAPI.init(t3);
            pNode.info("Going to send the API POST req for workflow creation name :  "+wb.workflow_name);
            Response resp=modelAPI.createWorkflowInScenario(wb);
            modelAPI.validateWorkflowInScenario(resp,wb);
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 2, groups = {"api"}, enabled = false,dataProvider="generateChannelActionScores",
            dataProviderClass = APIDP.class)
    public void ModelAPI_TC_ChannelActionScores(WorkflowAPIBean wb) {
        ExtentTest t3 = pNode.createNode("ModelAPI_TC_ChannelActionScores", wb.testDescription);
        try {
            ModelAPI modelAPI=ModelAPI.init(t3);
            pNode.info("Going to send the API POST req for action channel scores for given channels "
                    +wb.channelName);
            Response resp=modelAPI.fetchValidModelsForChannel(wb);
            modelAPI.validateModelsForChannel(resp,wb);
            String[] modelArr=wb.expectedModelsForChannel.split("\\|");
            for(String modelName:modelArr){
                resp=modelAPI.fetchScatterDataForChannelforGivenModel(wb,modelName);
                modelAPI.validateModelsForChannel(resp,wb);
            }
        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }

    @Test(priority = 3, groups = {"api"}, enabled = false,dataProvider="generateRulesGraph",
            dataProviderClass = APIDP.class)
    public void ModelAPI_TC_RulesGraphTest(WorkflowAPIBean wb) {
        ExtentTest t3 = pNode.createNode("ModelAPI_TC_RulesGraphTest", wb.testDescription);
        try {
            ModelAPI modelAPI=ModelAPI.init(t3);
            pNode.info("Going to send the API POST req for rules graph for given KPI "
                    +wb.kpi_name);
            Response resp=modelAPI.callRulesGraphAPI(wb);
            modelAPI.validateRuleGraphResp(resp,wb);

        } catch (Exception e) {
            markTestAsFailure(e, t3);
        }
    }
}
