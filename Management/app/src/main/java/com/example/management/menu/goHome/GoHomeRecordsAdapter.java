package com.example.management.menu.goHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;

import java.util.List;

public class GoHomeRecordsAdapter extends BaseAdapter {

	Context context;
	List<GoHomeInfo> list;

	public GoHomeRecordsAdapter(Context context, List<GoHomeInfo> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_gohome_records, null);
			vh.student = (TextView) convertView.findViewById(R.id.gohome_records_Item_tv_sname);
			vh.num = (TextView) convertView.findViewById(R.id.gohome_records_Item_tv_snum);
			vh.state = (TextView) convertView.findViewById(R.id.gohome_records_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getsName();
		String num = list.get(position).getsNum();
		int stateType = list.get(position).getState();

		switch(stateType) {
			case 3:
				vh.state.setText("已归家");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing));
				break;
			default:
				vh.state.setText("未归家");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray));
				break;
		}

		vh.student.setText(name);
		vh.num.setText(num);
		return convertView;
	}

	private class ViewHolder {
		TextView student;
		TextView num;
		TextView state;
	}

}
