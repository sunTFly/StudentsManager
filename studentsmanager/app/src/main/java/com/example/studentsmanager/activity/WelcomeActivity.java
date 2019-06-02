package com.example.studentsmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.studentsmanager.R;

import java.io.ByteArrayOutputStream;

import util.CheckNet;
import util.PermisionUtils;


public class WelcomeActivity extends AppCompatActivity{

    Context context;
    public static String studentsNum,studentsName,studentState,tel,address;
    public static boolean isLogin = false;
    public static Bitmap headImg ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 界面顶部渲染，只适用于android5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        context = WelcomeActivity.this;

        setIsLogin(context,false);

        if (!CheckNet.isNetworkConnected(context)){
            Toast.makeText(context,"网络连接失败",Toast.LENGTH_SHORT).show();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(context,MainActivity.class);
                    startActivity(intent);
                    WelcomeActivity.this.finish();
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void setIsLogin(Context context,boolean is){
        SharedPreferences.Editor isLogin = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        isLogin.putBoolean("isLogin", is);
        isLogin.apply();
        WelcomeActivity.isLogin = is;
    }

    public static boolean getIsLogin(Context context){
        boolean is = false;
        SharedPreferences isLogin = context.getSharedPreferences("data", MODE_PRIVATE);
        is = isLogin.getBoolean("isLogin",false);
        return is;
    }

    public static void setStudentsNum(Context context,String studentsNum){
        SharedPreferences.Editor isLogin = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        isLogin.putString("studentsNum",studentsNum);
        isLogin.apply();
        WelcomeActivity.studentsNum = studentsNum;
    }

    public static String getStudentsNum(Context context){
        String studentsNum = "";
        SharedPreferences isLogin = context.getSharedPreferences("data", MODE_PRIVATE);
        studentsNum = isLogin.getString("studentsNum","");
        return studentsNum;
    }

    public static void setStudentsName(Context context,String studentsName){
        SharedPreferences.Editor isLogin = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        isLogin.putString("studentsName",studentsName);
        isLogin.apply();
        WelcomeActivity.studentsName = studentsName;
    }

    public static String getStudentsName(Context context){
        String studentsName = "";
        SharedPreferences isLogin = context.getSharedPreferences("data", MODE_PRIVATE);
        studentsName = isLogin.getString("studentsName","");
        return studentsName;
    }

    public static void setNumAndPass(Context context,String num,String pass){
        SharedPreferences.Editor isLogin = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        isLogin.putString("numAndPass",num + "," + pass);
        isLogin.apply();
    }

    public static String getNumAndPass(Context context){
        String numAndPass = "";
        SharedPreferences isLogin = context.getSharedPreferences("data", MODE_PRIVATE);
        numAndPass = isLogin.getString("numAndPass","");
        return numAndPass;
    }

    public static void setIsRemberNum(Context context,boolean num){
        SharedPreferences.Editor isLogin = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        isLogin.putBoolean("isRemberNum",num);
        isLogin.apply();
    }

    public static boolean getIsRemberNum(Context context){
        boolean is = false;
        SharedPreferences isLogin = context.getSharedPreferences("data", MODE_PRIVATE);
        is = isLogin.getBoolean("isRemberNum",false);
        return is;
    }

    public static void setIsRemberPass(Context context,boolean pass){
        SharedPreferences.Editor isLogin = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        isLogin.putBoolean("isRemberPass",pass);
        isLogin.apply();
    }

    public static boolean getIsRemberPass(Context context){
        boolean is = false;
        SharedPreferences isLogin = context.getSharedPreferences("data", MODE_PRIVATE);
        is = isLogin.getBoolean("isRemberPass",false);
        return is;
    }

}
