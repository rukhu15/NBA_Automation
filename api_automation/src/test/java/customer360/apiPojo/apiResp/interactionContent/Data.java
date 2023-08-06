package customer360.apiPojo.apiResp.interactionContent;

import java.util.List;

public class Data {

	private List<GetData> data;
	private String name;
	private String showInLegend;

	public List<GetData> getData() {
		return data;
	}

	public void setData(List<GetData> data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowInLegend() {
		return showInLegend;
	}

	public void setShowInLegend(String showInLegend) {
		this.showInLegend = showInLegend;
	}

}
