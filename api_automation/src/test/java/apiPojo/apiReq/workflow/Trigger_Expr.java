package apiPojo.apiReq.workflow;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class Trigger_Expr {
    String not;
    List<BaseRule> rules;
    String combinator;
    String group_name;
    String rule_seqno;

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public List<BaseRule> getRules() {
        return rules;
    }

    public void setRules(List<BaseRule> rules) {
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

    public String getRule_seqno() {
        return rule_seqno;
    }

    public void setRule_seqno(String rule_seqno) {
        this.rule_seqno = rule_seqno;
    }

}
