package data.beans.api;

import framework.utility.dataParam.BaseBean;

/**
 * @author Prateek Sethi
 */
public class WorkflowAPIBean implements BaseBean {
    public String testDescription=null;
    public String scenarioName =null;
    public String workspaceName =null;
    public String workflow_name=null;
    public String workflow_desc=null;
    public String trigger_expr =null;
    public String rule_expr =null;
    public String action_expr=null;
    public String kpi_name=null;
    public String channelName=null;
    public String expectedModelsForChannel;
    public String kpi_type;
    public String time_range;
    public String prev_time_type;
    public String customerCountQuery;
}
