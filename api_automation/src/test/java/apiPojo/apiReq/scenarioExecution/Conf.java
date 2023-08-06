package apiPojo.apiReq.scenarioExecution;

/**
 * @author Prateek Sethi
 */
public class Conf {
    private String wrk_spc_id;
    private int scenario_id;
    private String scenario_name;
    private String client_id;
    private boolean debug_mode;

    public String getWrk_spc_id() {
        return wrk_spc_id;
    }

    public void setWrk_spc_id(String wrk_spc_id) {
        this.wrk_spc_id = wrk_spc_id;
    }

    public int getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(int scenario_id) {
        this.scenario_id = scenario_id;
    }

    public String getScenario_name() {
        return scenario_name;
    }

    public void setScenario_name(String scenario_name) {
        this.scenario_name = scenario_name;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public boolean isDebug_mode() {
        return debug_mode;
    }

    public void setDebug_mode(boolean debug_mode) {
        this.debug_mode = debug_mode;
    }
}
