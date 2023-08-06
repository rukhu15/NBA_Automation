package apiPojo.apiResp.GetConfiguredAPI;

import apiPojo.apiResp.PublishResponse.Response;


import java.util.List;

public class RespConfiguredConnection {

    private Response response = null;
    private List<Data> data = null;

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
