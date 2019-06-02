package com.example.management.menu.signOn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;

import java.util.List;

public class SignonClassAdapter extends BaseAdapter {

	Context context;
	List<ClassInfo> list;

	public SignonClassAdapter(Context context, List<ClassInfo> list) {
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
		ViewHolder vh = new ViewHolder();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_signon_class, null);
			vh.className = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_name);
			vh.classTime = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_time);
			vh.bjName = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_bjName);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getClassName();
		String time = list.get(position).getClassTime();
		String bjName = list.get(position).getBjName();

		vh.className.setText(name);
		vh.bjName.setText(bjName);
		vh.classTime.setText(time);
		return convertView;
	}

	private class ViewHolder {
		TextView className;
		TextView bjName;
		TextView classTime;
	}

}
