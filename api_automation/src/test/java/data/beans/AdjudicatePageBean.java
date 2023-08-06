package data.beans;

import framework.utility.dataParam.BaseBean;

public class AdjudicatePageBean implements BaseBean {

    public String testDescription = null;

    public String scenarioName = null;

    public String channelName =null;

    @Override
    public String toString() {
        return String.format("testDescription: %s|channelName : %s|scenarioName : %s" ,testDescription,channelName,scenarioName);
    }
}
