package apiPojo.apiReq.scenario;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class ScenarioUpdateReq {
    private String scenario_name;
    private String scenario_desc;
    private List<Integer> scenario_owner;
    private boolean is_active;
    private boolean is_deleted;
    private int user_id;
    private int wrkspc_id;

    public String getScenario_name() {
        return scenario_name;
    }

    public void setScenario_name(String scenario_name) {
        this.scenario_name = scenario_name;
    }

    public String getScenario_desc() {
        return scenario_desc;
    }

    public void setScenario_desc(String scenario_desc) {
        this.scenario_desc = scenario_desc;
    }

    public List<Integer> getScenario_owner() {
        return scenario_owner;
    }

    public void setScenario_owner(List<Integer> scenario_owner) {
        this.scenario_owner = scenario_owner;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getWrkspc_id() {
        return wrkspc_id;
    }

    public void setWrkspc_id(int wrkspc_id) {
        this.wrkspc_id = wrkspc_id;
    }
}
