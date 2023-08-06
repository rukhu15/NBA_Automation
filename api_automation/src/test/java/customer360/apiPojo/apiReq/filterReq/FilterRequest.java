package customer360.apiPojo.apiReq.filterReq;

public class FilterRequest {
	private String filter_name = null;
	private String is_enabled = null;
	private Filter_Value filter_value = null;

	public Filter_Value getFilter_value() {
		return filter_value;
	}

	public void setFilter_value(Filter_Value filter_value) {
		this.filter_value = filter_value;
	}

	public String getFilter_name() {
		return filter_name;
	}

	public void setFilter_name(String filter_name) {
		this.filter_name = filter_name;
	}

	public String getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(String is_enabled) {
		this.is_enabled = is_enabled;
	}
}
