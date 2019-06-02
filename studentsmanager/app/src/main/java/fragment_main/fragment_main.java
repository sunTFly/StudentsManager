package fragment_main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fragment_main.menu_gohome.GoHomeActivity;
import fragment_main.menu_holidays.HolidaysActivity;
import fragment_main.menu_night.NightActivity;
import fragment_main.menu_sign_on.ClassInfo;
import fragment_main.menu_sign_on.SignOnActivity;
import fragment_me.night_records.NightRecordsInfo;
import fragment_message.MessageViewPageAdapter;
import util.CheckNet;
import util.FixedSpeedScroller;
import util.GalleryTransformer;
import util.HttpUtil;
import util.ImageOperate;
import util.JsonUtils;
import util.LocalTime;

/**
 * Created by Avecle on 2019/3/6.
 */

public class fragment_main extends Fragment implements ViewPager.OnPageChangeListener {

    View view; // 分类碎片视图
    private Context context;
    private ViewPager vp; // 首页轮换图片
    private LinearLayout ll_point; // 轮换图片的圆点
    private int[] imageResIds; // 存放图片资源id的数组
    private ArrayList<ImageView> imageViews; // 存放图片的集合
    private int lastPosition; // 轮换的上一个位置
    public static boolean isRunning = false; // viewpager是否在自动轮询
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private List<NoticeInfo> dataList_notice, dataList_news; // 适配器数据
    private SimpleAdapter adapter; // 主页菜单适配器
    private ViewPager viewPager;
    private List<View> list;
    private List<TextView> list_menu;
    private List<TextView> list_line;
    private Button btn_reGet;
    int views[] = {R.layout.main_viewpager_news, R.layout.main_viewpager_news};
    int menus[] = {R.id.main_news_tv, R.id.main_notice_tv};
    int lines[] = {R.id.main_news_line, R.id.main_notice_line};
    private ListView lv_notice, lv_news;
    private LinearLayout ll_news, ll_notice;
    private FixedSpeedScroller mScroller;
    private LinearLayout ll_noNet,ll_loading;
    private List<News> newsList = new ArrayList<>();
    private List<News> noticeList = new ArrayList<>();
    private int flag = 0;

    //使用Handler更新主线程（UI线程)
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                viewPager.setVisibility(View.VISIBLE);
                ll_loading.setVisibility(View.GONE);
                initNewsViews();
            }else if(msg.what == 2){
                initNoticeViews();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main, container, false);
        context = getActivity();

        initView();

        // 初始化数据
        initData();

        // 初始化轮换viewpager
        initMenuViews();
        initNoticeViews();
        initNewsViews();

        initAdapter();

        initListener();

        // 开启图片的自动轮询
        new Thread(new Runnable() {

            @Override
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(3000);
                        if (isRunning) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() { // 在子线程中开启子线程
                                    // 往下翻一页（setCurrentItem方法用来设置ViewPager的当前页）
                                    vp.setCurrentItem(vp.getCurrentItem() + 1);
                                    mScroller.setmDuration(800);// 切换时间，毫秒
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


        return view;
    }

    private void initView() {
        viewPager = (ViewPager) view.findViewById(R.id.main_viewPager);
        ll_notice = (LinearLayout) view.findViewById(R.id.main_notice_ll);
        ll_news = (LinearLayout) view.findViewById(R.id.main_news_ll);
        ll_noNet = (LinearLayout)view.findViewById(R.id.main_ll_no_net);
        btn_reGet = (Button) view.findViewById(R.id.main_btn_reget);
        ll_loading = (LinearLayout)view.findViewById(R.id.main_ll_loading);
        gridView = (GridView) view.findViewById(R.id.gridview);
        // 初始化放小圆点的控件
        ll_point = (LinearLayout) view.findViewById(R.id.ll_point);
        // 初始化ViewPager控件
        vp = (ViewPager) view.findViewById(R.id.vp);
        // 设置ViewPager的滚动监听

        checkNet();
    }

    private void initMenuViews() {


        int icon[] = {R.mipmap.icon_main_holiday, R.mipmap.icon_main_night, R.mipmap.icon_main_signon, R.mipmap.icon_main_gohome};
        // 图标下的文字
        String name[] = {"请假", "晚归", "点到", "归家"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            Bitmap bi = BitmapFactory.decodeStream(getResources().openRawResource(icon[i]));
            bi = ImageOperate.getRoundRectBitmap(bi, 20);
            map.put("img", bi);
            map.put("text", name[i]);
            dataList.add(map);
        }

        String[] from = {"img", "text"};
        int[] to = {R.id.img, R.id.text};
        adapter = new SimpleAdapter(getContext(), dataList, R.layout.item_main_menu, from, to);
        //通过setViewBinder接口将bitmap转化一下
        adapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object bitmapData, String s) {
                if (view instanceof ImageView && bitmapData instanceof Bitmap) {
                    ImageView i = (ImageView) view;
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
                if (WelcomeActivity.getIsLogin(context)) {
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), HolidaysActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            String date = LocalTime.getLocalTDate();
                            String num = WelcomeActivity.studentsNum;
                            new NightState().execute("NightState", num, date);
                            break;
                        case 2:
                            new NightState().execute("signIn", WelcomeActivity.studentsNum, "2018-08-21 00:00:00", "1");
                            break;
                        case 3:
                            intent = new Intent(getActivity(), GoHomeActivity.class);
                            startActivity(intent);
                    }
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initNewsViews() {
        lv_news = (ListView) list.get(0).findViewById(R.id.main_notice);
        NewsAdapter adapter_news = new NewsAdapter(context,newsList);
        lv_news.setAdapter(adapter_news);
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
               Intent intent = new Intent(context,NewsWebActivity.class);
               intent.putExtra("web",newsList.get(position).getWebSite());
               startActivity(intent);
            }
        });

    }

    private void initNoticeViews() {
        lv_notice = (ListView) list.get(1).findViewById(R.id.main_notice);
        NewsAdapter ada = new NewsAdapter(context,noticeList);
        lv_notice.setAdapter(ada);
        lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Intent intent = new Intent(context,NewsWebActivity.class);
                intent.putExtra("web",noticeList.get(position).getWebSite());
                startActivity(intent);
            }
        });

    }

    private void initData() {

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

        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < views.length; i++) {
            View view = inflater.inflate(views[i], null);
            list.add(view);
        }

        // 初始化填充ViewPager的图片资源
        imageResIds = new int[]{R.mipmap.main_vg_1, R.mipmap.main_vg_2, R.mipmap.main_vg_3,
                R.mipmap.main_vg_4, R.mipmap.main_vg_5};
        // 保存图片资源的集合
        imageViews = new ArrayList<>();
        ImageView imageView;
        View pointView;

        // 循环遍历图片资源，然后保存到集合中
        for (int i = 0; i < imageResIds.length; i++) {
            // 添加图片到集合中
            imageView = new ImageView(getContext());
            Bitmap bi = BitmapFactory.decodeStream(getResources().openRawResource(imageResIds[i]));
            bi = ImageOperate.getRoundRectBitmap(bi, 30);
            imageView.setImageBitmap(bi);
            imageViews.add(imageView);

            // 加小白点，指示器（这里的小圆点定义在了drawable下的选择器中了，也可以用小图片代替）
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.point_selector); // 使用选择器设置背景
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(16, 16);
            if (i != 0) {
                // 如果不是第一个点，则设置点的左边距
                layoutParams.leftMargin = 10;
            }
            pointView.setEnabled(false); // 默认都是暗色的
            ll_point.addView(pointView, layoutParams);
        }
    }


    private void initListener() {

        btn_reGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNet();
            }
        });

        vp.addOnPageChangeListener(this);

        MessageViewPageAdapter vpAdapter = new MessageViewPageAdapter(list, context);
        viewPager.setAdapter(vpAdapter);
        viewPager.setCurrentItem(0);
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

        ll_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
            }
        });

        ll_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
            }
        });
    }

    private void changeColor(int i) {
        if (i == 0) {
            list_menu.get(i).setTextColor(Color.parseColor("#000000"));
            list_line.get(i).setBackgroundColor(Color.parseColor("#000000"));
            list_menu.get(i + 1).setTextColor(Color.parseColor("#cccccc"));
            list_line.get(i + 1).setBackgroundColor(Color.parseColor("#ffffff"));
        } else {
            list_menu.get(i).setTextColor(Color.parseColor("#000000"));
            list_line.get(i).setBackgroundColor(Color.parseColor("#000000"));
            list_menu.get(i - 1).setTextColor(Color.parseColor("#cccccc"));
            list_line.get(i - 1).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        viewPager.setCurrentItem(i);
    }

    /*
     * 初始化适配器
     */
    private void initAdapter() {
        ll_point.getChildAt(0).setEnabled(true); // 初始化控件时，设置第一个小圆点为亮色
        lastPosition = 0; // 设置之前的位置为第一个
        vp.setAdapter(new MyPagerAdapter());
        vp.setPageTransformer(true, new GalleryTransformer());
        vp.setPageMargin(30);
        // 设置默认显示中间的某个位置（这样可以左右滑动），这个数只有在整数范围内，可以随便设置
        vp.setCurrentItem(5000000); // 显示5000000这个位置的图片
        try {
            // 通过class文件获取mScroller属性
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(vp.getContext(), new AccelerateInterpolator());
            mField.set(vp, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 自定义适配器，继承自PagerAdapter
     */
    class MyPagerAdapter extends PagerAdapter {

        // 返回显示数据的总条数，为了实现无限循环，把返回的值设置为最大整数
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 指定复用的判断逻辑，固定写法：view == object
        @Override
        public boolean isViewFromObject(View view, Object object) {
            // 当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
            return view == object;
        }

        // 返回要显示的条目内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // container 容器 相当于用来存放imageView
            // 从集合中获得图片
            int newPosition = position % 5; // 数组中总共有5张图片，超过数组长度时，取摸，防止下标越界
            ImageView imageView = imageViews.get(newPosition);
            // 把图片添加到container中
            container.addView(imageView);
            // 把图片返回给框架，用来缓存
            return imageView;
        }

        // 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object:刚才创建的对象，即要销毁的对象
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int position) {
        // 当前的位置可能很大，为了防止下标越界，对要显示的图片的总数进行取余
        int newPosition = position % 5;
        // 设置小圆点为高亮或暗色
        ll_point.getChildAt(lastPosition).setEnabled(false);
        ll_point.getChildAt(newPosition).setEnabled(true);
        lastPosition = newPosition; // 记录之前的点
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private class NightState extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            if (arg[0].equals("NightState")) {
                String leaveRecord = HttpUtil.post_backstate(arg[1], arg[2]);
                flag = 1;
                return leaveRecord;
            } else if (arg[0].equals("btnBackRecord")) {
                String backRecord = HttpUtil.post_backRecord(arg[1]);
                return backRecord;
            } else if (arg[0].equals("signIn")) {
                flag = 2;
                String signIn = HttpUtil.post_signInRecord(arg[1], arg[2], arg[3]);
                return signIn;
            } else if (arg[0].equals("weekclass")) {
                String weekclass = HttpUtil.post_weekclass(arg[0]);
                return weekclass;
            }
            return "返回dobanck";
        }

        @Override
        protected void onPostExecute(String success) {
            Intent intent;
            JSONObject jsonObject = null;
            if (flag == 1) {
                try {
                    jsonObject = new JSONObject(success);
                    String backTime = (String) jsonObject.get("backTime");
                    int state = (int) jsonObject.get("state");
                    intent = new Intent(getActivity(), NightActivity.class);
                    intent.putExtra("backtime", backTime);
                    intent.putExtra("state", state);
                    startActivity(intent);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (flag == 2) {
                intent = new Intent(getActivity(), SignOnActivity.class);
                intent.putExtra("success",success);
                startActivity(intent);
            }
        }
    }

    public void getText() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                Document doc_notice = null;

                for (int j = 19840;j < 19853;j++){
                    News news = new News();
                    String titel = "",text = "";
                    Bitmap bitmap = null;
                    try {
                        doc_notice = Jsoup.connect("http://www.sanxiau.edu.cn/info/1017/" + j + ".htm").get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        doc_notice = null;
                    }
                    if (doc_notice != null) {
                        news.setWebSite("http://www.sanxiau.edu.cn/info/1017/" + j + ".htm");
                        Elements links = doc_notice.select("h1");
                        Elements pics = doc_notice.select("img[class$=img_vsb_content]");
                        Elements contents = doc_notice.select("div[id$=vsb_content]").select("p");
                        for (Element content:contents){
                            if (content.toString().indexOf("vsb_content_img") == -1){
                                text += content.text();
                            }
                        }
                        for (Element pic : pics){
                            //linkText += pic.attr("abs:src");
                            Bitmap bi = ImageOperate.getbitmap(pic.attr("abs:src"));
                            bitmap = ImageOperate.getDiskBitmap(bi,300,300);
                        }
                        for (Element link : links) {
                            titel += link.text();
                        }
                        news.setTitle(titel);
                        news.setContent(text);
                        news.setBitmap(bitmap);
                        noticeList.add(news);
                    }
                    Message msg = handler.obtainMessage();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }
            }
        }).start();

        new Thread() {
            public void run() {

                Document doc = null;

                for (int i = 19850; i < 19867; i++) {
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
            viewPager.setVisibility(View.GONE);
            ll_loading.setVisibility(View.GONE);
        }else{
            getText();
            ll_noNet.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            ll_loading.setVisibility(View.VISIBLE);
        }
    }
}
