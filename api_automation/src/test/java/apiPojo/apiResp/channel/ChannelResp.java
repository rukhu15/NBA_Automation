package apiPojo.apiResp.channel;

/**
 * @author Prateek Sethi
 */
public class ChannelResp {

    private Response response=null;
    private data data=null;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public apiPojo.apiResp.channel.data getData() {
        return data;
    }

    public void setData(apiPojo.apiResp.channel.data data) {
        this.data = data;
    }
}
