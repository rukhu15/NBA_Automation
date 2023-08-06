package tagManagement.apiPojo.apiReq.updateTag;

import java.util.List;

public class UpdateTagReq {

	private String desc_change;
	private String tag_name;
	private String tag_desc;
	private List<BrandDetails> brand_details;
	private List<ChannelDetails> channel_details;

	public String getDesc_change() {
		return desc_change;
	}

	public void setDesc_change(String desc_change) {
		this.desc_change = desc_change;
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

	public List<BrandDetails> getBrand_details() {
		return brand_details;
	}

	public void setBrand_details(List<BrandDetails> brand_details) {
		this.brand_details = brand_details;
	}

	public List<ChannelDetails> getChannel_details() {
		return channel_details;
	}

	public void setChannel_details(List<ChannelDetails> channel_details) {
		this.channel_details = channel_details;
	}

}
