package fragment_main.menu_gohome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.MainActivity;
import com.example.studentsmanager.activity.RegisterAndRecognizeActivity;
import com.example.studentsmanager.activity.WelcomeActivity;
import com.example.studentsmanager.activity.maputils.MapUtils;

import java.util.Date;

import fragment_main.menu_night.NightActivity;
import util.HttpUtil;
import util.ImageOperate;
import util.LocalTime;

public class GoHomeActivity extends AppCompatActivity implements AMapLocationListener {

    private Context context;
    private Button btn_signon;
    private ImageView iv_pic, iv_yn, iv_return;
    private TextView tv_name, tv_num, tv_address;
    private boolean isSchool = false,isIn = false;
    private final int RecognizeFace = 2;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String[] strMsg;

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
        setContentView(R.layout.activity_menu_gohome);

        context = GoHomeActivity.this;

        initView();

        initDate();

        initListener();


    }

    private void initView() {
        btn_signon = (Button) findViewById(R.id.gohome_btn_signon);
        iv_pic = (ImageView) findViewById(R.id.gohome_pic);
        tv_name = (TextView) findViewById(R.id.tv_gohome_name);
        tv_num = (TextView) findViewById(R.id.tv_gohome_num);
        iv_return = (ImageView) findViewById(R.id.gohome_return);
        tv_address = (TextView) findViewById(R.id.gohome_tv_address);

        if (WelcomeActivity.studentState.equals("1")) {
            btn_signon.setEnabled(false);
        }
    }

    private void initDate() {

        initResource();

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

    }

    private void initListener() {

        btn_signon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isIn) {
                    //人脸识别模块
                    String name = tv_name.getText().toString();
                    String num = tv_num.getText().toString();
                    Intent intent = new Intent();
                    intent.setClass(context, RegisterAndRecognizeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("registerStatus", 2);
                    bundle.putString("u_name", name);
                    bundle.putString("u_num", num);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, RecognizeFace);
                }
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoHomeActivity.this.finish();
                return;
            }
        });

    }

    private void initResource() {
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
            new BackHome().execute(WelcomeActivity.studentsNum, tv_address.getText().toString(), LocalTime.getDateAndTime());
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
                        tv_address.setText(strMsg[0]);
                        isIn = true;
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

    private class BackHome extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            return HttpUtil.post_backhome(arg[0], arg[1], arg[2]);
        }

        @Override
        protected void onPostExecute(String success) {
        }
    }

}
