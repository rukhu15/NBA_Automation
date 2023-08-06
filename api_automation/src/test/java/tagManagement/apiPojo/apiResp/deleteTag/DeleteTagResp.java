package tagManagement.apiPojo.apiResp.deleteTag;

import tagManagement.apiPojo.apiResp.createTag.Data;
import tagManagement.apiPojo.apiResp.createTag.Response;

public class DeleteTagResp {

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
