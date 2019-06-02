package com.example.management.menu.signon_records.details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;

import java.util.List;
import java.util.Map;

public class SignOnRecordsDetailsAdapter extends BaseAdapter {

	Context context;
	List<StudentSignOnInfo> list;

	public SignOnRecordsDetailsAdapter(Context context, List<StudentSignOnInfo> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_signon_records_details, null);
			vh.sName = (TextView) convertView.findViewById(R.id.signon_records_details_Item_tv_sname);
			vh.state = (TextView) convertView.findViewById(R.id.signon_records_details_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		String name = (String)list.get(position).getsName();
		int stateType = Integer.valueOf(list.get(position).getState());

		switch(stateType) {
			case 2:
				vh.state.setText("签到");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing));
				break;
			case 1:
				vh.state.setText("未签到");
				vh.state.setTextColor(context.getResources().getColor(R.color.weiGui));
				break;
		}

		vh.sName.setText(name);
		return convertView;
	}

	private class ViewHolder {
		TextView sName;
		TextView state;
	}

}
