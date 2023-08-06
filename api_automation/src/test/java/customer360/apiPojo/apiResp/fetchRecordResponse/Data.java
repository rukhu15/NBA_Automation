package customer360.apiPojo.apiResp.fetchRecordResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

	private String customer_id;
	private String customer_name;
	private String npi;
	private String market_potential;
	private String specialty1;
	private String terr_name;
	private String brand_segment;
	private String rx_sales_segment;
	private String l4w_website_visits;
	private String last_interaction_date;
	private String call_plan_attainment;
	private String l4w_nrx;
	private String l13w_trx;
	private String r4w_nrx_growth;
	private String l13w_reach;
	private String total_actual_calls;
	private String total_planned_calls;
	private String l4w_emails_sent;
	private String sample_dropped;
	private String l13w_percent_trx_growth;
	private String r4w_percent_trx_share_change;
	private String curr_week_event_attended_status;
	private String segment_group;
	private String engagement_score;
	private String l13w_not_called;
	private String L4W_BRAND_TRx;
	private String PREV_4W_BRAND_TRx;

	@SerializedName("%_trx_growth_[l4w]")
	@Expose
	private String PRCNT_trx_growth_;

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public String getMarket_potential() {
		return market_potential;
	}

	public void setMarket_potential(String market_potential) {
		this.market_potential = market_potential;
	}

	public String getSpecialty1() {
		return specialty1;
	}

	public void setSpecialty1(String specialty1) {
		this.specialty1 = specialty1;
	}

	public String getTerr_name() {
		return terr_name;
	}

	public void setTerr_name(String terr_name) {
		this.terr_name = terr_name;
	}

	public String getBrand_segment() {
		return brand_segment;
	}

	public void setBrand_segment(String brand_segment) {
		this.brand_segment = brand_segment;
	}

	public String getRx_sales_segment() {
		return rx_sales_segment;
	}

	public void setRx_sales_segment(String rx_sales_segment) {
		this.rx_sales_segment = rx_sales_segment;
	}

	public String getL4w_website_visits() {
		return l4w_website_visits;
	}

	public void setL4w_website_visits(String l4w_website_visits) {
		this.l4w_website_visits = l4w_website_visits;
	}

	public String getLast_interaction_date() {
		return last_interaction_date;
	}

	public void setLast_interaction_date(String last_interaction_date) {
		this.last_interaction_date = last_interaction_date;
	}

	public String getCall_plan_attainment() {
		return call_plan_attainment;
	}

	public void setCall_plan_attainment(String call_plan_attainment) {
		this.call_plan_attainment = call_plan_attainment;
	}

	public String getL4w_nrx() {
		return l4w_nrx;
	}

	public void setL4w_nrx(String l4w_nrx) {
		this.l4w_nrx = l4w_nrx;
	}

	public String getL13w_trx() {
		return l13w_trx;
	}

	public void setL13w_trx(String l13w_trx) {
		this.l13w_trx = l13w_trx;
	}

	public String getR4w_nrx_growth() {
		return r4w_nrx_growth;
	}

	public void setR4w_nrx_growth(String r4w_nrx_growth) {
		this.r4w_nrx_growth = r4w_nrx_growth;
	}

	public String getL13w_reach() {
		return l13w_reach;
	}

	public void setL13w_reach(String l13w_reach) {
		this.l13w_reach = l13w_reach;
	}

	public String getTotal_actual_calls() {
		return total_actual_calls;
	}

	public void setTotal_actual_calls(String total_actual_calls) {
		this.total_actual_calls = total_actual_calls;
	}

	public String getTotal_planned_calls() {
		return total_planned_calls;
	}

	public void setTotal_planned_calls(String total_planned_calls) {
		this.total_planned_calls = total_planned_calls;
	}

	public String getL4w_emails_sent() {
		return l4w_emails_sent;
	}

	public void setL4w_emails_sent(String l4w_emails_sent) {
		this.l4w_emails_sent = l4w_emails_sent;
	}

	public String getSample_dropped() {
		return sample_dropped;
	}

	public void setSample_dropped(String sample_dropped) {
		this.sample_dropped = sample_dropped;
	}

	public String getL13w_percent_trx_growth() {
		return l13w_percent_trx_growth;
	}

	public void setL13w_percent_trx_growth(String l13w_percent_trx_growth) {
		this.l13w_percent_trx_growth = l13w_percent_trx_growth;
	}

	public String getR4w_percent_trx_share_change() {
		return r4w_percent_trx_share_change;
	}

	public void setR4w_percent_trx_share_change(String r4w_percent_trx_share_change) {
		this.r4w_percent_trx_share_change = r4w_percent_trx_share_change;
	}

	public String getCurr_week_event_attended_status() {
		return curr_week_event_attended_status;
	}

	public void setCurr_week_event_attended_status(String curr_week_event_attended_status) {
		this.curr_week_event_attended_status = curr_week_event_attended_status;
	}

	public String getSegment_group() {
		return segment_group;
	}

	public void setSegment_group(String segment_group) {
		this.segment_group = segment_group;
	}

	public String getEngagement_score() {
		return engagement_score;
	}

	public void setEngagement_score(String engagement_score) {
		this.engagement_score = engagement_score;
	}

	public String getL13w_not_called() {
		return l13w_not_called;
	}

	public void setL13w_not_called(String l13w_not_called) {
		this.l13w_not_called = l13w_not_called;
	}

	public String getL4W_BRAND_TRx() {
		return L4W_BRAND_TRx;
	}

	public void setL4W_BRAND_TRx(String l4w_BRAND_TRx) {
		L4W_BRAND_TRx = l4w_BRAND_TRx;
	}

	public String getPREV_4W_BRAND_TRx() {
		return PREV_4W_BRAND_TRx;
	}

	public void setPREV_4W_BRAND_TRx(String pREV_4W_BRAND_TRx) {
		PREV_4W_BRAND_TRx = pREV_4W_BRAND_TRx;
	}

	public String getPRCNT_trx_growth_() {
		return PRCNT_trx_growth_;
	}

	public void setPRCNT_trx_growth_(String pRCNT_trx_growth_) {
		PRCNT_trx_growth_ = pRCNT_trx_growth_;
	}

}
