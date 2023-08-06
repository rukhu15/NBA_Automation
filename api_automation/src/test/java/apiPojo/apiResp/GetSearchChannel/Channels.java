package apiPojo.apiResp.GetSearchChannel;

public class Channels {
	
	private int channel_id;
	private String channel_name;
	private String channel_desc;
	private String db_value;
	
	
	public int getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}
	public String getChannel_name() {
		return channel_name;
	}
	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}
	public String getChannel_desc() {
		return channel_desc;
	}
	public void setChannel_desc(String channel_desc) {
		this.channel_desc = channel_desc;
	}
	public String getDb_value() {
		return db_value;
	}
	public void setDb_value(String db_value) {
		this.db_value = db_value;
	}

}
