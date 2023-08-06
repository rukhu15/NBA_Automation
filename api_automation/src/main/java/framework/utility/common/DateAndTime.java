package framework.utility.common;

import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAndTime {
    public static Long getTimeStamp(int minutes) {
        return DateUtils.addMinutes(new Date(), minutes).getTime();
    }

    /**
     * @param value
     * @return
     * @throws Exception
     */
    public static String getDateMMddyyyy(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, value);
        Date date = cal.getTime();
        String newDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
        return newDate;
    }

    public static String getFormattedDate(String date) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date tDate = formatter.parse(date);
        return new SimpleDateFormat("MM/dd/yyyy").format(tDate);
    }

    public static String getDateDDMMYYYY(int value) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, value);
        Date date = cal.getTime();
        String newDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
        return newDate;
    }

    public static Timestamp getCurrentTimeStamp(){
        return new Timestamp(new Date().getTime());
    }

    public static String getTimeDifference(Timestamp t1, Timestamp t2) {
        Long diff = t2.getTime() - t1.getTime();
        int seconds = (int) (diff / 1000);

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = (seconds % 3600) % 60;

        return hours + ":" + minutes + ":" + seconds;
    }

    /**
     * @param date MM /DD/YYYY in string form
     * @return
     */
    public static String getDateYYYYMMDD(String date) {
        String newDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(date));
        return newDate;
    }
}
