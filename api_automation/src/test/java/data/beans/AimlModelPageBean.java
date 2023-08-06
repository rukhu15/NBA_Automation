package data.beans;

import framework.utility.dataParam.BaseBean;
import framework.utility.globalConst.Constants;

public class AimlModelPageBean implements BaseBean{

    public String testDescription = null, modelDetails = null;

    @Override
    public String toString() {
        return String.format(
                "testDescription: %s|modelDetails: %s",
                testDescription, modelDetails);
    }

}
