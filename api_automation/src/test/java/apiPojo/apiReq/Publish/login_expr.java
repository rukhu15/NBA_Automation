package apiPojo.apiReq.Publish;

public class login_expr {


    String username;
    String password;
    String security_token;
    String env_url;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurity_token() {
        return security_token;
    }

    public void setSecurity_token(String security_token) {
        this.security_token = security_token;
    }

    public String getEnv_url() {
        return env_url;
    }

    public void setEnv_url(String env_url) {
        this.env_url = env_url;
    }
}