package apiPojo.apiResp.Actions;
import apiPojo.apiReq.workflow.DataObject;
import java.util.List;

/**
 * @author Prateek Sethi
 */
public class Data {
    public List<DataObject> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<DataObject> dataPoints) {
        this.dataPoints = dataPoints;
    }

    List<DataObject> dataPoints;

    public String getAvailableAudience() {
        return availableAudience;
    }

    public void setAvailableAudience(String availableAudience) {
        this.availableAudience = availableAudience;
    }

    String availableAudience;


}
