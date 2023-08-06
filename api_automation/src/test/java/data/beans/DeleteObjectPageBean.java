package data.beans;

import framework.utility.dataParam.BaseBean;

public class DeleteObjectPageBean implements BaseBean {

    public String testDescription = null, objectsToDelete = null;

    @Override
    public String toString() {
        return String.format(
                "testDescription: %s|objectsToDelete: %s",
                testDescription, objectsToDelete);
    }

}
