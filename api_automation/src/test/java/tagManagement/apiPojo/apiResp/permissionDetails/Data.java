package tagManagement.apiPojo.apiResp.permissionDetails;

import java.util.List;

public class Data {
	private List<BrandDetails> brand_details;
	private List<ChannelDetails> channel_details;

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
