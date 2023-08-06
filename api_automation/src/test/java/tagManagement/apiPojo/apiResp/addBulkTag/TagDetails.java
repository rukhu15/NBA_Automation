package tagManagement.apiPojo.apiResp.addBulkTag;

public class TagDetails {

	private int tag_seq_id;
	private String tag_name;
	private boolean is_active;

	public int getTag_seq_id() {
		return tag_seq_id;
	}

	public void setTag_seq_id(int tag_seq_id) {
		this.tag_seq_id = tag_seq_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

}
