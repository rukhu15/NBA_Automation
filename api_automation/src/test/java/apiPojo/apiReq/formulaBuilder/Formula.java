package apiPojo.apiReq.formulaBuilder;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONObject;
import java.util.List;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Formula {
    String kpi_name;
    String kpi_desc;
    String kpi_syntax;
    List<String> kpi_tags;
    String kpi_view;
    FormulaExprn kpi_exprn;
    String operations;
    boolean is_deleted;
    boolean is_active;
    int kpi_category;
    int kpi_subcategory;
    boolean use_as_trigger;
    boolean use_as_rule;
    int created_by;
    String created_timestamp;
    int updated_by;
    String updated_timestamp;
    int kpi_time_range;
    String kpi_prev_time_type;
    int rule_usage;

    public int getCreated_by() {
        return created_by;
    }

    public void setCreated_by(int created_by) {
        this.created_by = created_by;
    }

    public String getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(String created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    public int getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(int updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdated_timestamp() {
        return updated_timestamp;
    }

    public void setUpdated_timestamp(String updated_timestamp) {
        this.updated_timestamp = updated_timestamp;
    }

    public int getKpi_time_range() {
        return kpi_time_range;
    }

    public void setKpi_time_range(int kpi_time_range) {
        this.kpi_time_range = kpi_time_range;
    }

    public String getKpi_prev_time_type() {
        return kpi_prev_time_type;
    }

    public void setKpi_prev_time_type(String kpi_prev_time_type) {
        this.kpi_prev_time_type = kpi_prev_time_type;
    }

    public int getRule_usage() {
        return rule_usage;
    }

    public void setRule_usage(int rule_usage) {
        this.rule_usage = rule_usage;
    }

    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }



    public boolean getIs_deleted() {
        return is_deleted;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public Kpi_preview getKpi_preview() {
        return kpi_preview;
    }

    public void setKpi_preview(Kpi_preview kpi_preview) {
        this.kpi_preview = kpi_preview;
    }

    Kpi_preview kpi_preview;


    public boolean isUse_as_trigger() {
        return use_as_trigger;
    }

    public void setUse_as_trigger(boolean use_as_trigger) {
        this.use_as_trigger = use_as_trigger;
    }

    public boolean isUse_as_rule() {
        return use_as_rule;
    }

    public void setUse_as_rule(boolean use_as_rule) {
        this.use_as_rule = use_as_rule;
    }

    public String getKpi_desc() {
        return kpi_desc;
    }

    public void setKpi_desc(String kpi_desc) {
        this.kpi_desc = kpi_desc;
    }

    public String getKpi_name() {
        return kpi_name;
    }

    public void setKpi_name(String kpi_name) {
        this.kpi_name = kpi_name;
    }

    public int getKpi_category() {
        return kpi_category;
    }

    public void setKpi_category(int kpi_category) {
        this.kpi_category = kpi_category;
    }

    public int getKpi_subcategory() {
        return kpi_subcategory;
    }

    public void setKpi_subcategory(int kpi_subcategory) {
        this.kpi_subcategory = kpi_subcategory;
    }

    public String getKpi_syntax() {
        return kpi_syntax;
    }

    public void setKpi_syntax(String kpi_syntax) {
        this.kpi_syntax = kpi_syntax;
    }

    public List<String> getKpi_tags() {
        return kpi_tags;
    }

    public void setKpi_tags(List<String> kpi_tags) {
        this.kpi_tags = kpi_tags;
    }

    public String getKpi_view() {
        return kpi_view;
    }

    public void setKpi_view(String kpi_view) {
        this.kpi_view = kpi_view;
    }




    public FormulaExprn getKpi_exprn() {
        return kpi_exprn;
    }

    public void setKpi_exprn(FormulaExprn kpi_exprn) {
        this.kpi_exprn = kpi_exprn;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
