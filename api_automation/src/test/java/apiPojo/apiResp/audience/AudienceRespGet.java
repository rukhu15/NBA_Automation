package apiPojo.apiResp.audience;

import java.util.List;

public class AudienceRespGet {

	private Response response = null;
	private List<DataGet> data = null;

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public List<DataGet> getData() {
		return data;
	}

	public void setData(List<DataGet> data) {
		this.data = data;
	}

}
