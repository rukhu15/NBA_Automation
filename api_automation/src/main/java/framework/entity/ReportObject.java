package framework.entity;

public class ReportObject {
    public String suite, id, desc, status, exeTime, exeDate;

    public int runCount = 1;

    public ReportObject(String suite, String id, String desc, String status, String exeTime, String date) {
        this.suite = suite;
        this.id = id;
        this.desc = desc;
        this.status = status;
        this.exeTime = exeTime;
        this.exeDate = date;
    }
}
