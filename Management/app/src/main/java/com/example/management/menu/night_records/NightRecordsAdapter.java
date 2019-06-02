package com.example.management.menu.night_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;

import java.util.List;

public class NightRecordsAdapter extends BaseAdapter {

	Context context;
	List<NightRecordsInfo> list;

	public NightRecordsAdapter(Context context, List<NightRecordsInfo> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_night_records, null);
			vh.student = (TextView) convertView.findViewById(R.id.night_records_Item_tv_name);
			vh.date = (TextView) convertView.findViewById(R.id.night_records_Item_tv_date);
			vh.room = (TextView) convertView.findViewById(R.id.night_records_Item_tv_room);
			vh.state = (TextView) convertView.findViewById(R.id.night_records_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getsName();
		String room = list.get(position).getRoom();
		String date = list.get(position).getDate();
		int stateType = list.get(position).getState();

		switch(stateType) {
			case 1:
				vh.state.setText("未打卡");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray, null));
				break;
			case 2:
				vh.state.setText("到勤");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing,null));
				break;
			case 3:
				vh.state.setText("未归");
				vh.state.setTextColor(context.getResources().getColor(R.color.weiGui,null));
				break;
		}

		vh.student.setText(name);
		vh.room.setText(room);
		vh.date.setText(date);
		return convertView;
	}

	private class ViewHolder {
		TextView student;
		TextView room;
		TextView date;
		TextView state;
	}

}
