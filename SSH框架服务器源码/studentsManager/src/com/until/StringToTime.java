package com.until;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringToTime {
	public Timestamp Totime(String time) {
		Timestamp Time = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Time = new Timestamp(format.parse(time).getTime());
			return Time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Time;
		}
	}

	public Date ToDate(String time) {
		Date Time = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Time = new Date(format.parse(time).getTime());
			return Time;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Time;
		}
	}

	public String ThreeTime() {
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.MONTH, -2); // 设置为前3月
		calendar.set(Calendar.DATE, 1);
		dBefore = calendar.getTime(); // 得到前3月的时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前3月的时间
		String defaultEndDate = sdf.format(dNow); // 格式化当前时间
		return defaultStartDate;
	}
	public String TwoDayTime() {
		Date dNow = new Date(); // 当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); // 得到日历
		calendar.setTime(dNow);// 把当前时间赋给日历
		calendar.add(Calendar.DATE, -2);
		dBefore = calendar.getTime(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String defaultStartDate = sdf.format(dBefore); // 格式化前前一天的时间
		//String defaultEndDate = sdf.format(dNow); // 格式化当前时间
		//System.out.println(defaultStartDate);
		return defaultStartDate;
	}

}
