package apiPojo.apiReq.scenarioExecution;

/**
 * @author Avinash Gupta
 */
public class Params {
	private String db_name;
	private String wide_base_table;
	private String forPublishHistTable;
	private String client_ref;
	private String aud_where_clause;
	private String trigger_where_clause;
	private String rule_where_clause;
	private int scenario_id;
	private int workflow_count;
	private String workflowIDs;
	private String channelIDNameList;
	private String action_channel_details;
	private String adjudicate_channel;
	private String adjudicate_client;
	private String adjudicate_clientChannel;

	public String getDb_name() {
		return db_name;
	}

	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}

	public String getWide_base_table() {
		return wide_base_table;
	}

	public void setWide_base_table(String wide_base_table) {
		this.wide_base_table = wide_base_table;
	}

	public String getForPublishHistTable() {
		return forPublishHistTable;
	}

	public void setForPublishHistTable(String forPublishHistTable) {
		this.forPublishHistTable = forPublishHistTable;
	}

	public String getClient_ref() {
		return client_ref;
	}

	public void setClient_ref(String client_ref) {
		this.client_ref = client_ref;
	}

	public String getAud_where_clause() {
		return aud_where_clause;
	}

	public void setAud_where_clause(String aud_where_clause) {
		this.aud_where_clause = aud_where_clause;
	}

	public int getScenario_id() {
		return scenario_id;
	}

	public void setScenario_id(int scenario_id) {
		this.scenario_id = scenario_id;
	}

	public int getWorkflow_count() {
		return workflow_count;
	}

	public void setWorkflow_count(int workflow_count) {
		this.workflow_count = workflow_count;
	}

	public String getTrigger_where_clause() {
		return trigger_where_clause;
	}

	public void setTrigger_where_clause(String trigger_where_clause) {
		this.trigger_where_clause = trigger_where_clause;
	}

	public String getRule_where_clause() {
		return rule_where_clause;
	}

	public void setRule_where_clause(String rule_where_clause) {
		this.rule_where_clause = rule_where_clause;
	}

	public String getWorkflowIDs() {
		return workflowIDs;
	}

	public void setWorkflowIDs(String workflowIDs) {
		this.workflowIDs = workflowIDs;
	}

	public String getChannelIDNameList() {
		return channelIDNameList;
	}

	public void setChannelIDNameList(String channelIDNameList) {
		this.channelIDNameList = channelIDNameList;
	}

	public String getAction_channel_details() {
		return action_channel_details;
	}

	public void setAction_channel_details(String action_channel_details) {
		this.action_channel_details = action_channel_details;
	}

	public String getAdjudicate_channel() {
		return adjudicate_channel;
	}

	public void setAdjudicate_channel(String adjudicate_channel) {
		this.adjudicate_channel = adjudicate_channel;
	}

	public String getAdjudicate_client() {
		return adjudicate_client;
	}

	public void setAdjudicate_client(String adjudicate_client) {
		this.adjudicate_client = adjudicate_client;
	}

	public String getAdjudicate_clientChannel() {
		return adjudicate_clientChannel;
	}

	public void setAdjudicate_clientChannel(String adjudicate_clientChannel) {
		this.adjudicate_clientChannel = adjudicate_clientChannel;
	}
}