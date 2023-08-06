package apiPojo.apiRes.adjudicateGET;

public class AdjudicateResp {

	private int adj_hcp_id;
	private String adj_hcp_name;
	private String adj_hcp_desc;
	private int scenario_id;
	private int wrkspc_id;
	private int max_sug_limit;
	private int min_interval_days;
	private int adj_channel_id;
	private int scenario;
	private int channel_name;
	private String adj_channel_desc;
	private int max_limit;
	private String is_enabled;
	private String created_by;
	//private String updated_by;
	private int channel_id;

	public int getAdj_channel_id() {
		return adj_channel_id;
	}

	public void setAdj_channel_id(int adj_channel_id) {
		this.adj_channel_id = adj_channel_id;
	}

	public int getScenario() {
		return scenario;
	}

	public void setScenario(int scenario) {
		this.scenario = scenario;
	}

	public int getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(int channel_name) {
		this.channel_name = channel_name;
	}

	public String getAdj_channel_desc() {
		return adj_channel_desc;
	}

	public void setAdj_channel_desc(String adj_channel_desc) {
		this.adj_channel_desc = adj_channel_desc;
	}

	public int getMax_limit() {
		return max_limit;
	}

	public void setMax_limit(int max_limit) {
		this.max_limit = max_limit;
	}

	public String getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(String is_enabled) {
		this.is_enabled = is_enabled;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}

	public int getAdj_hcp_id() {
		return adj_hcp_id;
	}

	public void setAdj_hcp_id(int adj_hcp_id) {
		this.adj_hcp_id = adj_hcp_id;
	}

	public String getAdj_hcp_name() {
		return adj_hcp_name;
	}

	public void setAdj_hcp_name(String adj_hcp_name) {
		this.adj_hcp_name = adj_hcp_name;
	}

	public String getAdj_hcp_desc() {
		return adj_hcp_desc;
	}

	public void setAdj_hcp_desc(String adj_hcp_desc) {
		this.adj_hcp_desc = adj_hcp_desc;
	}

	public int getScenario_id() {
		return scenario_id;
	}

	public void setScenario_id(int scenario_id) {
		this.scenario_id = scenario_id;
	}

	public int getWrkspc_id() {
		return wrkspc_id;
	}

	public void setWrkspc_id(int wrkspc_id) {
		this.wrkspc_id = wrkspc_id;
	}

	public int getMax_sug_limit() {
		return max_sug_limit;
	}

	public void setMax_sug_limit(int max_sug_limit) {
		this.max_sug_limit = max_sug_limit;
	}

	public int getMin_interval_days() {
		return min_interval_days;
	}

	public void setMin_interval_days(int min_interval_days) {
		this.min_interval_days = min_interval_days;
	}

}
