package apiPojo.apiReq.formulaBuilder;

import java.util.List;

/**
 * @author Prateek Sethi
 */

public class DataSetType {

    String variable_type;
    String data_source;
    String file_name;
    String column_name;
    String operation;
    List<String> group_by;
    String time_period;
    String time_grain;
    List<Filter> filter;

    public String getVariable_type() {
        return variable_type;
    }

    public void setVariable_type(String variable_type) {
        this.variable_type = variable_type;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<String> getGroup_by() {
        return group_by;
    }

    public void setGroup_by(List<String> group_by) {
        this.group_by = group_by;
    }

    public String getTime_period() {
        return time_period;
    }

    public void setTime_period(String time_period) {
        this.time_period = time_period;
    }

    public String getTime_grain() {
        return time_grain;
    }

    public void setTime_grain(String time_grain) {
        this.time_grain = time_grain;
    }

    public List<Filter> getFilter() {
        return filter;
    }

    public void setFilter(List<Filter> filter) {
        this.filter = filter;
    }
}