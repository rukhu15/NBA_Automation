package apiPojo.apiResp.audience;

import apiPojo.apiReq.audience.filter_expr;

public class DataGet {

	private int audience_id;
	private String is_template;
	private String audience_name;
	private String cust_type;
	private filter_expr filter_expr;
	private String is_active;
	private int created_by;
	private String is_deleted;
	private String created_timestamp;
	private int updated_by;
	private String updated_timestamp;
	private int copy_from;

	public int getAudience_id() {
		return audience_id;
	}

	public void setAudience_id(int audience_id) {
		this.audience_id = audience_id;
	}

	public String getIs_template() {
		return is_template;
	}

	public void setIs_template(String is_template) {
		this.is_template = is_template;
	}

	public String getAudience_name() {
		return audience_name;
	}

	public void setAudience_name(String audience_name) {
		this.audience_name = audience_name;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public filter_expr getFilter_expr() {
		return filter_expr;
	}

	public void setFilter_expr(filter_expr filter_expr) {
		this.filter_expr = filter_expr;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public int getCreated_by() {
		return created_by;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getCreated_timestamp() {
		return created_timestamp;
	}

	public void setCreated_timestamp(String created_timestamp) {
		this.created_timestamp = created_timestamp;
	}

	public int getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

	public String getUpdated_timestamp() {
		return updated_timestamp;
	}

	public void setUpdated_timestamp(String updated_timestamp) {
		this.updated_timestamp = updated_timestamp;
	}

	public int getCopy_from() {
		return copy_from;
	}

	public void setCopy_from(int copy_from) {
		this.copy_from = copy_from;
	}

	
}
