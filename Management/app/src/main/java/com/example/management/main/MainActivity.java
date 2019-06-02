package com.example.management.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.WelcomeActivity;
import com.example.management.main.chat.ChatActivity;
import com.example.management.main.content.NoticeAdapter;
import com.example.management.menu.LoginActivity;
import com.example.management.menu.PerfectInfoActivity;
import com.example.management.menu.goHome.GoHomeRecordsActivity;
import com.example.management.menu.holidays.HolidaysActivity;
import com.example.management.menu.holidays_records.HolidaysRecordsActivity;
import com.example.management.menu.night_records.NightRecordsActivity;
import com.example.management.menu.signOn.SignOnActivity;
import com.example.management.menu.signon_records.SignOnRecordsActivity;
import com.example.management.util.CheckNet;
import com.example.management.util.ImageOperate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.management.util.PermisionUtils.verifyStoragePermissions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private View headerView;
    private ViewPageAdapter adapter;
    private Context context;
    private ViewPager vp_main;
    private ListView lv_chat;
    private TextView tv_menuName,tv_adminName;
    private LinearLayout ll_noNet,ll_loading;
    private ImageView iv_adminPic;
    private GridView gridView;
    private ListView lv_notice;
    private NoticeAdapter adapter_notice;
    private Button btn_reGet;
    private long exitTime = 0;
    public static List<ChatInfo> chatList;
    private List<View> list = new ArrayList<View>();
    private List<News> newsList = new ArrayList<>();
    int views[] = { R.layout.main_viewpager_content, R.layout.main_viewpager_chat};

    //使用Handler更新主线程（UI线程)
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                initNoticeViews();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 界面顶部渲染，只适用于android5.0及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //是否大于6.0
        if (Build.VERSION.SDK_INT >= 23){
            verifyStoragePermissions(this);
        }

        context = MainActivity.this;

        initView();

        initData();

        InitChatList();
        initMenuViews();

        initListener();

    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);
        vp_main = (ViewPager) findViewById(R.id.main_viewPager);
        tv_menuName = (TextView) findViewById(R.id.main_menu_name);
        tv_adminName = (TextView) headerView.findViewById(R.id.admin_name);
        iv_adminPic = (ImageView) headerView.findViewById(R.id.admin_img);


    }


    private void initData() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.getBackground().setAlpha(255);

        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < views.length; i++) {
            View view = inflater.inflate(views[i], null);
            list.add(view);
        }

        gridView = (GridView) list.get(0).findViewById(R.id.gridview);
        ll_noNet = (LinearLayout)list.get(0).findViewById(R.id.main_ll_no_net);
        lv_notice = (ListView) list.get(0).findViewById(R.id.main_notice);
        btn_reGet = (Button) list.get(0).findViewById(R.id.main_btn_reget);
        ll_loading = (LinearLayout)list.get(0).findViewById(R.id.main_ll_loading);

        checkNet();
    }

    private void initMenuViews() {

        int icon[] = {R.mipmap.icon_menu_holidays,R.mipmap.icon_menu_night,R.mipmap.icon_menu_signon};
        // 图标下的文字
        String name[] = {"请假处理", "晚归查询", "上课点到"};
        ArrayList<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            Bitmap bi = BitmapFactory.decodeStream(getResources().openRawResource(icon[i]));
            bi = ImageOperate.getRoundRectBitmap(bi,20);
            map.put("img", bi);
            map.put("text", name[i]);
            dataList.add(map);
        }

        String[] from = { "img", "text" };
        int[] to = { R.id.img, R.id.text };
        SimpleAdapter adapter = new SimpleAdapter(context, dataList, R.layout.item_main_menu, from, to);
        //通过setViewBinder接口将bitmap转化一下
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object bitmapData, String s) {
                if(view instanceof ImageView && bitmapData instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) bitmapData);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent;
                if (WelcomeActivity.isLogin){
                    switch (position){
                        case 0:
                            intent = new Intent(MainActivity.this, HolidaysActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(MainActivity.this, NightRecordsActivity.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(MainActivity.this,SignOnActivity.class);
                            startActivity(intent);
                            break;
                    }
                }else{
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initNoticeViews() {
        lv_notice.setVisibility(View.VISIBLE);
        ll_loading.setVisibility(View.GONE);
        NewsAdapter ada = new NewsAdapter(context,newsList);
        lv_notice.setAdapter(ada);
        lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(context,NewsWebActivity.class);
                intent.putExtra("web",newsList.get(position).getWebSite());
                startActivity(intent);
            }
        });
    }



    private void initListener() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        btn_reGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNet();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        adapter = new ViewPageAdapter(list, context);
        vp_main.setAdapter(adapter);
        vp_main.setOffscreenPageLimit(2);
        vp_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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
                if (arg0 == 0){
                    tv_menuName.setText("学生管理");
                }else if (arg0 == 1){
                    tv_menuName.setText("消息");
                }
            }

        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        if (WelcomeActivity.isLogin){
            if (id == R.id.nav_change) {
                Intent intent = new Intent(context, PerfectInfoActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_holidays) {
                Intent intent = new Intent(context, HolidaysRecordsActivity.class);
                startActivity(intent);
            }else if (id == R.id.nav_signon) {
                Intent intent = new Intent(context,SignOnRecordsActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_gohome) {
                Intent intent = new Intent(context,GoHomeRecordsActivity.class);
                startActivity(intent);
            }
        }else{
            Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
            return false;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void InitChatList() {
        lv_chat = (ListView)list.get(1).findViewById(R.id.message_chat_lv);
        final int picIDs[] = { R.mipmap.icon, R.mipmap.icon,
                R.mipmap.icon,R.mipmap.icon,R.mipmap.icon};
        String name[] = {"薛勇","小红","小刚","小白","小新"};
        String text[] = {"刘老师在吗","再见","在吗","呵呵","出来玩"};
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
        ChatAdapter ada = new ChatAdapter(context,chatList);
        lv_chat.setAdapter(ada);
        lv_chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (WelcomeActivity.getIsLogin(context)){
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("friendPic",picIDs[position]);
                    startActivity(intent);
                }else{
                    Toast.makeText(context,"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });
        lv_chat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                chatList.get(position).setDelete(true);
               ChatAdapter ada = new ChatAdapter(context,chatList);
                lv_chat.setAdapter(ada);
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (WelcomeActivity.getIsLogin(context)){
            setImageAName(true);
        }else{
            setImageAName(false);
        }
    }

    public void setImageAName(boolean isLogin){
        String num = WelcomeActivity.adminNum;
        String name = "未登录";
        Bitmap bit = BitmapFactory. decodeResource (context.getResources(),R.mipmap.head_sculpture_1);

        if (isLogin){
            name = WelcomeActivity.adminName;
            bit = WelcomeActivity.bitmap;
            tv_adminName.setText(name);
            iv_adminPic.setImageBitmap(bit);
        }else{
            tv_adminName.setText(name);
            iv_adminPic.setImageBitmap(bit);
            WelcomeActivity.setStudentsNum(context,"");
            WelcomeActivity.setIsLogin(context,false);
        }

    }

    /**
     *
     * 双击退出程序
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // 弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void getText() {

        new Thread() {
            public void run() {

                Document doc = null;
                for (int i = 19830; i < 19856; i++) {
                    News news = new News();
                    String titel = "",text = "";
                    Bitmap bitmap = null;
                    try {
                        doc = Jsoup.connect("http://www.sanxiau.edu.cn/info/1016/" + i + ".htm").get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        doc = null;
                    }
                    if (doc != null) {
                        news.setWebSite("http://www.sanxiau.edu.cn/info/1016/" + i + ".htm");
                        Elements links = doc.select("h1");
                        Elements pics = doc.select("img[class$=img_vsb_content]");
                        Elements contents = doc.select("div[id$=vsb_content_1001]").select("p");
                        for (Element content:contents){
                            if (content.toString().indexOf("vsbcontent_img") == -1){
                                text += content.text();
                            }
                        }
                        for (Element pic : pics){
                            //linkText += pic.attr("abs:src");
                            Bitmap bi = ImageOperate.getbitmap(pic.attr("abs:src"));
                            bitmap = ImageOperate.getDiskBitmap(bi,300,300);
                        }
                        for (Element link : links) {
                            String linkHref = link.attr("href");
                            titel += link.text();
                        }
                        news.setTitle(titel);
                        news.setContent(text);
                        news.setBitmap(bitmap);
                        newsList.add(news);
                    }
                }
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }

        }.start();


    }

    private void checkNet(){

        if (!CheckNet.isNetworkConnected(context)){
            ll_noNet.setVisibility(View.VISIBLE);
            lv_notice.setVisibility(View.GONE);
            ll_loading.setVisibility(View.GONE);
        }else{
            getText();
            ll_noNet.setVisibility(View.GONE);
            lv_notice.setVisibility(View.GONE);
            ll_loading.setVisibility(View.VISIBLE);
        }
    }

}
