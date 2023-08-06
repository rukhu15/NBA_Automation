package apiPojo.apiResp.audience;

/**
 * @author Prateek Sethi
 */
public class AudienceResp {
    private Response response =null;
    private Data data=null;

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
