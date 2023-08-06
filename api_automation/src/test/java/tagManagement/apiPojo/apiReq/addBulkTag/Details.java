package tagManagement.apiPojo.apiReq.addBulkTag;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Details {

	private int channel_id;
	private int content_id;
	private int tag_seq_id;
	private String is_active;

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public int getTag_seq_id() {
		return tag_seq_id;
	}

	public void setTag_seq_id(int tag_seq_id) {
		this.tag_seq_id = tag_seq_id;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

}
