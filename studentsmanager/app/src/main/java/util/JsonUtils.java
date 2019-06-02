package util;

import android.graphics.Color;

import com.example.studentsmanager.activity.WelcomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fragment_main.menu_sign_on.ClassInfo;
import fragment_me.class_table.ClassTbInfo;
import fragment_me.holidays_records.HolidaysIRecordsnfo;
import fragment_me.night_records.NightRecordsInfo;
import test.Holidays;


public class JsonUtils {
    //解析请假记录
    public static List<HolidaysIRecordsnfo> ListLea(String success, String lealist) {
        List<HolidaysIRecordsnfo> Lea = new ArrayList<HolidaysIRecordsnfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stulea = JsonObject0.getString(lealist);
            JSONArray jsonArray = new JSONArray(stulea);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    HolidaysIRecordsnfo h = new HolidaysIRecordsnfo();
                    h.setStartDate(jsonObject.getString("leaTime"));
                    h.setEndDate(jsonObject.getString("backTime"));
                    h.setHolidaysReason(jsonObject.getString("leaRea"));
                    h.setStateType(Integer.valueOf(jsonObject.getString("pass")));
                    Lea.add(h);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Lea;

    }

    //解析晚归记录
    public static List<NightRecordsInfo> ListBack(String success, String stuBack) {
        List<NightRecordsInfo> Lea = new ArrayList<NightRecordsInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stuback = JsonObject0.getString(stuBack);
            JSONArray jsonArray = new JSONArray(stuback);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    NightRecordsInfo n = new NightRecordsInfo();
                    n.setsName(WelcomeActivity.studentsName);
                    String date = jsonObject.getString("date");
                    n.setDate(LocalTime.DateTimeToDate(date));
                    String backTime = jsonObject.getString("backTime");
                    if (backTime.equals("")) {
                        backTime = "";
                    } else {
                        backTime = LocalTime.DateTimeToTime(jsonObject.getString("backTime"));
                    }
                    n.setTime(backTime);
                    n.setMonth(LocalTime.getMonth(date));
                    n.setState(Integer.valueOf(jsonObject.getString("backState")));
                    Lea.add(n);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Lea;

    }

    //解析课表
    public static List<ClassTbInfo> Listclass(String success, String weekclass) {
        List<ClassTbInfo> Lea = new ArrayList<ClassTbInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String clazz = JsonObject0.getString(weekclass);
            JSONArray jsonArray = new JSONArray(clazz);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    ClassTbInfo c = new ClassTbInfo();
                    Random random = new Random();
                    c.setClassName(jsonObject.getString("ClaName"));
                    c.setClassRoom(jsonObject.getString("ClassRoom"));
                    int week = Integer.valueOf(jsonObject.getString("Week"));
                    if (week == 0){
                        c.setDay(6);
                    }else{
                        c.setDay(week-1);
                    }
                    c.setJiesu(Integer.valueOf(jsonObject.getString("Section")) - 1);
                    c.setColor(Color.argb(60,random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    Lea.add(c);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return Lea;

    }

    //解析签到记录
    public static List<ClassInfo> ListSignIn(String success, String signOn) {
        List<ClassInfo> sign = new ArrayList<ClassInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String signOnR = JsonObject0.getString(signOn);
            JSONArray jsonArray = new JSONArray(signOnR);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    ClassInfo c = new ClassInfo();
                    String classTime = "00:00";
                    c.setClassName(jsonObject.getString("ClaName"));
                    c.setClassRoom(jsonObject.getString("ClassRoom"));
                    c.setTeacher(jsonObject.getString("TeaName"));
                    c.setStateType(Integer.valueOf(jsonObject.getString("SigState")));
                    c.setDay(Integer.valueOf(LocalTime.getDay(jsonObject.getString("Time"))));
                    c.setTeaNo(jsonObject.getString("TeaNo"));
                    c.setTeaTel(jsonObject.getString("TeaTel"));
                    switch (Integer.valueOf(jsonObject.getString("Section"))){
                        case 1:
                            classTime = "8:20";
                            break;
                        case 2:
                            classTime = "10:20";
                            break;
                        case 3:
                            classTime = "14:20";
                            break;
                        case 4:
                            classTime = "16:20";
                            break;
                        case 5:
                            classTime = "19:00";
                            break;
                        case 6:
                            classTime = "19:55";
                            break;
                        case 7:
                            classTime = "20:50";
                            break;
                    }
                    c.setClassTime(classTime);
                    sign.add(c);
                }
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sign;

    }
}

