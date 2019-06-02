package com.example.management.main.content;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.util.ImageOperate;

import java.util.List;

public class NoticeAdapter extends BaseAdapter {

	Context context;
	List<NoticeInfo> list;

	public NoticeAdapter(Context context, List<NoticeInfo> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_main_notice, null);
			vh.noticePic = (ImageView) convertView.findViewById(R.id.main_notice_pic);
			vh.noticeName = (TextView) convertView.findViewById(R.id.main_notice_name);
			vh.noticeText = (TextView) convertView.findViewById(R.id.main_notice_text);
			vh.noticeDate = (TextView) convertView.findViewById(R.id.main_notice_date);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getNoticeName();
		String text = list.get(position).getNoticeText();
		String date = list.get(position).getNoticeDate();
		int picID = list.get(position).getPicID();

		Bitmap bitmap = BitmapFactory.decodeResource (null,picID);
		bitmap = ImageOperate.getDiskBitmap(bitmap,200,200);

		vh.noticePic.setImageResource(picID);
		vh.noticeName.setText(name);
		vh.noticeText.setText(text);
		vh.noticeDate.setText(date);
		return convertView;
	}

	private class ViewHolder {
		ImageView noticePic;
		TextView noticeName;
		TextView noticeText;
		TextView noticeDate;
	}

}
