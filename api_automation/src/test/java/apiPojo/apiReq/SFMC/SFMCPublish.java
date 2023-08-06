package apiPojo.apiReq.SFMC;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SFMCPublish {

    private List<login_expr> login_expr;

    private String connection_name;
    private String connector_type;
    private String is_verified;
    private String wrkspc_id;
    private String connector;
    private List<String> channel;

    private String description;

    private String logo_path;


    public List<apiPojo.apiReq.SFMC.login_expr> getLogin_expr() {
        return login_expr;
    }

    public void setLogin_expr(List<apiPojo.apiReq.SFMC.login_expr> login_expr) {
        this.login_expr = login_expr;
    }

    public String getConnection_name() {
        return connection_name;
    }

    public void setConnection_name(String connection_name) {
        this.connection_name = connection_name;
    }

    public String getConnector_type() {
        return connector_type;
    }

    public void setConnector_type(String connector_type) {
        this.connector_type = connector_type;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }

    public String getWrkspc_id() {
        return wrkspc_id;
    }

    public void setWrkspc_id(String wrkspc_id) {
        this.wrkspc_id = wrkspc_id;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public List<String> getChannel() {
        return channel;
    }

    public void setChannel(List<String> channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }
}
