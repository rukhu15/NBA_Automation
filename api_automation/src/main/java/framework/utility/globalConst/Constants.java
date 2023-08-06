package framework.utility.globalConst;


public class Constants {

    public static int EXPLICIT_SMALL_WAIT_TIME = 6; // Must not exceed 8 seconds Max
    public static int EXPLICIT_WAIT_TIME = 14; // Must not exceed 15 seconds Max
    public static int EXPLICIT_LONG_WAIT_TIME = 30; // Must not exceed 15 seconds Max
    public static int IMPLICIT_WAIT_TIME = 12; // Must not exceed 15 seconds Max
    public static int NOTIFICATION_WAIT_TIME = 40; // Must not exceed 15 seconds Max
    public static int MAX_WAIT_TIME = 3200; // Must not exceed 3.5 seconds, used for sync
    public static int MAX_LONG_WAIT_TIME = 8000; // Must not exceed 10 seconds, used for sync

    //For Model Rules to Verify KPI Name it will use over framework anywhere we want to use KPI names
    public static String KPI_Name_Categorical_KPI = "kpiAutomation_One";

}
