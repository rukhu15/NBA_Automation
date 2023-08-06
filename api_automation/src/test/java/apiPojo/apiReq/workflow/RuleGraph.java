package apiPojo.apiReq.workflow;

/**
 * @author Prateek Sethi
 */
public class RuleGraph {

    int kpi_id;
    int scenario_id;
    String type;
    int time_range;
    String prev_time_type;


    public int getKpi_id() {
        return kpi_id;
    }

    public void setKpi_id(int kpi_id) {
        this.kpi_id = kpi_id;
    }

    public int getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(int scenario_id) {
        this.scenario_id = scenario_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTime_range() {
        return time_range;
    }

    public void setTime_range(int time_range) {
        this.time_range = time_range;
    }

    public String getPrev_time_type() {
        return prev_time_type;
    }

    public void setPrev_time_type(String prev_time_type) {
        this.prev_time_type = prev_time_type;
    }



}
