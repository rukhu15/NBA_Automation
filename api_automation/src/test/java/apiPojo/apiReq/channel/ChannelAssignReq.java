package apiPojo.apiReq.channel;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelAssignReq {

    private List<Integer> channel;
    private List<Integer> channel_id;

    public List<Integer> getChannel() {
        return channel;
    }

    public void setChannel(List<Integer> channel) {
        this.channel = channel;
    }

    public List<Integer> getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(List<Integer> channel_id) {
        this.channel_id = channel_id;
    }
}
