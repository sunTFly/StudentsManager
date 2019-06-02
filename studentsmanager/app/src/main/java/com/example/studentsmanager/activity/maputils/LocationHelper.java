package com.example.studentsmanager.activity.maputils;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.studentsmanager.R;

import fragment_main.menu_night.NightActivity;


public class LocationHelper implements AMapLocationListener {
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String[] strMsg;
    private Context context;
    private TextView tv_yn;
    private ImageView iv_yn;
    private final double mainLongitude = 108.4505,mainLatitude = 30.7510;
    private double distance;

    public LocationHelper(Context context,TextView tv_yn,ImageView iv_yn){
        this.context = context;
        this.iv_yn = iv_yn;
        this.tv_yn = tv_yn;
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
        public void dispatchMessage(android.os.Message msg) {
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
}

