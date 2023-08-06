package customer360.apiPojo.apiResp.fetchRecordResponse;

public class Column {
	private String field;
	private String headerName;
	private String show;
	private String locked;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}
}
