package fragment_main.menu_night;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.MainActivity;
import com.example.studentsmanager.activity.RegisterAndRecognizeActivity;
import com.example.studentsmanager.activity.WelcomeActivity;
import com.example.studentsmanager.activity.maputils.LocationHelper;
import com.example.studentsmanager.activity.maputils.MapUtils;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import util.HttpUtil;
import util.ImageOperate;
import util.JsonUtils;
import util.LocalTime;

public class NightActivity extends AppCompatActivity implements AMapLocationListener {

    private Context context;
    private Button btn_signon;
    private ImageView iv_pic, iv_yn ,iv_return;
    private TextView tv_name, tv_num, tv_state, tv_yn;
    private RelativeLayout rl_night;
    private String str_state = "签到打卡";
    public boolean isWait = true,isFinish = false;
    public static boolean isIn = false;
    private String[] strYn = {"已进入签到范围:xxxxx", "如长时间未定位请开启GPS"};
    private Date startTime, endTime;
    private String strStart = "00:00:00",strEnd = "23:00:00";
    private int[] colors = {R.color.lightGray,R.color.daoQing,R.color.weiGui};
    private String[] strState = {"未打卡","到勤","未归"};
    private final  int RecognizeFace = 2;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String[] strMsg;
    private final double mainLongitude = 108.4505,mainLatitude = 30.7510;
    private double distance;
    int i = 1;
    private int state;
    private String stateTime;

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
        setContentView(R.layout.activity_night);

        context = NightActivity.this;

        getIntentData();

        initView();

        initDate();

        initListener();


    }

    private void getIntentData() {
        state = getIntent().getIntExtra("state",1) - 1;
        stateTime = getIntent().getStringExtra("backtime");
        if (!stateTime.equals("")){
            stateTime = LocalTime.DateTimeToTime(stateTime);
    }
    }

    private void initView() {
        GradientDrawable gd;
        btn_signon = (Button) findViewById(R.id.night_btn_signon);
        btn_signon.setEnabled(false);
        gd= (GradientDrawable) btn_signon.getBackground();
        gd.setColor(ContextCompat.getColor(context,R.color.back));
        iv_pic = (ImageView) findViewById(R.id.night_pic);
        iv_yn = (ImageView) findViewById(R.id.night_iv_yn);
        tv_name = (TextView) findViewById(R.id.tv_night_name);
        tv_num = (TextView) findViewById(R.id.tv_night_num);
        tv_state = (TextView) findViewById(R.id.tv_night_state);
        gd= (GradientDrawable) tv_state.getBackground();
        gd.setColor(ContextCompat.getColor(context,colors[state]));
        tv_yn = (TextView) findViewById(R.id.night_tv_yn);
        tv_yn.setText(strYn[1]);
        rl_night = (RelativeLayout) findViewById(R.id.rl_night);
        if (state != 0){
            rl_night.setBackgroundColor(ContextCompat.getColor(context,colors[state]));
            tv_state.setText(strState[state]);
            btn_signon.setText(str_state + "\n" + stateTime);
            gd= (GradientDrawable) btn_signon.getBackground();
            gd.setColor(ContextCompat.getColor(context,colors[state]));
            isFinish = true;
            isWait = false;
        }
        iv_return = (ImageView) findViewById(R.id.night_return);
    }

    private void initDate() {

        initResource();

        try {
            startTime = new SimpleDateFormat("HH:mm:ss").parse(strStart);
            endTime = new SimpleDateFormat("HH:mm:ss").parse(strEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (isWait) {
                        btn_signon.setText(str_state + "\n" + getTimeHMS());
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

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
                                Date nowTime = null;
                                try {
                                    nowTime = new SimpleDateFormat("HH:mm:ss").parse(getTimeHMS());
                                    switch (isEffectiveDate(nowTime, startTime, endTime)) {
                                        case 1:
                                            btn_signon.setEnabled(true);
                                            state = 1;
                                            break;
                                        case 2:
                                            btn_signon.setEnabled(false);
                                            break;
                                        case 3:
                                            btn_signon.setEnabled(false);
                                            state = 2;
                                            isFinish = true;
                                            isWait = false;
                                            changeColor(2);
                                            break;
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    private void initListener() {

        btn_signon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIn){
                    //人脸识别模块
                    String name=tv_name.getText().toString();
                    String num=tv_num.getText().toString();
                    Intent intent=new Intent();
                    intent.setClass(context,RegisterAndRecognizeActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putInt("registerStatus",2);
                    bundle.putString("u_name",name);
                    bundle.putString("u_num",num);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,RecognizeFace);
                }else {
                    Toast.makeText(context,"未在签到范围内",Toast.LENGTH_SHORT).show();
                }
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NightActivity.this.finish();
            }
        });

    }

    private String getTimeHMS() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return df.format(date);
    }

    public static int isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        //1:两个时间之间
        //2:小于第一个时间
        //3:大于第二个时间
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return 1;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return 1;
        } else if (date.before(begin)) {
            return 2;
        } else if (date.after(end)) {
            return 3;
        } else {
            return 0;
        }
    }

    private void changeColor(int i){
        int color = ContextCompat.getColor(context,colors[i]);
        GradientDrawable gd= (GradientDrawable) btn_signon.getBackground();
        gd.setColor(color);
        rl_night.setBackgroundColor(color);
        gd= (GradientDrawable) tv_state.getBackground();
        gd.setColor(color);
        tv_state.setText(strState[i]);
    }

    private void initResource(){
        String num = WelcomeActivity.studentsNum;
        Bitmap bi = WelcomeActivity.headImg;
        String name = WelcomeActivity.studentsName;
        iv_pic.setImageBitmap(bi);
        tv_name.setText(name);
        tv_num.setText(num);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RecognizeFace && resultCode == Activity.RESULT_OK) {
             isFinish = true;
             isWait = false;
             if (state == 1){
                 changeColor(1);
             }
             String num = WelcomeActivity.studentsNum;
             String dateTime = LocalTime.getDateAndTime();
             String date =LocalTime.getLocalTDate();
            new StuBack().execute(num,dateTime,date);
             btn_signon.setEnabled(false);
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
                        //textView.setText("地址：" + strMsg[0] + "\n" + "经    度：" + strMsg[1] + "\n" + "纬    度：" + strMsg[1]);
                        distance = MapUtils.getDistance(mainLongitude,mainLatitude,Double.valueOf(strMsg[1]),Double.valueOf(strMsg[2]));
                        tv_yn.setText("距离" + distance + "米");
                        if (distance <= 5000){
                            iv_yn.setImageBitmap(BitmapFactory.decodeResource (context.getResources(),R.mipmap.icon_gou));
                            NightActivity.isIn = true;
                        }else{
                            iv_yn.setImageBitmap(BitmapFactory.decodeResource (context.getResources(),R.mipmap.icon_cha));
                            NightActivity.isIn = false;
                        }
                    } catch (Exception e) {
                        //Toast.makeText(com.example.studentsmanager.activity.MainActivity.this, "定位失败", Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    break;
            }
        };

    };
    private class StuBack extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
           return HttpUtil.post_back(arg[0], arg[1], arg[2]);
        }

        @Override
        protected void onPostExecute(String success) {

        }
    }
}
