package util;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocalTime {

    public static String getLocalTime() {
        String time;
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(new Date());
        return time;
    }

    public static String DateTimeToTime(String DateTime){
        String time[] = DateTime.split("T");
        return time[1];
    }

    public static int getMonth(String DateTime){
        String time[] = DateTime.split("T");
        String month[] = time[0].split("-");
        return Integer.valueOf(month[1]);
    }

    public static int getDay(String DateTime){
        String time[] = DateTime.split("T");
        String month[] = time[0].split("-");
        return Integer.valueOf(month[2]);
    }

    public static String DateTimeToDate(String DateTime){
        String time[] = DateTime.split("T");
        return time[0];
    }

    public static Date StringToDate(String time) {
        Date Time = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Time = new Date(format.parse(time).getTime());
            return Time;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return Time;
        }
    }

    public static String getDateAndTime() {
        String time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time = format.format(new Date());
        return time;
    }

    public static String getLocalTDate() {
        String time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time = format.format(new Date());
        return time;
    }


}
