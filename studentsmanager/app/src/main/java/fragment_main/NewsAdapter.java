package fragment_main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.studentsmanager.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    Context context;
    List<News> list;

    public NewsAdapter(Context context, List<News> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_main_notice, null);
            vh.title = (TextView) convertView.findViewById(R.id.main_notice_name);
            vh.content = (TextView) convertView.findViewById(R.id.main_notice_text);
            vh.pic = (ImageView)  convertView.findViewById(R.id.main_notice_pic);
            vh.time = (TextView) convertView.findViewById(R.id.main_notice_date);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        News news = list.get(position);
        Bitmap bit = news.getBitmap();
        if (bit == null){
            vh.pic.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.mipmap.icon_no_pic));
        }else{
            vh.pic.setImageBitmap(news.getBitmap());
        }
        vh.title.setText(news.getTitle());
        vh.content.setText(news.getContent());
        SimpleDateFormat sl = new SimpleDateFormat("yyyy-MM-dd");
        vh.time.setText(sl.format(new Date()));

        return convertView;
    }

    private class ViewHolder {
        TextView title;
        TextView content;
        ImageView pic;
        TextView time;
    }

}
