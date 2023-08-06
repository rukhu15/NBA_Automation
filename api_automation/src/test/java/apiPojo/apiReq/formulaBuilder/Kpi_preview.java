package apiPojo.apiReq.formulaBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Kpi_preview {

    PreviewTypeObj kpi_preview;
    protected Map<String,PreviewTypeObj> other = new HashMap<String,PreviewTypeObj>();
    public Object get(String name) {
        return other.get(name);
    }
    @JsonAnyGetter
    public Map<String,PreviewTypeObj> any() {
        return other;
    }

    @JsonAnySetter
    public void set(String name, PreviewTypeObj value) {
        other.put(name, value);
    }
    public PreviewTypeObj getKpi_preview() {
        return kpi_preview;
    }

    public void setKpi_preview(String name, PreviewTypeObj previewTypeObj) {
        set(name,previewTypeObj);
    }
}
