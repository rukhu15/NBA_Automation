package apiPojo.apiReq.workflow;

/**
 * @author Prateek Sethi
 */
public class Actions {
    int channel_id;
    int sequence_id;
    String schedule_type;

    int schedule_offset;
    int action_priority_key;
    boolean override_automated_scoring;
    boolean override_key_topics;
    String key_topics;

    public boolean isOverride_key_topics() {
        return override_key_topics;
    }

    public void setOverride_key_topics(boolean override_key_topics) {
        this.override_key_topics = override_key_topics;
    }

    public String getKey_topics() {
        return key_topics;
    }

    public void setKey_topics(String key_topics) {
        this.key_topics = key_topics;
    }

    public boolean isConfigure() {
        return configure;
    }

    public void setConfigure(boolean configure) {
        this.configure = configure;
    }

    public String getSuggestion_title() {
        return suggestion_title;
    }

    public void setSuggestion_title(String suggestion_title) {
        this.suggestion_title = suggestion_title;
    }

    public String getSuggestion_reason() {
        return suggestion_reason;
    }

    public void setSuggestion_reason(String suggestion_reason) {
        this.suggestion_reason = suggestion_reason;
    }

    boolean configure;
    String suggestion_title;
    String suggestion_reason;

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public int getSequence_id() {
        return sequence_id;
    }

    public void setSequence_id(int sequence_id) {
        this.sequence_id = sequence_id;
    }

    public String getSchedule_type() {
        return schedule_type;
    }

    public void setSchedule_type(String schedule_type) {
        this.schedule_type = schedule_type;
    }


    public int getSchedule_offset() {
        return schedule_offset;
    }

    public void setSchedule_offset(int schedule_offset) {
        this.schedule_offset = schedule_offset;
    }

    public int getAction_priority_key() {
        return action_priority_key;
    }

    public void setAction_priority_key(int action_priority_key) {
        this.action_priority_key = action_priority_key;
    }

	public boolean isOverride_automated_scoring() {
		return override_automated_scoring;
	}

	public void setOverride_automated_scoring(boolean override_automated_scoring) {
		this.override_automated_scoring = override_automated_scoring;
	}

}
