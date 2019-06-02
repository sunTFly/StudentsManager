package fragment_me.class_table;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentsmanager.R;

import java.util.List;

public class ClasTbAdapter extends BaseAdapter {

	Context context;
	List<ClassTbInfo> list;

	public ClasTbAdapter(Context context, List<ClassTbInfo> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_classtb, null);
			vh.classTb = (TextView) convertView.findViewById(R.id.item_classtb_tv);
			vh.ll = (LinearLayout) convertView.findViewById(R.id.item_classtb_ll);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		AbsListView.LayoutParams param = new AbsListView.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, getPixelsFromDp(70));
		convertView.setLayoutParams(param);

		String name = list.get(position).getClassName();
		String room = list.get(position).getClassRoom();
		int color = list.get(position).getColor();

		if (name == null){

		}else{
			vh.classTb.setText(name + "\n" + room);
			GradientDrawable gd= (GradientDrawable) vh.ll.getBackground();
			gd.setColor(color);
		}

		return convertView;
	}

	private class ViewHolder {
		TextView classTb;
		LinearLayout ll;
	}

	private int getPixelsFromDp(int size){
		final float scale = context.getResources().getDisplayMetrics().density;
		return  (int) (size * scale + 0.5f);
	}

}
