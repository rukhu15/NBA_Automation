package tagManagement.apiPojo.apiResp.updateTag;

import tagManagement.apiPojo.apiResp.createTag.Data;
import tagManagement.apiPojo.apiResp.createTag.Response;

public class UpdateTagResp {

	private Response response;
	private Data data;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
