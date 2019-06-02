package com.example.management.util;

import com.example.management.menu.goHome.GoHomeInfo;
import com.example.management.menu.holidays_records.HolidaysRecordsInfo;
import com.example.management.menu.night_records.NightRecordsInfo;
import com.example.management.menu.signOn.ClassInfo;
import com.example.management.menu.signon_records.details.StudentSignOnInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //解析请假记录
    public static List<HolidaysRecordsInfo> HolidayRecords(String success, String lealist,String dep) {
        List<HolidaysRecordsInfo> list = new ArrayList<HolidaysRecordsInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stulea = JsonObject0.getString(lealist);
            JSONArray jsonArray = new JSONArray(stulea);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    HolidaysRecordsInfo h = new HolidaysRecordsInfo();
                    h.setsName(jsonObject.getString("stuName"));
                    h.setsNo(jsonObject.getString("stuNo"));
                    h.setYuanxi(dep);
                    h.setReason(jsonObject.getString("leaRea"));
                    h.setImgStr(jsonObject.getString("img"));
                    h.setStateType(Integer.valueOf(jsonObject.getString("pass")));
                    h.setStartDate(LocalTimeUtil.DateTimeToDate(jsonObject.getString("leaTime")));
                    list.add(h);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    //解析请假记录
    public static List<NightRecordsInfo> NightRecords(String success, String lealist) {
        List<NightRecordsInfo> list = new ArrayList<NightRecordsInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stulea = JsonObject0.getString(lealist);
            JSONArray jsonArray = new JSONArray(stulea);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    NightRecordsInfo n = new NightRecordsInfo();
                    n.setsName(jsonObject.getString("stuName"));
                    n.setRoom(jsonObject.getString("DorName"));
                    n.setDate(LocalTimeUtil.DateTimeToDate(jsonObject.getString("date")));
                    n.setState(Integer.valueOf(jsonObject.getString("backState")));
                    list.add(n);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    //解析签到记录
    public static List<StudentSignOnInfo> SignOnDetails(String success, String lealist) {
        List<StudentSignOnInfo> list = new ArrayList<StudentSignOnInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stulea = JsonObject0.getString(lealist);
            JSONArray jsonArray = new JSONArray(stulea);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    StudentSignOnInfo s = new StudentSignOnInfo();
                    s.setClassName(jsonObject.getString("ProName") + jsonObject.getString("GarName"));
                    s.setsName(jsonObject.getString("StuName"));
                    s.setState(jsonObject.getString("SigState"));
                    list.add(s);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    //解析归家记录
    public static List<GoHomeInfo> GoHomeRecords(String success, String lealist) {
        List<GoHomeInfo> list = new ArrayList<GoHomeInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stulea = JsonObject0.getString(lealist);
            JSONArray jsonArray = new JSONArray(stulea);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    GoHomeInfo h = new GoHomeInfo();
                    h.setsNum(jsonObject.getString("StuNo"));
                    h.setsName(jsonObject.getString("stuName"));
                    h.setState(Integer.valueOf(jsonObject.getString("StuState")));
                    list.add(h);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    //解析教师课表
    public static List<ClassInfo> TeaClass(String success, String lealist) {
        List<ClassInfo> list = new ArrayList<ClassInfo>();
        try {
            JSONObject JsonObject0 = new JSONObject(success);
            String stulea = JsonObject0.getString(lealist);
            JSONArray jsonArray = new JSONArray(stulea);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                {
                    ClassInfo c = new ClassInfo();
                    String time = "";
                    c.setClassName(jsonObject.getString("ClaName"));
                    c.setWeek(Integer.valueOf(jsonObject.getString("week")));
                    c.setBjName(jsonObject.getString("ProName") + jsonObject.getString("GarName"));
                    switch (Integer.valueOf(jsonObject.getString("Section"))){
                        case 1:
                            time = "8:20";
                            break;
                        case 2:
                            time = "10:20";
                            break;
                        case 3:
                            time = "14:20";
                            break;
                        case 4:
                            time = "16:20";
                            break;
                        case 5:
                            time = "19:00";
                            break;
                    }
                    c.setClassTime(time);
                    list.add(c);
                }
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }








}

