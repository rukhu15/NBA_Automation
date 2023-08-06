package tagManagement.apiPojo.apiReq.saveBulkTag;

import java.util.List;

public class ContentDetails {

    private int content_id;
    private List<TagDetails> tag_details;

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public List<TagDetails> getTag_details() {
        return tag_details;
    }

    public void setTag_details(List<TagDetails> tag_details) {
        this.tag_details = tag_details;
    }
}
