package tagManagement.apiPojo.apiResp.getTagName;

public class RowData {

	private int tag_seq_id;
	private String tag_id;
	private String tag_name;
	private String tag_desc;
	private String updated_timestamp;
	private String actions;
	private Usage usage;

	public Usage getUsage() {
		return usage;
	}

	public void setUsage(Usage usage) {
		this.usage = usage;
	}

	public int getTag_seq_id() {
		return tag_seq_id;
	}

	public void setTag_seq_id(int tag_seq_id) {
		this.tag_seq_id = tag_seq_id;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String getTag_desc() {
		return tag_desc;
	}

	public void setTag_desc(String tag_desc) {
		this.tag_desc = tag_desc;
	}

	public String getUpdated_timestamp() {
		return updated_timestamp;
	}

	public void setUpdated_timestamp(String updated_timestamp) {
		this.updated_timestamp = updated_timestamp;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

}
