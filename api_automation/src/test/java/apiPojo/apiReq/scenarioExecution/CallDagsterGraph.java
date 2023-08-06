package apiPojo.apiReq.scenarioExecution;

/**
 * @author Prateek Sethi
 */
public class CallDagsterGraph {
    private int scenario_id;
    private String scenario_name;
    private boolean debug_mode;
    private int company_id;
    private String company_alias;
    private String ops_dist_list;
    private String metastore_db_name;
    private String wrkspc_dir_path;
    private String zeppelin_host;
    private String secret_key;

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_alias() {
        return company_alias;
    }

    public void setCompany_alias(String company_alias) {
        this.company_alias = company_alias;
    }

    public String getOps_dist_list() {
        return ops_dist_list;
    }

    public void setOps_dist_list(String ops_dist_list) {
        this.ops_dist_list = ops_dist_list;
    }

    public String getMetastore_db_name() {
        return metastore_db_name;
    }

    public void setMetastore_db_name(String metastore_db_name) {
        this.metastore_db_name = metastore_db_name;
    }

    public String getWrkspc_dir_path() {
        return wrkspc_dir_path;
    }

    public void setWrkspc_dir_path(String wrkspc_dir_path) {
        this.wrkspc_dir_path = wrkspc_dir_path;
    }

    public String getZeppelin_host() {
        return zeppelin_host;
    }

    public void setZeppelin_host(String zeppelin_host) {
        this.zeppelin_host = zeppelin_host;
    }


    public boolean isDebug_mode() {
        return debug_mode;
    }

    public void setDebug_mode(boolean debug_mode) {
        this.debug_mode = debug_mode;
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
}
