package apiPojo.apiReq.audience;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class filter_expr {
    private boolean not;
    private List<rules> rules;
    private String combinator;
    private String group_name;
    private String rule_seqno;

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

    public String getCombinator() {
        return combinator;
    }

    public void setCombinator(String combinator) {
        this.combinator = combinator;
    }

    public boolean getNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public List<apiPojo.apiReq.audience.rules> getRules() {
        return rules;
    }

    public void setRules(List<apiPojo.apiReq.audience.rules> rules) {
        this.rules = rules;
    }
}
