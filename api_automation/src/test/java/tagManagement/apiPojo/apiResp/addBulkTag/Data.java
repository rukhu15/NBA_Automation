package tagManagement.apiPojo.apiResp.addBulkTag;

import java.util.List;

public class Data {

	private int content_id;
	private String content_name;
	private List<TagDetails> tag_details;

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public String getContent_name() {
		return content_name;
	}

	public void setContent_name(String content_name) {
		this.content_name = content_name;
	}

	public List<TagDetails> getTag_details() {
		return tag_details;
	}

	public void setTag_details(List<TagDetails> tag_details) {
		this.tag_details = tag_details;
	}

}
