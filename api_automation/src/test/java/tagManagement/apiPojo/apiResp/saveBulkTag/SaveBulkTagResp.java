package tagManagement.apiPojo.apiResp.saveBulkTag;


import java.util.List;

public class SaveBulkTagResp {

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
