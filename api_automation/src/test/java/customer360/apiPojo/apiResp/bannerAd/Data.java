package customer360.apiPojo.apiResp.bannerAd;

import java.util.List;

public class Data {
	private String impressions;
	private String clicks;
	private String ctr;
	private List<String> impressionsData;
	private List<String> impressionsXaxis;
	private List<String> ctrData;
	private List<String> ctrXaxis;

	public List<String> getCtrXaxis() {
		return ctrXaxis;
	}

	public void setCtrXaxis(List<String> ctrXaxis) {
		this.ctrXaxis = ctrXaxis;
	}

	public String getImpressions() {
		return impressions;
	}

	public void setImpressions(String impressions) {
		this.impressions = impressions;
	}

	public String getClicks() {
		return clicks;
	}

	public void setClicks(String clicks) {
		this.clicks = clicks;
	}

	public String getCtr() {
		return ctr;
	}

	public void setCtr(String ctr) {
		this.ctr = ctr;
	}

	public List<String> getImpressionsData() {
		return impressionsData;
	}

	public void setImpressionsData(List<String> impressionsData) {
		this.impressionsData = impressionsData;
	}

	public List<String> getImpressionsXaxis() {
		return impressionsXaxis;
	}

	public void setImpressionsXaxis(List<String> impressionsXaxis) {
		this.impressionsXaxis = impressionsXaxis;
	}

	public List<String> getCtrData() {
		return ctrData;
	}

	public void setCtrData(List<String> ctrData) {
		this.ctrData = ctrData;
	}
}
