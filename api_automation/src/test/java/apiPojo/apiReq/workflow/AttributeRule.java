package apiPojo.apiReq.workflow;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeRule {
	List<Object> value;
	int kpi_id;
	int rule_id;
	String kpi_type;
	List<String> operator;
	int rule_type;
	int rule_seqno;
	String column_name;
	String display_name;
	boolean is_kpi_groupby_disabled;
	
	public List<Object> getValue() {
		return value;
	}
	public void setValue(List<Object> value) {
		this.value = value;
	}
	public int getKpi_id() {
		return kpi_id;
	}
	public void setKpi_id(int kpi_id) {
		this.kpi_id = kpi_id;
	}
	public int getRule_id() {
		return rule_id;
	}
	public void setRule_id(int rule_id) {
		this.rule_id = rule_id;
	}
	public String getKpi_type() {
		return kpi_type;
	}
	public void setKpi_type(String kpi_type) {
		this.kpi_type = kpi_type;
	}
	public List<String> getOperator() {
		return operator;
	}
	public void setOperator(List<String> operator) {
		this.operator = operator;
	}
	public int getRule_type() {
		return rule_type;
	}
	public void setRule_type(int rule_type) {
		this.rule_type = rule_type;
	}
	public int getRule_seqno() {
		return rule_seqno;
	}
	public void setRule_seqno(int rule_seqno) {
		this.rule_seqno = rule_seqno;
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
	public boolean getIs_kpi_groupby_disabled() {
		return is_kpi_groupby_disabled;
	}
	public void setIs_kpi_groupby_disabled(boolean is_kpi_groupby_disabled) {
		this.is_kpi_groupby_disabled = is_kpi_groupby_disabled;
	}

}
