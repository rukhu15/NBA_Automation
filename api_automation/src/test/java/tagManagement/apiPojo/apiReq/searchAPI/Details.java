package tagManagement.apiPojo.apiReq.searchAPI;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class Details {
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int content_type_id;
	private String content_type_name;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int channel_id;
	private String channel_name;

	private String start_date;
	private String end_date;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int tag_seq_id;
	private String tag_name;

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

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(int channel_id) {
		this.channel_id = channel_id;
	}

	public String getChannel_name() {
		return channel_name;
	}

	public void setChannel_name(String channel_name) {
		this.channel_name = channel_name;
	}

	public int getContent_type_id() {
		return content_type_id;
	}

	public void setContent_type_id(int content_type_id) {
		this.content_type_id = content_type_id;
	}

	public String getContent_type_name() {
		return content_type_name;
	}

	public void setContent_type_name(String content_type_name) {
		this.content_type_name = content_type_name;
	}

}
