package tagManagement.apiPojo.apiReq.addKeyTopics;

public class TagDetails {

    private int tag_seq_id;

    private boolean is_active;

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean  is_active) {
        this.is_active = is_active;
    }

    public int getTag_seq_id() {
        return tag_seq_id;
    }

    public void setTag_seq_id(int tag_seq_id) {
        this.tag_seq_id = tag_seq_id;
    }
}
