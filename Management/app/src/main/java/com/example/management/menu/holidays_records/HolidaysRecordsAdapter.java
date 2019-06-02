package com.example.management.menu.holidays_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;

import java.util.List;

public class HolidaysRecordsAdapter extends BaseAdapter {

	Context context;
	List<HolidaysRecordsInfo> list;

	public HolidaysRecordsAdapter(Context context, List<HolidaysRecordsInfo> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_holidays_records, null);
			vh.student = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_student);
			vh.startDate = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_startDate);
			vh.yuanxi = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_yuanxi);
			vh.state = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getsName();
		String yuanxi = list.get(position).getYuanxi();
		String startDate = list.get(position).getStartDate();
		int stateType = list.get(position).getStateType();

		switch(stateType) {
			case 1:
				vh.state.setText("通过");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing, null));
				break;
			case 2:
				vh.state.setText("未通过");
				vh.state.setTextColor(context.getResources().getColor(R.color.weiGui,null));
				break;
			case 0:
				vh.state.setText("未审核");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray,null));
				break;
		}

		vh.student.setText(name);
		vh.yuanxi.setText(yuanxi);
		vh.startDate.setText(startDate);
		return convertView;
	}

	private class ViewHolder {
		TextView student;
		TextView startDate;
		TextView yuanxi;
		TextView state;
	}

}
