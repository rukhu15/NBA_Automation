package customer360.apiPojo.apiResp.channelInteraction;

import java.util.List;

public class Data {

	private List<GetData> data;
	private int count;

	public List<GetData> getData() {
		return data;
	}

	public void setData(List<GetData> data) {
		this.data = data;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
