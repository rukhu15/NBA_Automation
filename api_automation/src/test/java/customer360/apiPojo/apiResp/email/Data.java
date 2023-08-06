package customer360.apiPojo.apiResp.email;

public class Data {
	private String sendaverage;
	private String delaverage;
	private String openaverage;
	private String clickedaverage;

	private Chart rtechart;
	private Chart hoemailchart;

	public String getSendaverage() {
		return sendaverage;
	}

	public void setSendaverage(String sendaverage) {
		this.sendaverage = sendaverage;
	}

	public String getDelaverage() {
		return delaverage;
	}

	public void setDelaverage(String delaverage) {
		this.delaverage = delaverage;
	}

	public String getOpenaverage() {
		return openaverage;
	}

	public void setOpenaverage(String openaverage) {
		this.openaverage = openaverage;
	}

	public String getClickedaverage() {
		return clickedaverage;
	}

	public void setClickedaverage(String clickedaverage) {
		this.clickedaverage = clickedaverage;
	}

	public Chart getRtechart() {
		return rtechart;
	}

	public void setRtechart(Chart rtechart) {
		this.rtechart = rtechart;
	}

	public Chart getHoemailchart() {
		return hoemailchart;
	}

	public void setHoemailchart(Chart hoemailchart) {
		this.hoemailchart = hoemailchart;
	}

}
