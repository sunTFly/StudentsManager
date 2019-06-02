package util;

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
  //  private static final String path = "http://192.168.43.35:8080/studentsManager/";

//登录
    public static String post_login(String stuNo, String password) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("password", password);
            return sendPostRequest("studentsLoginAction.action", params, "UTF-8");
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
    //晚归签到状态
    public static String post_backstate(String stuNo, String date) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("date", date);
            return sendPostRequest("BackStateMapAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }

    //学生归家
    public static String post_backhome(String stuNo, String homeAda,String homeTime) {
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
    public static String post_signInRecord(String stuNo,String time,String state) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("time", time);
            params.put("state", state);
            return sendPostRequest("SignInRecordAction.action", params, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "提交失败";
    }
    //晚归签到
    public static String post_back(String stuNo, String backTime,String time) {
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
    //上课签到
    public static String post_signIn(String stuNo, String section,String time) {
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
    public static String post_leave(String isPass, String stuNo, String leaTime, String backTime, String leaReson, String leaImg) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("isPass", isPass);
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
    public static String post_stuUpdate(String stuNo, String stuPw, String StuTel, String stuAdr,String feature,String imgStr) {
        Map<String, String> params = new HashMap<>();
        try {
            params.put("stuNo", stuNo);
            params.put("stuPw", stuPw);
            params.put("StuTel", StuTel);
            params.put("stuAdr", stuAdr);
            params.put("feature", feature);
            params.put("imgStr", imgStr);
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
