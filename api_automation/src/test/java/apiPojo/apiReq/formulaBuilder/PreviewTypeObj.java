package apiPojo.apiReq.formulaBuilder;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class PreviewTypeObj {
    String desc;
    List<Preview_Display> child;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Preview_Display> getChild() {
        return child;
    }

    public void setChild(List<Preview_Display> child) {
        this.child = child;
    }

}
