package customer360.apiPojo.apiResp.performanceSummary;

import java.util.List;

public class Data {

	private String sales;
	private String prevSales;
	private String perChange;
	private List<String> xAxis;
	private List<String> data;

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getPrevSales() {
		return prevSales;
	}

	public void setPrevSales(String prevSales) {
		this.prevSales = prevSales;
	}

	public String getPerChange() {
		return perChange;
	}

	public void setPerChange(String perChange) {
		this.perChange = perChange;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}
}
