package tagManagement.apiPojo.apiReq.addKeyTopics;


import java.util.List;

public class AddKeyTopicsReq {

    private int channel_id;

    private List<TagDetails> tag_details;




    public List<TagDetails> getTag_details() {
        return tag_details;
    }

    public void setTag_details(List<TagDetails> tag_details) {
        this.tag_details = tag_details;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }


}
