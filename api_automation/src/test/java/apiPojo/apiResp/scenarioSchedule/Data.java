package apiPojo.apiResp.scenarioSchedule;

/**
 * @author Prateek Sethi
 */
public class Data {
    private String schedule_name;
    private int scenario_id;
    private ScheduleExpr schedule_expr;

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public int getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(int scenario_id) {
        this.scenario_id = scenario_id;
    }

    public ScheduleExpr getSchedule_expr() {
        return schedule_expr;
    }

    public void setSchedule_expr(ScheduleExpr schedule_expr) {
        this.schedule_expr = schedule_expr;
    }
}
