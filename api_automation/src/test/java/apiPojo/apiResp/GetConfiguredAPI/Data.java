package apiPojo.apiResp.GetConfiguredAPI;

import java.util.List;

public class Data {

    private int connector_id;
    private String connector_name;
    private int connection_id;
    private String connection_name;
    private String connector_identifier;
    private String logo_path;
    private String description;
    private boolean is_verified;
    private String connector_type;
    private String category_name;

    private boolean deletable;

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public String getUpdated_timestamp() {
        return updated_timestamp;
    }

    public void setUpdated_timestamp(String updated_timestamp) {
        this.updated_timestamp = updated_timestamp;
    }

    private String updated_timestamp;

    public List<String> getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(List<String> channel_name) {
        this.channel_name = channel_name;
    }

    private List<String> channel_name;

    public int getConnector_id() {
        return connector_id;
    }

    public void setConnector_id(int connector_id) {
        this.connector_id = connector_id;
    }

    public String getConnector_name() {
        return connector_name;
    }

    public void setConnector_name(String connector_name) {
        this.connector_name = connector_name;
    }

    public int getConnection_id() {
        return connection_id;
    }

    public void setConnection_id(int connection_id) {
        this.connection_id = connection_id;
    }

    public String getConnection_name() {
        return connection_name;
    }

    public void setConnection_name(String connection_name) {
        this.connection_name = connection_name;
    }

    public String getConnector_identifier() {
        return connector_identifier;
    }

    public void setConnector_identifier(String connector_identifier) {
        this.connector_identifier = connector_identifier;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getConnector_type() {
        return connector_type;
    }

    public void setConnector_type(String connector_type) {
        this.connector_type = connector_type;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }


}
