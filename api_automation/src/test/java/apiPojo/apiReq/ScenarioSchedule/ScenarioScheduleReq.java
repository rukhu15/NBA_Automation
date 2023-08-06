package apiPojo.apiReq.ScenarioSchedule;

/**
 * @author Prateek Sethi
 */
public class ScenarioScheduleReq {
    private int scenario_id;
    private String schedule_name;
    private ScheduleExpr schedule_expr;
    private int user_id;
    private boolean is_active;
    private boolean is_enabled;
    private boolean is_deleted;

    public int getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(int scenario_id) {
        this.scenario_id = scenario_id;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public ScheduleExpr getSchedule_expr() {
        return schedule_expr;
    }

    public void setSchedule_expr(ScheduleExpr schedule_expr) {
        this.schedule_expr = schedule_expr;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
