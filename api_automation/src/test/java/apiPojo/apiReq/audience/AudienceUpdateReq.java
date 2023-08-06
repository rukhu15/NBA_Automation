package apiPojo.apiReq.audience;

public class AudienceUpdateReq {

	private int audience_id;
	private String is_template;
	private String audience_name;
	private String cust_type;
	private filter_expr filter_expr;
	private String is_active;
	private String is_deleted;
	private String action_flag;
	private int wrkspc_id;

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

	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

	public String getAction_flag() {
		return action_flag;
	}

	public void setAction_flag(String action_flag) {
		this.action_flag = action_flag;
	}

	public int getWrkspc_id() {
		return wrkspc_id;
	}

	public void setWrkspc_id(int wrkspc_id) {
		this.wrkspc_id = wrkspc_id;
	}

}
