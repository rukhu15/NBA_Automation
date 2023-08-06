package data.beans;

import framework.utility.dataParam.BaseBean;

public class KpiPageBean implements BaseBean {

    public String testDescription = null, kpiDetails = null;

    @Override
    public String toString() {
        return String.format(
                "testDescription: %s|kpiDetails: %s",
                testDescription, kpiDetails);
    }

}
