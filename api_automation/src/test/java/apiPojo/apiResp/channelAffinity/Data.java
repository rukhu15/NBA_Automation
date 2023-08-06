package apiPojo.apiResp.channelAffinity;

public class Data {

	private int channel_id;
	private String channel_name;
	private String db_value;
	private String model_type_x;
	private double affinity;
	private String model_type_y;
	private double impact;
	
	
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

	public String getDb_value() {
		return db_value;
	}

	public void setDb_value(String db_value) {
		this.db_value = db_value;
	}

	public String getModel_type_x() {
		return model_type_x;
	}

	public void setModel_type_x(String model_type_x) {
		this.model_type_x = model_type_x;
	}

	public double getAffinity() {
		return affinity;
	}

	public void setAffinity(double affinity) {
		this.affinity = affinity;
	}

	public String getModel_type_y() {
		return model_type_y;
	}

	public void setModel_type_y(String model_type_y) {
		this.model_type_y = model_type_y;
	}

	public double getImpact() {
		return impact;
	}

	public void setImpact(double impact) {
		this.impact = impact;
	}

}
