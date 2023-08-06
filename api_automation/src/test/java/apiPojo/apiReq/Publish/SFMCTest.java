package apiPojo.apiReq.Publish;

public class SFMCTest {

    private String clientid;
    private String clientsecret;
    private String accountMID;
    private String useOAuth2Authentication;

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

    public String getAccountMID() {
        return accountMID;
    }

    public void setAccountMID(String accountMID) {
        this.accountMID = accountMID;
    }

    public String getUseOAuth2Authentication() {
        return useOAuth2Authentication;
    }

    public void setUseOAuth2Authentication(String useOAuth2Authentication) {
        this.useOAuth2Authentication = useOAuth2Authentication;
    }
}
