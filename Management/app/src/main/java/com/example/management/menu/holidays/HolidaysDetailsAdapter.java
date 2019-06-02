package com.example.management.menu.holidays;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.management.R;

import java.util.List;
import java.util.Map;

public class HolidaysDetailsAdapter extends BaseAdapter {

	Context context;
	List<Map<String,Object>> list;

	public HolidaysDetailsAdapter(Context context, List<Map<String,Object>> list) {
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
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_holidays_img_reason, null);
			vh.img = (ImageView) convertView.findViewById(R.id.item_holidays_img);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		Bitmap bit = (Bitmap) list.get(position).get("img");

		vh.img.setImageBitmap(bit);

		return convertView;
	}

	private class ViewHolder {
		ImageView img;
	}

}
