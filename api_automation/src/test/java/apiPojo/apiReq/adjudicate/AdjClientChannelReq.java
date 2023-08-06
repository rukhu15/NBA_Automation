package apiPojo.apiReq.adjudicate;

public class AdjClientChannelReq {

	private int channel_id;
	private int max_sug_limit;
	private int min_interval_days;

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
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
