package tagManagement.apiPojo.apiReq.saveBulkTag;

import java.util.List;

public class SaveBulkTagReq {

    private List<ContentDetails> content_details;

    public List<ContentDetails> getContent_details() {
        return content_details;
    }

    public void setContent_details(List<ContentDetails> content_details) {
        this.content_details = content_details;
    }
}
