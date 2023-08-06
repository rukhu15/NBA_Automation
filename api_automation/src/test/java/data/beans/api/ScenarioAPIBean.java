package data.beans.api;

import framework.utility.dataParam.BaseBean;

/**
 * @author Prateek Sethi
 */
public class ScenarioAPIBean implements BaseBean {
    public String testDescription=null;
    public String scenarioName =null;
    public String scenarioDescription =null;
    public String scenarioOwner=null;
    public String scenarioMember=null;
    public String tags =null;
    public String isActive =null;
    public String brand_name =null;
    public String isDeleted=null;

    @Override
    public String toString() {
        return "ScenarioAPIBean{" +
                "testDescription='" + testDescription + '\'' +
                ", scenarioName='" + scenarioName + '\'' +
                ", scenarioDescription='" + scenarioDescription + '\'' +
                '}';
    }
}
