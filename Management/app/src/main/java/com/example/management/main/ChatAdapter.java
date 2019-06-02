package com.example.management.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.management.R;

import java.util.List;

public class ChatAdapter extends BaseAdapter {

	Context context;
	List<ChatInfo> list;
	ViewHolder vh;

	public ChatAdapter(Context context, List<ChatInfo> list) {
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
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_main_chat, null);
			vh.chatPic = (ImageView) convertView.findViewById(R.id.main_chat_Item_img);
			vh.chatName = (TextView) convertView.findViewById(R.id.main_chat_Item_tv_name);
			vh.chatText = (TextView) convertView.findViewById(R.id.main_chat_Item_tv_text);
			vh.chatTime = (TextView) convertView.findViewById(R.id.main_chat_Item_tv_time);
			vh.btn_delete = (Button) convertView.findViewById(R.id.main_chat_delete);
			vh.btn_cancel = (Button) convertView.findViewById(R.id.main_chat_cancel);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		String name = (String)list.get(position).getName();
		String text = (String)list.get(position).getText();
		String time = (String)list.get(position).getTime();
		Bitmap pic = (Bitmap) list.get(position).getPic();
		final boolean isDelete = (boolean)list.get(position).isDelete();
		isDelete(isDelete);
		vh.btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isDelete(false);
				MainActivity.chatList.get(position).setDelete(false);
				notifyDataSetChanged();
			}
		});
		vh.btn_delete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.chatList.remove(position);
				Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
				notifyDataSetChanged();
			}
		});

		vh.chatPic.setImageBitmap(pic);
		vh.chatName.setText(name);
		vh.chatText.setText(text);
		vh.chatTime.setText(time);
		return convertView;
	}

	private class ViewHolder {
		ImageView chatPic;
		TextView chatName;
		TextView chatText;
		TextView chatTime;
		Button btn_delete;
		Button btn_cancel;
	}

	private void isDelete(boolean is){
		if (is){
			vh.chatPic.setVisibility(View.GONE);
			vh.chatName.setVisibility(View.GONE);
			vh.chatText.setVisibility(View.GONE);
			vh.chatTime.setVisibility(View.GONE);
			vh.btn_delete.setVisibility(View.VISIBLE);
			vh.btn_cancel.setVisibility(View.VISIBLE);
		}else{
			vh.chatPic.setVisibility(View.VISIBLE);
			vh.chatName.setVisibility(View.VISIBLE);
			vh.chatText.setVisibility(View.VISIBLE);
			vh.chatTime.setVisibility(View.VISIBLE);
			vh.btn_delete.setVisibility(View.GONE);
			vh.btn_cancel.setVisibility(View.GONE);
		}


	}

}
