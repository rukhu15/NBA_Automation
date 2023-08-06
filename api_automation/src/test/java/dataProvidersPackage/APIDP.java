package dataProvidersPackage;

import data.beans.api.*;
import framework.utility.dataParam.Data;
import org.testng.annotations.DataProvider;

import java.util.Iterator;

/**
 * @author Prateek Sethi
 */
public class APIDP {
	@DataProvider(name = "createScenarioAPI")
	public static Iterator<Object[]> createScenarioAPI() {
		return Data.listOf(ScenarioAPIBean.class).from("createScenarioAPI");
	}

	@DataProvider(name = "updateScenarioAPI")
	public static Iterator<Object[]> updateScenarioAPI() {
		return Data.listOf(ScenarioAPIBean.class).from("updateScenarioAPI");
	}

	@DataProvider(name = "getScenarioAPI")
	public static Iterator<Object[]> getScenarioAPI() {
		return Data.listOf(ScenarioAPIBean.class).from("getScenarioAPI");
	}

	@DataProvider(name = "deleteScenarioAPI")
	public static Iterator<Object[]> deleteScenarioAPI() {
		return Data.listOf(ScenarioAPIBean.class).from("deleteScenarioAPI");
	}

	@DataProvider(name = "createDesignAPI")
	public static Iterator<Object[]> createDesignAPI() {
		return Data.listOf(DesignAPIBean.class).from("createDesignAPI");
	}

	@DataProvider(name = "addChannelsToScenario")
	public static Iterator<Object[]> addChannelsToScenario() {

		return Data.listOf(DesignAPIBean.class).from("addChannelsToScenario");
	}

	@DataProvider(name = "addContentKeyTopicsForScenario")
	public static Iterator<Object[]> addContentKeyTopicsForScenario() {
		return Data.listOf(DesignAPIBean.class).from("addContentKeyTopicsForScenario");
	}

	@DataProvider(name = "createWorkflowAPI")
	public static Iterator<Object[]> createWorkflowAPI() {
		return Data.listOf(WorkflowAPIBean.class).from("createWorkflowAPI");
	}

	@DataProvider(name = "getAdjudicateAPI")
	public static Iterator<Object[]> getAdjudicateAPI() {
		return Data.listOf(AdjudicateAPIBean.class).from("getAdjudicateAPI");
	}

	@DataProvider(name = "addAdjudicateChannel")
	public static Iterator<Object[]> addAdjudicateChannel() {
		return Data.listOf(AdjudicateAPIBean.class).from("addAdjudicateChannel");
	}

	@DataProvider(name = "addAdjClientChannel")
	public static Iterator<Object[]> addAdjClientChannel() {
		return Data.listOf(AdjudicateAPIBean.class).from("addAdjClientChannel");
	}

	@DataProvider(name = "addAdjudicateclient")
	public static Iterator<Object[]> addAdjudicateclient() {
		return Data.listOf(AdjudicateAPIBean.class).from("addAdjudicateclient");
	}

	@DataProvider(name = "activateScenario")
	public static Iterator<Object[]> activateScenario() {
		return Data.listOf(ExecuteScenarioBean.class).from("activateScenario");
	}

	@DataProvider(name = "executeScenario")
	public static Iterator<Object[]> executeScenario() {
		return Data.listOf(ExecuteScenarioBean.class).from("executeScenario");
	}

	@DataProvider(name = "getsearchChannel")
	public static Iterator<Object[]> getsearchChannel() {
		return Data.listOf(ChannelAPIBean.class).from("getsearchChannel");
	}

	@DataProvider(name = "getselectedChannellist")
	public static Iterator<Object[]> getselectedChannellist() {
		return Data.listOf(ChannelAPIBean.class).from("getselectedChannellist");
	}

	@DataProvider(name = "getAudienceAPI")
	public static Iterator<Object[]> getAudienceAPI() {
		return Data.listOf(DesignAPIBean.class).from("getAudienceAPI");
	}

	@DataProvider(name = "updateAudienceAPI")
	public static Iterator<Object[]> updateAudienceAPI() {
		return Data.listOf(DesignAPIBean.class).from("updateAudienceAPI");
	}

	@DataProvider(name = "deleteDesignAPI")
	public static Iterator<Object[]> deleteDesignAPI() {
		return Data.listOf(DesignAPIBean.class).from("deleteDesignAPI");
	}
	
	@DataProvider(name = "getChannelAffinityScore")
	public static Iterator<Object[]> getChannelAffinityScore() {
		return Data.listOf(ChannelAPIBean.class).from("getChannelAffinityScore");
	}

	@DataProvider(name="postFormulaCreate")
	public static Iterator<Object[]> postFormulaCreate(){
		return Data.listOf(KPIBuilderAPIBean.class).from("postFormulaCreate");
	}

	@DataProvider(name="postFormulaUpdate")
	public static Iterator<Object[]> postFormulaUpdate(){
		return Data.listOf(KPIBuilderAPIBean.class).from("postFormulaUpdate");
	}

	@DataProvider(name="generateChannelActionScores")
	public static Iterator<Object[]> generateChannelActionScores(){
		return Data.listOf(WorkflowAPIBean.class).from("generateChannelActionScores");
	}
	@DataProvider(name="generateRulesGraph")
	public static Iterator<Object[]> generateRulesGraph(){
		return Data.listOf(WorkflowAPIBean.class).from("generateRulesGraph");
	}

	@DataProvider(name = "getconfiguredconnections")
	public static Iterator<Object[]> getconfiguredconnections() {
		return Data.listOf(PublishAPIBean.class).from("getconfiguredconnections");
	}

	@DataProvider(name = "getshowconnector")
	public static Iterator<Object[]> getshowconnector() {
		return Data.listOf(PublishAPIBean.class).from("getshowconnector");
	}


	@DataProvider(name = "getarchiveConnection")
	public static Iterator<Object[]> getarchiveConnection() {
		return Data.listOf(PublishAPIBean.class).from("getarchiveConnection");
	}

	@DataProvider(name = "postveevaconnection")
	public static Iterator<Object[]> postveevaconnection() {
		return Data.listOf(PublishAPIBean.class).from("postveevaconnection");
	}
	@DataProvider(name = "postsfmcconnection")
	public static Iterator<Object[]> postsfmcconnection() {
		return Data.listOf(PublishAPIBean.class).from("postsfmcconnection");
	}

	@DataProvider(name = "postveevatestconnection")
	public static Iterator<Object[]> postveevatestconnection() {
		return Data.listOf(PublishAPIBean.class).from("postveevatestconnection");
	}
	@DataProvider(name = "postsfmctestconnection")
	public static Iterator<Object[]> postsfmctestconnection() {
		return Data.listOf(PublishAPIBean.class).from("postsfmctestconnection");
	}
}