package apiPojo.apiResp.GetSearchChannel;

import java.util.List;

public class GetSearchChannelResponse {

	private Response response = null;
	private Data data = null;
	private List<Channels> channels;
	private List<Categories> Categories;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public List<Channels> getChannels() {
		return channels;
	}

	public void setChannels(List<Channels> channels) {
		this.channels = channels;
	}

	public List<Categories> getCategories() {
		return Categories;
	}

	public void setCategories(List<Categories> categories) {
		Categories = categories;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
