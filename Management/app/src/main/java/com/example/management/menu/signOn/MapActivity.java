package com.example.management.menu.signOn;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.management.R;
import com.example.management.WelcomeActivity;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;


public class MapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {
    // AMap 是地图对象
    private AMap aMap;
    private MapView mapView;
    //显示定位经纬度的文本框
    private TextView present;
    //经纬度
    public Double lattude, logtude;
    //以前的定位点
    private LatLng oldLatLng;
    //是否是第一次定位
    private boolean isFirstLatLng;
    Marker marker;
    // 声明 AMapLocationClient 类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    // 声明 mLocationOption 对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    // 声明 mListener 对象，定位监听器
    private OnLocationChangedListener mListener = null;
    // 标识，用于判断是否只显示一次定位信息和用户重新定位
    private MyLocationStyle myLocationStyle = new MyLocationStyle();
    //该 boolean 类型的数据用于地图可以滑动
    private boolean isFirstLoc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // 获取地图控件引用
        mapView = findViewById(R.id.map);
        // 获取经纬度按钮
        present = findViewById(R.id.present);
        // 在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
            // 设置显示定位按钮 并且可以点击
            UiSettings settings = aMap.getUiSettings();
            aMap.setLocationSource(this);// 设置了定位的监听
            // 是否显示定位按钮
            settings.setMyLocationButtonEnabled(true);
            //隐藏了高德地图的 logo
            settings.setLogoBottomMargin(-50);
            aMap.setMyLocationEnabled(true);// 显示定位层并且可以触发定位,默认是flase
            //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
            aMap.setMyLocationStyle(myLocationStyle);
        }
        // 开始定位
        location();
        isFirstLatLng = true;
    }

    //发起定位
    public void teasignin(View v) {
        new position().execute("teasignin", WelcomeActivity.adminNum, String.valueOf(logtude), String.valueOf(lattude));

    }

    private void location() {
        // 初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        // 设置定位回调监听
        mLocationClient.setLocationListener(this);
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //定位的Client将会采用设备传感器计算海拔，角度和速度。
        mLocationOption.setSensorEnable(true);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否只定位一次,默认为false,连续定位
        mLocationOption.setOnceLocation(false);
        // 设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(true);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocationClient.startLocation();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        mLocationClient.stopLocation();// 停止定位
        mLocationClient.onDestroy();// 销毁定位客户端。
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState
        // (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }

    /*
     * AMapLocationListener接口只有onLocationChanged方法可以实现，
     * 用于接收异步返回的定位结果，回调参数是AMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功
                LatLng newLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                present.setText("GPS状态" + aMapLocation.getGpsAccuracyStatus() + "\n经度" + aMapLocation.getLongitude()
                        + "\n纬度" + aMapLocation.getLatitude() + "\n海拔高度" + aMapLocation.getAltitude());
                logtude = aMapLocation.getLongitude();
                lattude = aMapLocation.getLatitude();
                if (isFirstLatLng) {
                    //记录第一次的定位信息
                    oldLatLng = newLatLng;
                    isFirstLatLng = false;
                }
                // 点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(aMapLocation);
                if (isFirstLoc) {
                    // 设置缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                    // 将地图移动到定位点
                    aMap.moveCamera(CameraUpdateFactory
                            .changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    marker.showInfoWindow();// 设置默认显示一个infowinfow
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + "" + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + "" + aMapLocation.getProvince() + "" + aMapLocation.getDistrict()
                            + "" + aMapLocation.getStreet() + "" + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }

            } else {
                // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
               // Toast.makeText(getApplicationContext(), "定位失败，错误码" + aMapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "定位失败,请打开权限" , Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mListener = null;
    }

    private class position extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            if (arg[0].equals("teasignin")) {
                String teasignin = HttpUtil.post_teasignin(arg[1], arg[2], arg[3]);
                return teasignin;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String success) {
            //Toast.makeText(context,success,Toast.LENGTH_LONG).show();
            MapActivity.this.finish();
        }
    }

}
