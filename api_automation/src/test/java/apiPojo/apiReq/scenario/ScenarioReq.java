package apiPojo.apiReq.scenario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Prateek Sethi
 */
@Getter
@Setter
@Builder
public class ScenarioReq {
    private String scenario_name;
    private String scenario_desc;
    private List<Integer> scenario_owner;
    private List<String> scenario_member;
    private List<String> tags;
    private int created_by;
    private boolean is_active;
    private int brand_id;
    private boolean is_deleted;


}
