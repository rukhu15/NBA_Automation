package tagManagement.apiPojo.apiResp.addBulkTag;

import java.util.List;

public class AddBulkTagResp {

	private Response response;
	private List<Data> data;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

}
