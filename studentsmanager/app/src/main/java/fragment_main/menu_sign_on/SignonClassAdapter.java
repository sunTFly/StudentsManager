package fragment_main.menu_sign_on;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentsmanager.R;

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
			vh.classRoom = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_room);
			vh.classTime = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_time);
			vh.teacher = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_teacher);
			vh.state = (TextView) convertView.findViewById(R.id.signon_class_Item_tv_state);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getClassName();
		String room = list.get(position).getClassRoom();
		String time = list.get(position).getClassTime();
		String teacher = list.get(position).getTeacher();
		int stateType = list.get(position).getStateType();

		switch(stateType) {
			case 2:
				vh.state.setText("已签到");
				vh.state.setTextColor(context.getResources().getColor(R.color.daoQing));
				break;
			case 1:
				vh.state.setText("未签到");
				vh.state.setTextColor(context.getResources().getColor(R.color.gray));
				break;
		}


		vh.className.setText(name);
		vh.classRoom.setText(room);
		vh.teacher.setText(teacher);
		vh.classTime.setText(time);
		return convertView;
	}

	private class ViewHolder {
		TextView className;
		TextView classRoom;
		TextView teacher;
		TextView classTime;
		TextView state;
	}

}
