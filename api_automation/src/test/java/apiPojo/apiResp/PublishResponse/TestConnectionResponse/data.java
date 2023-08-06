package apiPojo.apiResp.PublishResponse.TestConnectionResponse;

public class data {

    private boolean conn_status;
    private String conn_msg;

    public boolean isConn_status() {
        return conn_status;
    }

    public void setConn_status(boolean conn_status) {
        this.conn_status = conn_status;
    }

    public String getConn_msg() {
        return conn_msg;
    }

    public void setConn_msg(String conn_msg) {
        this.conn_msg = conn_msg;
    }
}
