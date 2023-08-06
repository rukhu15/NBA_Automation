package apiPojo.apiReq.workflow;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionChannelInsightRequest {
    String channel;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int workspaceId;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int userId;
    String model_name;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }


}
