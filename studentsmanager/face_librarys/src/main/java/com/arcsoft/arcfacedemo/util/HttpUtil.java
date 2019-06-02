package com.arcsoft.arcfacedemo.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {
private static final String path="http://172.20.10.8:8080/userDemo/androidLoginAction.action";
public static String post_login(String name, String password, String bitmap){
    Map<String ,String> params=new HashMap<>();
    try {
params.put("name",name);
params.put("password",password);
params.put("img",bitmap);
return sendPostRequest(path,params,"UTF-8");
    }catch (Exception e){
        e.printStackTrace();
    }
return "提交失败";
}
private static String sendPostRequest(String path,Map<String,String> params,String encoding) throws Exception{
    StringBuilder sb=new StringBuilder();
    if (params!=null && !params.isEmpty()){
        for (Map.Entry<String,String>entry:params.entrySet()){
            sb.append(entry.getKey()).append("=");
            sb.append(URLEncoder.encode(entry.getValue(),encoding));
            sb.append("&");
        }
        sb.deleteCharAt(sb.length()-1);
    }
    byte[] entity=sb.toString().getBytes();
    URL url=new URL(path);
    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
    conn.setReadTimeout(5000);
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
    conn.setRequestProperty("Content_Type","application/x-www-form-urlencoded");
    conn.setRequestProperty("Content-Length",String.valueOf(entity.length));
    OutputStream outstream=conn.getOutputStream();
    outstream.write(entity);
    if (conn.getResponseCode()==200){
        InputStream instream=conn.getInputStream();
        String text=StreamToString.readInputStream(instream);
        return text;
    }
    return "连接失败";
}

}
