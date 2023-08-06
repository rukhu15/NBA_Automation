package apiPojo.apiReq.workflow;

import apiPojo.apiResp.Actions.Data;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Channel_Insight_Score {

    ChannelInsightResponse response;

    public ChannelInsightResponse getResponse() {
        return response;
    }

    public void setResponse(ChannelInsightResponse response) {
        this.response = response;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    Data data;



}