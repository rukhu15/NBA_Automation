package apiPojo.apiReq.workflow;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class Rule_Expr {
    boolean not;
    List<Object> rules;
    String combinator;
    String group_name;
    Integer rule_seqno;

    public boolean getNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public List<Object> getRules() {
        return rules;
    }

    public void setRules(List<Object> rules) {
        this.rules = rules;
    }

    public String getCombinator() {
        return combinator;
    }

    public void setCombinator(String combinator) {
        this.combinator = combinator;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public Integer getRule_seqno() {
        return rule_seqno;
    }

    public void setRule_seqno(Integer rule_seqno) {
        this.rule_seqno = rule_seqno;
    }

}
