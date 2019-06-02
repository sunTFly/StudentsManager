package com.example.studentsmanager.activity.maputils;

import com.amap.api.location.AMapLocation;

public class MapUtils {
    /**
     * 开始定位
     */
    public final static int MSG_LOCATION_START = 0;
    /**
     * 定位完成
     */
    public final static int MSG_LOCATION_FINISH = 1;
    /**
     * 停止定位
     */

    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @return
     */
    public synchronized static String getLocationStr(AMapLocation location, final int index) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");
            return (location.getAddress() + "," + location.getLongitude() + "," + location.getLatitude());
        } else {
            //定位失败
            sb.append("定位失败，请打开GPS" + "\n");
            return sb.toString();
        }
        //return sb.toString();
    }

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {

        return d * Math.PI / 180.0;

    }

    public static double getDistance(double lat1, double lng1, double lat2,

                                     double lng2) {

        double radLat1 = rad(lat1);

        double radLat2 = rad(lat2);

        double a = radLat1 - radLat2;

        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)

                + Math.cos(radLat1) * Math.cos(radLat2)

                * Math.pow(Math.sin(b / 2), 2)));

        s = s * EARTH_RADIUS;

        s = Math.round(s * 10000d) / 10000d;

        s = s*1000;

        return s;

    }

}
