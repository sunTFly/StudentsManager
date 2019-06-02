package com.example.management.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
    //private static final String path = "http://172.20.10.8:8080/studentsManager/";
    private static final String path = "http://120.78.164.217:8080/studentsManager/";
    //登录
    public static String post_login(String teaNo, String password) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("teaNo", teaNo);
            params.put("password", password);
            return sendPostRequest("teacherLoginAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }
    //判断是否发起签到
    public static String post_issign(String teaNo) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("teaNo", teaNo);
            return sendPostRequest("teacherissignAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }
    //教师端查看课程
    public static String post_asclass(String week, String teaNo) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("week", week);
            params.put("teaNo", teaNo);
            return sendPostRequest("teacherClassAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }
    //教师端签到记录
    public static String post_teachersign(String teaName, String section,String signDate) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("teaName", teaName);
            params.put("section", section);
            params.put("signDate", signDate);
            return sendPostRequest("teachersigninAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //归家记录
    public static String post_homerecord(String depName) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("depName", depName);
            return sendPostRequest("teacherHomeAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }
    //更新教师信息
    public static String post_teaupdate(String teaNo, String teaPw, String teaTel, String imgStr) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("teaNo", teaNo);
            params.put("teaPw", teaPw);
            params.put("teaTel", teaTel);
            params.put("imgStr", imgStr);
            return sendPostRequest("teacherupdateAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //教师端查询晚归记录
    public static String post_teabackrec(String depName, String dorName, String time) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("depName", depName);
            params.put("dorName", dorName);
            params.put("backTime", time);
            return sendPostRequest("teacherbackAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //请假记录
    public static String post_leaveRecord(String stuNo) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            return sendPostRequest("LeaveRecordAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //晚归记录
    public static String post_backRecord(String stuNo) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            return sendPostRequest("BackRecordAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //签到记录
    public static String post_signInRecord(String stuNo, String time) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("time", time);
            return sendPostRequest("SignInRecordAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //晚归签到
    public static String post_back(String stuNo, String backTime, String time) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("backTime", backTime);
            params.put("time", time);
//params.put("img",bitmap);
            return sendPostRequest("studentsBackAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //院系请假签到
    public static String post_depleas(String depName, String pass, String leaTime) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("depName", depName);
            params.put("pass", pass);
            params.put("leaTime", leaTime);
            return sendPostRequest("teacherleaveAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }
    //发起签到
    public static String post_teasignin(String teaNo, String logtude, String lattude) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("teaNo", teaNo);
            params.put("logtude", logtude);
            params.put("lattude", lattude);
            return sendPostRequest("teacherpositionAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //请假批准
    public static String post_leadel(String stuNo, String leaTime, String pass) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("pass", pass);
            params.put("leaTime", leaTime);
            return sendPostRequest("teacherleaveDealAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }


    //上课签到
    public static String post_signIn(String stuNo, String section, String time) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("section", section);
            params.put("time", time);
//params.put("img",bitmap);
            return sendPostRequest("SignInAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //学生归家
    public static String post_backhome(String stuNo, String homeAda, String homeTime) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("homeAda", homeAda);
            params.put("homeTime", homeTime);
//params.put("img",bitmap);
            return sendPostRequest("backHomeAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //查看课表
    public static String post_weekclass(String weekcla) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("weekcla", weekcla);
            return sendPostRequest("WeekclassAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //学生请假
    public static String post_leave(String ispass, String stuNo, String leaTime, String backTime, String leaReson, String leaImg) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("isPass", ispass);
            params.put("stuNo", stuNo);
            params.put("leaTime", leaTime);
            params.put("backTime", backTime);
            params.put("leaReson", leaReson);
            params.put("leaImg", leaImg);
            return sendPostRequest("studentsLeaveAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //更新学生信息
    public static String post_stuUpdate(String stuNo, String stuPw, String StuTel, String stuAdr) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("stuPw", stuPw);
            params.put("StuTel", StuTel);
            params.put("stuAdr", stuAdr);
            return sendPostRequest("studentsUpdateAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //发送psot请求
    private static String sendPostRequest(String action, Map<String, String> params, String encoding) throws Exception {
        StringBuilder sb = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=");
                sb.append(URLEncoder.encode(entry.getValue(), encoding));
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        byte[] entity = sb.toString().getBytes();
        URL url = new URL(path + action);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content_Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
        OutputStream outstream = conn.getOutputStream();
        outstream.write(entity);
        if (conn.getResponseCode() == 200) {
            InputStream instream = conn.getInputStream();
            String text = StreamToString.readInputStream(instream);
            return text;
        }
        return "连接失败";
    }

}
