package fragment_me;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentsmanager.R;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

	Context context;
	List<MenuInfo> list;

	public MenuAdapter(Context context, List<MenuInfo> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_me_menu, null);
			vh.menuPic = (ImageView) convertView.findViewById(R.id.menu_Item_img);
			vh.menuName = (TextView) convertView.findViewById(R.id.menu_Item_tv);
			vh.jiantou = (ImageView) convertView.findViewById(R.id.menu_Item_img2);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = list.get(position).getMenuName();
		Bitmap pic = list.get(position).getPic();
		int jiantou = list.get(position).getJiantouId();

		vh.menuPic.setImageBitmap(pic);
		vh.menuName.setText(name);
		vh.jiantou.setImageResource(jiantou);
		return convertView;
	}

	private class ViewHolder {
		ImageView menuPic;
		TextView menuName;
		ImageView jiantou;
	}

}
