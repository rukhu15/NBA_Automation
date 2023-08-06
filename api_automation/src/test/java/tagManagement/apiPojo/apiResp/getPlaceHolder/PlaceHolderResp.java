package tagManagement.apiPojo.apiResp.getPlaceHolder;

import java.util.List;

public class PlaceHolderResp {
    private Response response;

    private List<Data> data;

    public Response getResponse () {return response;}

    public void setResponse(Response response) {this.response = response;}

    public List<Data> getData() {return data;}

    public void setData(List<Data> data) {this.data = data;}
}



