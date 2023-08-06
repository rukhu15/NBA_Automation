package customer360.apiPojo.apiResp.affiliatedAccountsGraph;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RowData {

	private String ACCOUNT_NAME;
	private String ACCOUNT_ADDRESS;
	private String AFFILIATION;
	private int NON_WRITER_COUNT;
	private int WRITER_COUNT;
	private String YEARLY_REVENUE;
	private String CURR_YEAR_UNITS;
	private String PREV_YEAR_UNITS;

	@SerializedName("YoY_%_Change")
	@Expose
	private double YoY_Pct_Change;

	public String getACCOUNT_NAME() {
		return ACCOUNT_NAME;
	}

	public void setACCOUNT_NAME(String aCCOUNT_NAME) {
		ACCOUNT_NAME = aCCOUNT_NAME;
	}

	public String getACCOUNT_ADDRESS() {
		return ACCOUNT_ADDRESS;
	}

	public void setACCOUNT_ADDRESS(String aCCOUNT_ADDRESS) {
		ACCOUNT_ADDRESS = aCCOUNT_ADDRESS;
	}

	public String getAFFILIATION() {
		return AFFILIATION;
	}

	public void setAFFILIATION(String aFFILIATION) {
		AFFILIATION = aFFILIATION;
	}

	public int getNON_WRITER_COUNT() {
		return NON_WRITER_COUNT;
	}

	public void setNON_WRITER_COUNT(int nON_WRITER_COUNT) {
		NON_WRITER_COUNT = nON_WRITER_COUNT;
	}

	public int getWRITER_COUNT() {
		return WRITER_COUNT;
	}

	public void setWRITER_COUNT(int wRITER_COUNT) {
		WRITER_COUNT = wRITER_COUNT;
	}

	public String getYEARLY_REVENUE() {
		return YEARLY_REVENUE;
	}

	public void setYEARLY_REVENUE(String yEARLY_REVENUE) {
		YEARLY_REVENUE = yEARLY_REVENUE;
	}

	public String getCURR_YEAR_UNITS() {
		return CURR_YEAR_UNITS;
	}

	public void setCURR_YEAR_UNITS(String cURR_YEAR_UNITS) {
		CURR_YEAR_UNITS = cURR_YEAR_UNITS;
	}

	public String getPREV_YEAR_UNITS() {
		return PREV_YEAR_UNITS;
	}

	public void setPREV_YEAR_UNITS(String pREV_YEAR_UNITS) {
		PREV_YEAR_UNITS = pREV_YEAR_UNITS;
	}

	public double getYoY_Pct_Change() {
		return YoY_Pct_Change;
	}

	public void setYoY_Pct_Change(double yoY_Pct_Change) {
		YoY_Pct_Change = yoY_Pct_Change;
	}

}
