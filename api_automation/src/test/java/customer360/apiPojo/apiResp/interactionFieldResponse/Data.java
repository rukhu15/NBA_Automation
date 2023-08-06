package customer360.apiPojo.apiResp.interactionFieldResponse;

import java.util.List;

public class Data {

	private List<GetData> data;
	private int accepted;
	private int dismissed;
	private int ignored;
	private int actioned;

	public List<GetData> getData() {
		return data;
	}

	public void setData(List<GetData> data) {
		this.data = data;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public int getDismissed() {
		return dismissed;
	}

	public void setDismissed(int dismissed) {
		this.dismissed = dismissed;
	}

	public int getIgnored() {
		return ignored;
	}

	public void setIgnored(int ignored) {
		this.ignored = ignored;
	}

	public int getActioned() {
		return actioned;
	}

	public void setActioned(int actioned) {
		this.actioned = actioned;
	}
}
