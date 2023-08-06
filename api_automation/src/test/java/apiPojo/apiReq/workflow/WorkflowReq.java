package apiPojo.apiReq.workflow;

/**
 * @author Prateek Sethi
 */
public class WorkflowReq {
    String workflow_name;
    String workflow_desc;
    Rule_Expr trigger_expr;
    Rule_Expr rule_expr;
    Action_Expr action_expr;

    public String getWorkflow_name() {
        return workflow_name;
    }

    public void setWorkflow_name(String workflow_name) {
        this.workflow_name = workflow_name;
    }

    public String getWorkflow_desc() {
        return workflow_desc;
    }

    public void setWorkflow_desc(String workflow_desc) {
        this.workflow_desc = workflow_desc;
    }

    public Rule_Expr getTrigger_expr() {
        return trigger_expr;
    }

    public void setTrigger_expr(Rule_Expr trigger_expr) {
        this.trigger_expr = trigger_expr;
    }

    public Rule_Expr getRule_expr() {
        return rule_expr;
    }

    public void setRule_expr(Rule_Expr rule_expr) {
        this.rule_expr = rule_expr;
    }

    public Action_Expr getAction_expr() {
        return action_expr;
    }

    public void setAction_expr(Action_Expr action_expr) {
        this.action_expr = action_expr;
    }

}
