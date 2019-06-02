package fragment_me.night_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentsmanager.R;

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
			vh.sName = (TextView) convertView.findViewById(R.id.night_records_Item_tv_name);
			vh.date = (TextView) convertView.findViewById(R.id.night_records_Item_tv_date);
			vh.time = (TextView) convertView.findViewById(R.id.night_records_Item_tv_time);
			vh.state = (TextView) convertView.findViewById(R.id.night_records_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getsName();
		String date = list.get(position).getDate();
		String time = list.get(position).getTime();
		int state = list.get(position).getState();

		switch(state) {
			case 1:
				vh.state.setText("未打卡");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray));
				break;
			case 2:
				vh.state.setText("到勤");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing));
				break;
			case 3:
				vh.state.setText("未归");
				vh.state.setTextColor(context.getResources().getColor(R.color.weiGui));
				break;
		}


		vh.sName.setText(name);
		vh.date.setText(date);
		vh.time.setText(time);
		return convertView;
	}

	private class ViewHolder {
		TextView sName;
		TextView date;
		TextView time;
		TextView state;
	}

}
