package fragment_me.signon_records;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentsmanager.R;

import java.util.List;

import fragment_main.menu_sign_on.ClassInfo;

public class SignonRecordsAdapter extends BaseAdapter {

	Context context;
	List<ClassInfo> list;

	public SignonRecordsAdapter(Context context, List<ClassInfo> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_signon_records, null);
			vh.className = (TextView) convertView.findViewById(R.id.signon_records_classname);
			vh.classRoom = (TextView) convertView.findViewById(R.id.signon_records_classroom);
			vh.teacher = (TextView) convertView.findViewById(R.id.signon_records_teacher);
			vh.state = (TextView) convertView.findViewById(R.id.signon_records_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getClassName();
		String room = list.get(position).getClassRoom();
		String teacher = list.get(position).getTeacher();
		int stateType = list.get(position).getStateType();

		switch(stateType) {
			case 1:
				vh.state.setText("未签到");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray,null));
				break;
			case 2:
				vh.state.setText("已签到");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing, null));
				break;
		}


		vh.className.setText(name);
		vh.classRoom.setText(room);
		vh.teacher.setText(teacher);
		return convertView;
	}

	private class ViewHolder {
		TextView className;
		TextView classRoom;
		TextView teacher;
		TextView state;
	}

}
