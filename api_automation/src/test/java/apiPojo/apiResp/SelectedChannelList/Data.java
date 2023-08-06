package apiPojo.apiResp.SelectedChannelList;

public class Data {

	private int scenario_id;
	private int channel_id;
	private int key;
    private String is_active;
	private int channel_category_id;
	private String channel_category_name;
	private String channel_name;
    private String db_value;
	
	
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

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public int getChannel_category_id() {
		return channel_category_id;
	}

	public void setChannel_category_id(int channel_category_id) {
		this.channel_category_id = channel_category_id;
	}

	public String getChannel_category_name() {
		return channel_category_name;
	}

	public void setChannel_category_name(String channel_category_name) {
		this.channel_category_name = channel_category_name;
	}

	public String getDb_value() {
		return db_value;
	}

	public void setDb_value(String db_value) {
		this.db_value = db_value;
	}

}
