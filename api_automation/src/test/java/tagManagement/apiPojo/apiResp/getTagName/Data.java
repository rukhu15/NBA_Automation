package tagManagement.apiPojo.apiResp.getTagName;

import java.util.List;

public class Data {

	private List<RowData> rowData;
	private List<ColumnData> column_data;

	public List<RowData> getRowData() {
		return rowData;
	}

	public void setRowData(List<RowData> rowData) {
		this.rowData = rowData;
	}

	public List<ColumnData> getColumn_data() {
		return column_data;
	}

	public void setColumn_data(List<ColumnData> column_data) {
		this.column_data = column_data;
	}

}
