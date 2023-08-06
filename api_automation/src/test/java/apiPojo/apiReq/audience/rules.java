package apiPojo.apiReq.audience;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class rules {
    private String type;
    private List<String> value;
    private String operator;
    private String base_table;
    private String column_name;
    private String display_name;
    private String display_category;
    private String interim_table;
    private String compound_table;
    private int rule_seqno;
    private int kpi_id;

    public int getRule_seqno() {
        return rule_seqno;
    }

    public void setRule_seqno(int rule_seqno) {
        this.rule_seqno = rule_seqno;
    }

    public String getBase_table() {
        return base_table;
    }

    public void setBase_table(String base_table) {
        this.base_table = base_table;
    }

    public String getInterim_table() {
        return interim_table;
    }

    public void setInterim_table(String interim_table) {
        this.interim_table = interim_table;
    }

    public String getCompound_table() {
        return compound_table;
    }

    public void setCompound_table(String compound_table) {
        this.compound_table = compound_table;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getDisplay_category() {
        return display_category;
    }

    public void setDisplay_category(String display_category) {
        this.display_category = display_category;
    }
    
    public int getKpi_id() {
		return kpi_id;
	}

	public void setKpi_id(int kpi_id) {
		this.kpi_id = kpi_id;
	}
}
