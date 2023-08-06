package data.beans;

import framework.utility.dataParam.BaseBean;

public class TagPageBean implements BaseBean {
    public String testDescription = null;
    public String tagName = null;

    public String tagDesc =null;

    @Override
    public String toString() {
        return String.format("testDescription: %s|tagName: %s|tagDesc: %s" ,testDescription,tagName,tagDesc);
    }
}
