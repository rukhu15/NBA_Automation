package apiPojo.apiResp.Getshowconnector;

import apiPojo.apiResp.PublishResponse.Response;

import java.util.List;

public class Resshowconnector {

    private List<Data> Data;
    private Response response;
    public List<apiPojo.apiResp.Getshowconnector.Data> getData() {
        return Data;
    }

    public void setData(List<apiPojo.apiResp.Getshowconnector.Data> data) {
        Data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

   ;

}
