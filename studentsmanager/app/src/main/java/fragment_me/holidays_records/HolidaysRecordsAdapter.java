package fragment_me.holidays_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentsmanager.R;

import java.util.List;

import util.LocalTime;

public class HolidaysRecordsAdapter extends BaseAdapter {

	Context context;
	List<HolidaysIRecordsnfo> list;

	public HolidaysRecordsAdapter(Context context, List<HolidaysIRecordsnfo> list) {
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
			vh.time = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_time);
			vh.startDate = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_startDate);
			vh.holidays_reason = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_text);
			vh.state = (TextView) convertView.findViewById(R.id.holidays_records_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String startDate = LocalTime.DateTimeToDate(list.get(position).getStartDate());
		String endDate =  LocalTime.DateTimeToDate(list.get(position).getEndDate());
		String reason = list.get(position).getHolidaysReason();
		int stateType = list.get(position).getStateType();

		switch(stateType) {
			case 0:
				vh.state.setText("未受理");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray));
				break;
			case 1:
				vh.state.setText("通过");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing));
				break;
			case 2:
				vh.state.setText("未通过");
				vh.state.setTextColor(context.getResources().getColor(R.color.weiGui));
				break;
		}

		vh.time.setText(startDate + " - " + endDate);
		vh.holidays_reason.setText("请假事由:" + reason);
		vh.startDate.setText(startDate);
		return convertView;
	}

	private class ViewHolder {
		TextView time;
		TextView startDate;
		TextView holidays_reason;
		TextView state;
	}

}
