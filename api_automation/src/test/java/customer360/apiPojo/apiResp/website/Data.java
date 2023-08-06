package customer360.apiPojo.apiResp.website;

import java.util.List;

public class Data {
	private String visits;
	private String conversion;
	private String cvr;
	private List<String> visitsData;
	private List<String> visitsXaxis;
	private List<String> conversionRateData;
	private List<String> conversionRateXaxis;

	public String getVisits() {
		return visits;
	}

	public void setVisits(String visits) {
		this.visits = visits;
	}

	public String getConversion() {
		return conversion;
	}

	public void setConversion(String conversion) {
		this.conversion = conversion;
	}

	public String getCvr() {
		return cvr;
	}

	public void setCvr(String cvr) {
		this.cvr = cvr;
	}

	public List<String> getVisitsData() {
		return visitsData;
	}

	public void setVisitsData(List<String> visitsData) {
		this.visitsData = visitsData;
	}

	public List<String> getVisitsXaxis() {
		return visitsXaxis;
	}

	public void setVisitsXaxis(List<String> visitsXaxis) {
		this.visitsXaxis = visitsXaxis;
	}

	public List<String> getConversionRateData() {
		return conversionRateData;
	}

	public void setConversionRateData(List<String> conversionRateData) {
		this.conversionRateData = conversionRateData;
	}

	public List<String> getConversionRateXaxis() {
		return conversionRateXaxis;
	}

	public void setConversionRateXaxis(List<String> conversionRateXaxis) {
		this.conversionRateXaxis = conversionRateXaxis;
	}

}
