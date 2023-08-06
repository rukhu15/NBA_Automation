package apiPojo.apiReq.formulaBuilder;

/**
 * @author Prateek Sethi
 */
public class FormulaSave {
    String kpi_name;
    int kpi_id;
    String is_toggle;

    public String getKpi_name() {
        return kpi_name;
    }

    public void setKpi_name(String kpi_name) {
        this.kpi_name = kpi_name;
    }

    public int getKpi_id() {
        return kpi_id;
    }

    public void setKpi_id(int kpi_id) {
        this.kpi_id = kpi_id;
    }

    public String getIs_toggle() {
        return is_toggle;
    }

    public void setIs_toggle(String is_toggle) {
        this.is_toggle = is_toggle;
    }
}
