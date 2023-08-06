package apiPojo.apiReq.SFMC;

public class login_expr {


    private String clientid;
    private String clientsecret;
    private String useOAuth2Authentication;
    private String accountMID;
    private String webserviceurl;
    private String password;
    private String username;
    private String api_scopes;
    private String api_service;
    private String ft_server_host_address;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public String getUseOAuth2Authentication() {
        return useOAuth2Authentication;
    }

    public void setUseOAuth2Authentication(String useOAuth2Authentication) {
        this.useOAuth2Authentication = useOAuth2Authentication;
    }

    public String getAccountMID() {
        return accountMID;
    }

    public void setAccountMID(String accountMID) {
        this.accountMID = accountMID;
    }

    public String getWebserviceurl() {
        return webserviceurl;
    }

    public void setWebserviceurl(String webserviceurl) {
        this.webserviceurl = webserviceurl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApi_scopes() {
        return api_scopes;
    }

    public void setApi_scopes(String api_scopes) {
        this.api_scopes = api_scopes;
    }

    public String getApi_service() {
        return api_service;
    }

    public void setApi_service(String api_service) {
        this.api_service = api_service;
    }

    public String getFt_server_host_address() {
        return ft_server_host_address;
    }

    public void setFt_server_host_address(String ft_server_host_address) {
        this.ft_server_host_address = ft_server_host_address;
    }
}
