package apiPojo.apiResp.scenario;

import java.util.List;

/**
 * @author Prateek Sethi
 */
public class ScenarioResp {
	private String scenario_name;
	private String scenario_desc;
	private List<Integer> scenario_owner;
	private List<String> scenario_member;
	private List<String> tags;
	private int created_by;
	private boolean is_active;
	private String created_timestamp;
	private int wrkspc_id;
	private int brand_id;
//    private String therapy_id;
//    private String select_flag;
	private String is_exist;

	public String getIs_exist() {
		return is_exist;
	}

	public void setIs_exist(String is_exist) {
		this.is_exist = is_exist;
	}

	public int getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(int brand_id) {
		this.brand_id = brand_id;
	}

	public int getScenario_id() {
		return scenario_id;
	}

	public void setScenario_id(int scenario_id) {
		this.scenario_id = scenario_id;
	}

	private int scenario_id;

	public int getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(int updated_by) {
		this.updated_by = updated_by;
	}

	private int updated_by;

	public int getWrkspc_id() {
		return wrkspc_id;
	}

	public void setWrkspc_id(int wrkspc_id) {
		this.wrkspc_id = wrkspc_id;
	}

	public String getCreated_timestamp() {
		return created_timestamp;
	}

	public void setCreated_timestamp(String created_timestamp) {
		this.created_timestamp = created_timestamp;
	}

	public String getUpdated_timestamp() {
		return updated_timestamp;
	}

	public void setUpdated_timestamp(String updated_timestamp) {
		this.updated_timestamp = updated_timestamp;
	}

	private String updated_timestamp;

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	private boolean is_deleted;

	public void setScenario_name(String scenario_name) {
		this.scenario_name = scenario_name;
	}

	public void setScenario_desc(String scenario_desc) {
		this.scenario_desc = scenario_desc;
	}

	public void setScenario_owner(List<Integer> scenario_owner) {
		this.scenario_owner = scenario_owner;
	}

	public void setScenario_member(List<String> scenario_member) {
		this.scenario_member = scenario_member;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setCreated_by(int created_by) {
		this.created_by = created_by;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getScenario_name() {
		return scenario_name;
	}

	public String getScenario_desc() {
		return scenario_desc;
	}

	public List<Integer> getScenario_owner() {
		return scenario_owner;
	}

	public List<String> getScenario_member() {
		return scenario_member;
	}

	public List<String> getTags() {
		return tags;
	}

	public int getCreated_by() {
		return created_by;
	}

	public boolean isIs_active() {
		return is_active;
	}

}