package customer360.apiPojo.apiResp.affiliatedAccountsGraph;

import java.util.List;

public class Data {

	private List<ColumnList> columnList;
	private List<RowData> rowData;
	
	public List<ColumnList> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ColumnList> columnList) {
		this.columnList = columnList;
	}
	public List<RowData> getRowData() {
		return rowData;
	}
	public void setRowData(List<RowData> rowData) {
		this.rowData = rowData;
	}
}
