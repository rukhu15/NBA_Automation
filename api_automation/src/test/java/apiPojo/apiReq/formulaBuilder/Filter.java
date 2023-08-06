package apiPojo.apiReq.formulaBuilder;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

/**
 * @author Prateek Sethi
 */

public class Filter {
    String column_name;
    String value;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
