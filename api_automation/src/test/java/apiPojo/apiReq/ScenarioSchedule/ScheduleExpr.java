package apiPojo.apiReq.ScenarioSchedule;

/**
 * @author Prateek Sethi
 */
public class ScheduleExpr {
    private String start_date;
    private String end_date;
    private String schedule_interval="";

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getSchedule_interval() {
        return schedule_interval;
    }

    public void setSchedule_interval(String schedule_interval) {
        this.schedule_interval = schedule_interval;
    }
}
