package fragment_message;

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
import java.util.Map;

public class MessageFriendsAdapter extends BaseAdapter {

	Context context;
	List<Map<String, Object>> list;

	public MessageFriendsAdapter(Context context, List<Map<String, Object>> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_message_friends, null);
			vh.friendsPic = (ImageView) convertView.findViewById(R.id.message_friends_Item_img);
			vh.friendsName = (TextView) convertView.findViewById(R.id.message_friends_Item_tv);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = (String)list.get(position).get("menus");
		Bitmap pic = (Bitmap) list.get(position).get("picCol");

		vh.friendsPic.setImageBitmap(pic);
		vh.friendsName.setText(name);
		return convertView;
	}

	private class ViewHolder {
		ImageView friendsPic;
		TextView friendsName;
	}

}
