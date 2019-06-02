package fragment_main.menu_holidays;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.studentsmanager.R;

import java.util.List;
import java.util.Map;

public class HolidaysAdapter extends BaseAdapter {

	Context context;
	List<Map<String,Object>> list;

	public HolidaysAdapter(Context context, List<Map<String,Object>> list) {
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
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_holidays_img_reason, null);
			vh.img = (ImageView) convertView.findViewById(R.id.item_holidays_img);
			vh.img_remove = (ImageView) convertView.findViewById(R.id.item_holidays_cha);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		Bitmap bit = (Bitmap) list.get(position).get("img");

		vh.img.setImageBitmap(bit);
		vh.img_remove.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HolidaysActivity.imgList.remove(position);
				HolidaysActivity.dataList.remove(position);
				notifyDataSetChanged();
			}
		});




		return convertView;
	}

	private class ViewHolder {
		ImageView img;
		ImageView img_remove;
	}

}
