package fragment_message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.WelcomeActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fragment_message.chat.ChatActivity;
import util.ImageOperate;

/**
 * Created by Avecle on 2019/3/6.
 */

public class fragment_message extends Fragment{

    View view;
    private Context context;
    private ViewPager viewPager;
    private MessageViewPageAdapter adapter;
    private List<View> list;
    private List<TextView> list_menu;
    private List<TextView> list_line;
    private ListView lv_message,lv_chat;
    private RelativeLayout rl_search;
    private LinearLayout ll_chat,ll_friends;
    public static List<ChatInfo> chatList;

    int views[] = { R.layout.message_viewpager_chat, R.layout.message_viewpager_friends};
    int menus[] = { R.id.message_menu_tv_chat, R.id.message_menu_tv_friends};
    int lines[] = { R.id.message_menu_line_chat, R.id.message_menu_line_friends};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_message, null);
        
        initView();
        
        initData(inflater);

        initListener();

        InitChatList();
        InitFriendsList();

        return view;
    }

    private void initView() {
        context = getActivity();
        viewPager = (ViewPager) view.findViewById(R.id.welcome_viewPager);
        ll_chat = (LinearLayout) view.findViewById(R.id.message_chat_ll);
        ll_friends = (LinearLayout) view.findViewById(R.id.message_friends_ll);


    }

    private void initData(LayoutInflater inflater) {
        list_menu = new ArrayList<TextView>();
        list_line = new ArrayList<TextView>();
        list = new ArrayList<View>();

        for (int i = 0; i < menus.length; i++) {
            TextView tv = (TextView) view.findViewById(menus[i]);
            TextView line = (TextView) view.findViewById(lines[i]);
            list_menu.add(tv);
            list_line.add(line);
        }

        list_menu.get(0).setTextColor(Color.parseColor("#000000"));
        list_line.get(0).setBackgroundColor(Color.parseColor("#000000"));

        inflater = LayoutInflater.from(context);
        for (int i = 0; i < views.length; i++) {
            View view = inflater.inflate(views[i], null);
            list.add(view);
        }






    }


    private void initListener() {
        adapter = new MessageViewPageAdapter(list, context);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < list_menu.size(); i++) {
                    list_menu.get(i).setTextColor(Color.parseColor("#cccccc"));
                    list_line.get(i).setBackgroundColor(Color.parseColor("#ffffff"));
                }
                list_menu.get(arg0).setTextColor(Color.parseColor("#000000"));
                list_line.get(arg0).setBackgroundColor(Color.parseColor("#000000"));
            }

        });


        rl_search = (RelativeLayout) list.get(1).findViewById(R.id.search_rl);
        rl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "123", Toast.LENGTH_SHORT).show();
            }
        });

        ll_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
            }
        });

        ll_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
            }
        });

    }

    private void changeColor(int i){
        if (i == 0){
            list_menu.get(i).setTextColor(Color.parseColor("#000000"));
            list_line.get(i).setBackgroundColor(Color.parseColor("#000000"));
            list_menu.get(i+1).setTextColor(Color.parseColor("#cccccc"));
            list_line.get(i+1).setBackgroundColor(Color.parseColor("#ffffff"));
        }else {
            list_menu.get(i).setTextColor(Color.parseColor("#000000"));
            list_line.get(i).setBackgroundColor(Color.parseColor("#000000"));
            list_menu.get(i-1).setTextColor(Color.parseColor("#cccccc"));
            list_line.get(i-1).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewPager.setCurrentItem(i);
    }

    private void InitFriendsList() {
        List<Map<String, Object>> menuList;
        lv_message = (ListView)list.get(1).findViewById(R.id.message_friends_lv);
        int picIDs[] = { R.mipmap.icon_message_newfriends, R.mipmap.icon_message_myclass,
                R.mipmap.icon_message_mycorporation,R.mipmap.icon_message_teacher,R.mipmap.icon_message_info,R.mipmap.icon_message_systeminfo };
        String menus[] = {"新的朋友","我的班级","我的社团","任课教师","官方消息","系统通知"};
        int rowCount = picIDs.length;
        menuList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < rowCount; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            Bitmap bi = BitmapFactory.decodeStream(getResources().openRawResource(picIDs[i]));
            bi = ImageOperate.getRoundRectBitmap(bi,20);
            map.put("picCol",bi);
            map.put("menus",menus[i]);
            menuList.add(map);
        }
        MessageFriendsAdapter ada = new MessageFriendsAdapter(context,menuList);
        lv_message.setAdapter(ada);
        lv_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void InitChatList() {
        lv_chat = (ListView)list.get(0).findViewById(R.id.message_chat_lv);
        final int picIDs[] = { R.mipmap.icon_message_newfriends, R.mipmap.icon_message_myclass,
                R.mipmap.icon_message_mycorporation,R.mipmap.icon_message_teacher,R.mipmap.icon_message_info};
        String name[] = {"刘锋老师","小红","小刚","小白","小新"};
        String text[] = {"好的","再见","在吗","呵呵","出来玩"};
        String time[] = new String[5];
        for (int i=0;i < time.length;i++){
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            time[i] = sdf.format(d);
        }
        int rowCount = picIDs.length;
        chatList = new ArrayList<ChatInfo>();
        for (int i = 0; i < rowCount; i++) {
            ChatInfo ci = new ChatInfo();
            Bitmap bi = BitmapFactory.decodeStream(getResources().openRawResource(picIDs[i]));
            ci.setPic(bi);
            ci.setName(name[i]);
            ci.setText(text[i]);
            ci.setTime(time[i]);
            ci.setDelete(false);
            chatList.add(ci);
        }
        MessageChatAdapter ada = new MessageChatAdapter(context,chatList);
        lv_chat.setAdapter(ada);
        lv_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (WelcomeActivity.getIsLogin(context)){
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("name",chatList.get(position).getName());
                    intent.putExtra("friendPic",picIDs[position]);
                    startActivity(intent);
                }
            }
        });
        lv_chat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                chatList.get(position).setDelete(true);
                MessageChatAdapter ada = new MessageChatAdapter(context,chatList);
                lv_chat.setAdapter(ada);
                return false;
            }
        });

    }

}
