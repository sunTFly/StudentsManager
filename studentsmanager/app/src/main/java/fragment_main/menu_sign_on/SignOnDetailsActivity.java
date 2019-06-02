package fragment_main.menu_sign_on;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.RegisterAndRecognizeActivity;
import com.example.studentsmanager.activity.WelcomeActivity;
import com.example.studentsmanager.activity.maputils.MapUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fragment_main.menu_night.NightActivity;
import util.HttpUtil;
import util.JsonUtils;
import util.LocalTime;

public class SignOnDetailsActivity extends AppCompatActivity implements AMapLocationListener {

    private Context context;
    private String className, classRoom, teacher, classTime, days, nowDays, teaTel,teaNo;
    private int stateType;
    private TextView tv_className, tv_teacher, tv_teacherphone, tv_classRoom, tv_classTime, tv_state, tv_yn, tv_distance;
    private Button btn_state;
    private ImageView iv_yn,iv_return;
    private boolean isWait = true, isFinish = false, isOnTime = false;
    private String strStart = "00:00", strEnd = "23:00";
    private int[] colors = {R.color.lightGray, R.color.daoQing, R.color.weiGui};
    private Date startTime, endTime;
    private String str_state = "签到打卡";
    private double distance = 5000.0;
    private String[] strYn = {"已进入签到时间", "未进入签到时间", "签到时间已过"};
    private final int RecognizeFace = 2;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String[] strMsg;
    private int flag = -1;
    private double mainLongitude = 108.4505, mainLatitude = 30.7510;

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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_signon_details);

        context = SignOnDetailsActivity.this;

        getData();

        new stuSignIn().execute("issign",teaNo);

        initView();

        initDate();

        initListener();

    }

    private void getData() {
        Intent intent = getIntent();
        className = intent.getStringExtra("className");
        classRoom = intent.getStringExtra("classRoom");
        teacher = intent.getStringExtra("teacher");
        classTime = intent.getStringExtra("classTime");
        stateType = intent.getIntExtra("stateType", 3);
        days = intent.getStringExtra("days");
        teaTel = intent.getStringExtra("teaTel");
        teaNo = intent.getStringExtra("teaNo");
        nowDays = SignOnActivity.getDate(2);
    }

    private void initView() {
        tv_className = (TextView) findViewById(R.id.signon_details_className);
        tv_teacher = (TextView) findViewById(R.id.signon_details_teacher);
        tv_teacherphone = (TextView) findViewById(R.id.signon_details_teacherphone);
        tv_classRoom = (TextView) findViewById(R.id.signon_details_classRoom);
        tv_classTime = (TextView) findViewById(R.id.signon_details_classTime);
        tv_state = (TextView) findViewById(R.id.signon_details_state);
        btn_state = (Button) findViewById(R.id.signon_details_btn_signon);
        btn_state.setEnabled(false);
        tv_yn = (TextView) findViewById(R.id.signon_details_tv_yn);
        iv_yn = (ImageView) findViewById(R.id.signon_details_iv_yn);
        tv_distance = (TextView) findViewById(R.id.signon_details_tv_distance);
        iv_return = (ImageView) findViewById(R.id.signon_details_return);

    }

    private void initDate() {

        try {
            //strEnd = classTime + 2;
            //strStart = dateMinut(classTime,-3);
            startTime = new SimpleDateFormat("HH:mm").parse(strStart);
            endTime = new SimpleDateFormat("HH:mm").parse(strEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_className.setText(className);
        tv_teacher.setText(teacher);
        tv_classRoom.setText(classRoom);
        tv_classTime.setText(classTime);
        tv_teacherphone.setText(teaTel);
        check(stateType);

        if (compare_date(nowDays, days) == 0) {
            compareTime();
        } else if (compare_date(nowDays, days) == 1) {
            tv_yn.setText(strYn[2]);
            isOnTime = false;
            isWait = false;
            isFinish = true;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() { // 在子线程中开启子线程
                            //定位模拟
                            Location();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isFinish) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() { // 在子线程中开启子线程
                                compareTime();
                            }
                        });
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isWait) {
                        btn_state.setText(str_state + "\n" + getTimeHMS());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void initListener() {

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOnDetailsActivity.this.finish();
                return;
            }
        });

        btn_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (distance <= 5000) {
                    //人脸识别
                    String name = WelcomeActivity.studentsName;
                    String num = WelcomeActivity.studentsNum;
                    Intent intent = new Intent();
                    intent.setClass(context, RegisterAndRecognizeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("registerStatus", 2);
                    bundle.putString("u_name", name);
                    bundle.putString("u_num", num);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, RecognizeFace);
                } else {
                    Toast.makeText(context, "未进入签到范围", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RecognizeFace && resultCode == Activity.RESULT_OK) {
            String date = LocalTime.getLocalTDate();
            String jiesu = timeToJiesu(classTime);
            new stuSignIn().execute(WelcomeActivity.studentsNum, jiesu, date);
            check(2);
            isFinish = true;
            isWait = false;
            btn_state.setEnabled(false);
            Toast.makeText(context, "签到成功", Toast.LENGTH_SHORT).show();
        }
    }

    private String getTimeHMS() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return df.format(date);
    }

    private String getTimeHM() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        return df.format(date);
    }

    public static int compare_date(String DATE1, String DATE2) {


        DateFormat df = new SimpleDateFormat("MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    private void changeColor(int i) {
        if (i == 1 || i == 3) {
            check(i);
        }
    }

    private void check(int i) {
        int color;
        GradientDrawable gd;
        switch (i) {
            case 1:
                tv_state.setText("未签到");
                tv_state.setTextColor(context.getResources().getColor(R.color.gray));
                color = ContextCompat.getColor(context, R.color.gray);
                gd = (GradientDrawable) btn_state.getBackground();
                gd.setColor(color);
                break;
            case 2:
                tv_state.setText("已签到");
                tv_state.setTextColor(context.getResources().getColor(R.color.daoQing));
                color = ContextCompat.getColor(context, R.color.daoQing);
                gd = (GradientDrawable) btn_state.getBackground();
                gd.setColor(color);
                tv_yn.setText(strYn[2]);
                btn_state.setEnabled(false);
                isWait = false;
                isFinish = true;
                break;
            case 3:
                tv_state.setText("未签到");
                tv_state.setTextColor(context.getResources().getColor(R.color.weiGui));
                color = ContextCompat.getColor(context, R.color.weiGui);
                gd = (GradientDrawable) btn_state.getBackground();
                gd.setColor(color);
                tv_yn.setText(strYn[2]);
                isWait = false;
                isFinish = true;
                break;
        }
    }

    public String timeToJiesu(String classTime) {
        String jiesu = "";
        switch (classTime) {
            case "8:20":
                jiesu = "1";
                break;
            case "10:20":
                jiesu = "2";
                break;
            case "14:20":
                jiesu = "3";
                break;
            case "16:20":
                jiesu = "4";
                break;
            case "19:00":
                jiesu = "5";
                break;
            case "19:55":
                jiesu = "6";
                break;
            case "20:50":
                jiesu = "7";
                break;
        }
        return jiesu;

    }

    public void compareTime() {
        Date nowTime = null;
        try {
            nowTime = new SimpleDateFormat("HH:mm").parse(getTimeHM());
            if (stateType != 2){
                switch (NightActivity.isEffectiveDate(nowTime, startTime, endTime)) {
                    case 1:
                        btn_state.setEnabled(true);
                        isOnTime = true;
                        changeColor(1);
                        tv_yn.setText(strYn[0]);
                        break;
                    case 2:
                        btn_state.setEnabled(false);
                        changeColor(2);
                        tv_yn.setText(strYn[1]);
                        break;
                    case 3:
                        btn_state.setEnabled(false);
                        isFinish = true;
                        isWait = false;
                        changeColor(3);
                        tv_yn.setText(strYn[2]);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Location() {
        // TODO Auto-generated method stub
        try {
            locationClient = new AMapLocationClient(context);
            locationOption = new AMapLocationClientOption();
            // 设置定位模式为高精度
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置是否强制刷新WIFI，默认为强制刷新
            locationOption.setWifiActiveScan(true);
            //模拟定位
            locationOption.setMockEnable(true);
            // 设置定位间隔,单位毫秒,默认为2000ms
            locationOption.setInterval(2000);
            // 设置定位监听
            locationClient.setLocationListener(this);
            locationOption.setOnceLocation(false);//设置为多次定位
            locationClient.setLocationOption(locationOption);// 设置定位参数
            // 启动定位
            locationClient.startLocation();
            mHandler.sendEmptyMessage(MapUtils.MSG_LOCATION_START);
        } catch (Exception e) {
            // Toast.makeText(com.example.studentsmanager.activity.MainActivity.this, "定位失败", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            Message msg = mHandler.obtainMessage();
            msg.obj = loc;
            msg.what = MapUtils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                //定位完成
                case MapUtils.MSG_LOCATION_FINISH:
                    String result = "";
                    try {
                        AMapLocation loc = (AMapLocation) msg.obj;
                        result = MapUtils.getLocationStr(loc, 1);
                        strMsg = result.split(",");
                        //Toast.makeText(com.example.studentsmanager.activity.MainActivity.this, "定位成功", Toast.LENGTH_LONG).show();
                        //tv_distance.setText("地址：" + strMsg[0] + "\n" + "经    度：" + strMsg[1] + "\n" + "纬    度：" + strMsg[1]);
                        distance = MapUtils.getDistance(mainLongitude, mainLatitude, Double.valueOf(strMsg[1]), Double.valueOf(strMsg[2]));
                        tv_distance.setText("距离教室" + distance + "米\n需要50米之内才能签到");
                        if (distance <= 5000 && isOnTime) {
                            iv_yn.setImageResource(R.mipmap.icon_gou);
                        } else {
                            iv_yn.setImageResource(R.mipmap.icon_cha);
                        }
                    } catch (Exception e) {
                        //Toast.makeText(com.example.studentsmanager.activity.MainActivity.this, "定位失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        }

        ;

    };

    private class stuSignIn extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            if (arg[0].equals("issign")) {
                flag = 1;
                String issign = HttpUtil.post_issign(arg[1]);
                return issign;
            }
            return HttpUtil.post_signIn(arg[0], arg[1], arg[2]);

        }

        @Override
        protected void onPostExecute(String success) {

            if (flag == 1){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(success);
                    mainLongitude = Double.valueOf(String.valueOf(jsonObject.get("Longitude")));
                    mainLatitude = Double.valueOf(String.valueOf(jsonObject.get("Latitude")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }

}
