package tagManagement.apiPojo.apiResp.createTag;

public class Data {
	private int tag_seq_id;
	private String tag_id;
	private String tag_name;
	private String tag_desc;
	private String is_active;
	private String is_deleted;
	private String created_by;
	private String created_timestamp;
	private String updated_by;
	private String updated_timestamp;
	private int nmspc;

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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_timestamp() {
		return created_timestamp;
	}

	public void setCreated_timestamp(String created_timestamp) {
		this.created_timestamp = created_timestamp;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getUpdated_timestamp() {
		return updated_timestamp;
	}

	public void setUpdated_timestamp(String updated_timestamp) {
		this.updated_timestamp = updated_timestamp;
	}

	public int getNmspc() {
		return nmspc;
	}

	public void setNmspc(int nmspc) {
		this.nmspc = nmspc;
	}
}
