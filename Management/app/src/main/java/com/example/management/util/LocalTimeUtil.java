package com.example.management.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LocalTimeUtil {

    public static String getLocalTime() {
        String time;
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        time = format.format(new Date());
        return time;
    }

    public static int getWeek(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0)
            w = 7;
        return w;
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

    public static String getLastTDate() {
        String time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
        return time;
    }

    public static String getLastLastTDate() {
        String time;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time = format.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 2));
        return time;
    }


    public static int compareTime(String date1,String date2){
        DateFormat df = new SimpleDateFormat("HH:mm:ss");//创建日期转换对象HH:mm:ss为时分秒，年月日为yyyy-MM-dd
        Date dt1,dt2,dt_before,dt_after;
        try {
            //1.未到时间 2.时间之间 3.超过时间
            dt1 = df.parse(date1); //上课时间
            dt2 = df.parse(date2); //当前时间
            dt_before = new Date(dt1.getTime() - 24 * 60 * 60 * 1000); //上课前十分钟
            dt_after = new Date(dt1.getTime() + 24 * 60 * 60 * 1000); //上课后十分钟
            if(dt_before.getTime() >= dt2.getTime()) //上课前十分钟时间大于当前时间
                return 1;
            else if (dt_after.getTime() > dt2.getTime() && dt_before.getTime() < dt2.getTime())
                return 2;
            else if (dt_after.getTime() <= dt2.getTime())
                return 3;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
