package tagManagement.apiPojo.apiReq.addBulkTag;

import java.util.List;

public class BulkTagReq {

	private List<Details> content_details;
	private List<Details> tag_details;

	public List<Details> getContent_details() {
		return content_details;
	}

	public void setContent_details(List<Details> content_details) {
		this.content_details = content_details;
	}

	public List<Details> getTag_details() {
		return tag_details;
	}

	public void setTag_details(List<Details> tag_details) {
		this.tag_details = tag_details;
	}
}
