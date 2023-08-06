package data.beans;

import framework.utility.dataParam.BaseBean;

public class SegmentationPageBean implements BaseBean {

    public String testDescription = null, detailsOfSegment = null;

    @Override
    public String toString() {
        return String.format(
                "testDescription: %s|detailsOfSegment: %s",
                testDescription, detailsOfSegment);
    }

}
