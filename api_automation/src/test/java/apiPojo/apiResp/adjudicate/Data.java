package apiPojo.apiResp.adjudicate;

public class Data {

	private int scenario_id;
	private int channel_id;
	private int wrkspc_id;
	private int max_sug_limit;
	private int min_interval_days;
	private String is_enabled;
	private int created_by;
	private int updated_by;
	private String created_timestamp;
	private String updated_timestamp;
	private int adj_hcp_channel_id;
	private int scrn_config_id;

	public int getScenario_id() {
		return scenario_id;
	}

	public void setScenario_id(int scenario_id) {
		this.scenario_id = scenario_id;
	}

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
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

	public String getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(String is_enabled) {
		this.is_enabled = is_enabled;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public int getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

	public String getCreated_timestamp() {
		return created_timestamp;
	}

	public void setCreated_timestamp(String created_timestamp) {
		this.created_timestamp = created_timestamp;
	}

	public String getUpdated_timestamp() {
		return updated_timestamp;
	}

	public void setUpdated_timestamp(String updated_timestamp) {
		this.updated_timestamp = updated_timestamp;
	}

	public int getAdj_hcp_channel_id() {
		return adj_hcp_channel_id;
	}

	public void setAdj_hcp_channel_id(int adj_hcp_channel_id) {
		this.adj_hcp_channel_id = adj_hcp_channel_id;
	}

	public int getScrn_config_id() {
		return scrn_config_id;
	}

	public void setScrn_config_id(int scrn_config_id) {
		this.scrn_config_id = scrn_config_id;
	}

}
