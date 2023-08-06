package data.beans;

import framework.utility.dataParam.BaseBean;

public class ModelPageBean implements BaseBean {
    public String testDescription = null;

    public String workflowName = null;

    public String workflowDescription =null;

    public String kpiName = null;

    public String scenarioName = null;

    public String actionsInput = null;

    public String tagName = null;

    @Override
    public String toString() {
        return String.format("testDescription: %s|workflowName: %s|workflowDescription: %s|actionsInputs : %s|tagName : %s" ,testDescription,workflowName,workflowDescription,actionsInput,tagName);
    }
}
