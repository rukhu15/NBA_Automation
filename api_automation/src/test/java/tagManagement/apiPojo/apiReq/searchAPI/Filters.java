package tagManagement.apiPojo.apiReq.searchAPI;

import java.util.List;

public class Filters {
	private List<Details> content_type_details;
	private List<Details> channel_details;
	private List<Details> key_topics_details;
	private List<Details> import_date_details;

	public List<Details> getContent_type_details() {
		return content_type_details;
	}

	public void setContent_type_details(List<Details> contentList) {
		this.content_type_details = contentList;
	}

	public List<Details> getChannel_details() {
		return channel_details;
	}

	public void setChannel_details(List<Details> importDateList) {
		this.channel_details = importDateList;
	}

	public List<Details> getKey_topics_details() {
		return key_topics_details;
	}

	public void setKey_topics_details(List<Details> key_topics_details) {
		this.key_topics_details = key_topics_details;
	}

	public List<Details> getImport_date_details() {
		return import_date_details;
	}

	public void setImport_date_details(List<Details> import_date_details) {
		this.import_date_details = import_date_details;
	}

}
