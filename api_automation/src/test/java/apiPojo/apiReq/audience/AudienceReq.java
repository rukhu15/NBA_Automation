package apiPojo.apiReq.audience;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class AudienceReq {
    private String audience_name =null;
    private apiPojo.apiReq.audience.filter_expr filter_expr=null;
    private List<Integer> brand_list;
    private int wrkspc_id;
    private String is_template;
    private String cust_type=null;
    private String is_active =null;
    private String is_deleted =null;
    private int created_by;
    private int updated_by;

    public String getIs_template() {
        return is_template;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public List<Integer> getBrand_list() {
        return brand_list;
    }

    public void setBrand_list(List<Integer> brand_list) {
        this.brand_list = brand_list;
    }

    public int getWrkspc_id() {
        return wrkspc_id;
    }

    public void setWrkspc_id(int wrkspc_id) {
        this.wrkspc_id = wrkspc_id;
    }

    public String isIs_template() {
        return is_template;
    }

    public void setIs_template(String is_template) {
        this.is_template = is_template;
    }

    public String getAudience_name() {
        return audience_name;
    }

    public void setAudience_name(String audience_name) {
        this.audience_name = audience_name;
    }

    public apiPojo.apiReq.audience.filter_expr getFilter_expr() {
        return filter_expr;
    }

    public void setFilter_expr(apiPojo.apiReq.audience.filter_expr filter_expr) {
        this.filter_expr = filter_expr;
    }

    public String getCust_type() {
        return cust_type;
    }

    public void setCust_type(String cust_type) {
        this.cust_type = cust_type;
    }





    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }
}
