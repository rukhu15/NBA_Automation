package customer360.apiPojo.apiResp.fetchRecordResponse;

import java.util.List;

public class DataInDefault {
	private List<Data> data;
	private List<Column> column;

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Column> getColumn() {
		return column;
	}

	public void setColumn(List<Column> column) {
		this.column = column;
	}

}
