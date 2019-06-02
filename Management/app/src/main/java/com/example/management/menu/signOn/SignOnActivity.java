package com.example.management.menu.signOn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.WelcomeActivity;
import com.example.management.menu.signon_records.details.SignOnRecordsDetailsActivity;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;
import com.example.management.util.LocalTimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class SignOnActivity extends Activity {

    private Context context;
    private ImageView iv_return, iv_noCLass;
    private LinearLayout ll_yesterday, ll_today, ll_tomorrow;
    private ListView lv_class;
    private List<View> list;
    private ViewPager viewPager;
    private List<TextView> list_menu;
    private List<TextView> list_line;
    private ViewPageAdapter adapter;
    private String jiesu = "";
    private List<ClassInfo> list_all = new ArrayList<>();
    private List<ClassInfo> list_lastlast = new ArrayList<>(), list_yesterday = new ArrayList<>(), list_today = new ArrayList<>();
    private int flag = -1;
    int views[] = {R.layout.siginon_viewpager_today, R.layout.siginon_viewpager_today, R.layout.siginon_viewpager_today};
    int menus[] = {R.id.sigonon_menu_tv_yesterday, R.id.sigonon_menu_tv_today, R.id.sigonon_menu_tv_tomorrow,};
    int lines[] = {R.id.sigonon_menu_line_yesterday, R.id.sigonon_menu_line_today, R.id.sigonon_menu_line_tomorrow};


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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signon);

        context = SignOnActivity.this;

        initView();

        new SignOn().execute("asclass", String.valueOf(LocalTimeUtil.getWeek()), WelcomeActivity.adminNum);

        initData();

        initListener();

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.signon_viewPager);
        ll_yesterday = (LinearLayout) findViewById(R.id.sigonon_yesterday_ll);
        ll_today = (LinearLayout) findViewById(R.id.sigonon_today_ll);
        ll_tomorrow = (LinearLayout) findViewById(R.id.sigonon_tomorrow_ll);
        iv_return = (ImageView) findViewById(R.id.signon_return);
    }


    private void initData() {
        list_menu = new ArrayList<TextView>();
        list_line = new ArrayList<TextView>();
        list = new ArrayList<View>();

        for (int i = 0; i < menus.length; i++) {
            TextView tv = (TextView) findViewById(menus[i]);
            tv.setText(getDate(i));
            TextView line = (TextView) findViewById(lines[i]);
            list_menu.add(tv);
            list_line.add(line);
        }

        list_menu.get(2).setTextColor(Color.parseColor("#000000"));
        list_line.get(2).setBackgroundColor(Color.parseColor("#000000"));

        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < views.length; i++) {
            View view = inflater.inflate(views[i], null);
            list.add(view);
        }
    }

    private void initListener() {


        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOnActivity.this.finish();
                return;
            }
        });

        adapter = new ViewPageAdapter(list, context);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(2);
        viewPager.setOffscreenPageLimit(3);
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

        ll_yesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
            }
        });
        ll_today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
            }
        });
        ll_tomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(2);
            }
        });

    }

    public static String getDate(int i) {
        Date date = null;
        //0前天日期 1昨天日期 2今天天日期
        switch (i) {
            case 0:
                date = new Date(System.currentTimeMillis() - 2 * 1000 * 60 * 60 * 24);
                break;
            case 1:
                date = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
                break;
            case 2:
                date = new Date();
                break;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");//设置日期格式
        return df.format(date);
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

    private void InitYesterdayClassList() {
        iv_noCLass = (ImageView) list.get(0).findViewById(R.id.signon_iv_noClass);
        lv_class = (ListView) list.get(0).findViewById(R.id.siginon_class_lv);
        if (list_lastlast.isEmpty()) {
            lv_class.setVisibility(View.GONE);
            iv_noCLass.setVisibility(View.VISIBLE);
            return;
        }
        SignonClassAdapter ada = new SignonClassAdapter(context, list_lastlast);
        lv_class.setAdapter(ada);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                jiesu = timeToJiesu(list_lastlast.get(position).getClassTime());
                Intent intent = new Intent(context, SignOnRecordsDetailsActivity.class);
                intent.putExtra("jiesu", jiesu);
                intent.putExtra("date", LocalTimeUtil.getLastLastTDate());
                startActivity(intent);

            }
        });

    }


    private void InitTodayClassList() {
        lv_class = (ListView) list.get(1).findViewById(R.id.siginon_class_lv);
        iv_noCLass = (ImageView) list.get(1).findViewById(R.id.signon_iv_noClass);
        if (list_yesterday.isEmpty()) {
            lv_class.setVisibility(View.GONE);
            iv_noCLass.setVisibility(View.VISIBLE);
            return;
        }
        SignonClassAdapter ada = new SignonClassAdapter(context, list_yesterday);
        lv_class.setAdapter(ada);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jiesu = timeToJiesu(list_yesterday.get(position).getClassTime());
                Intent intent = new Intent(context, SignOnRecordsDetailsActivity.class);
                intent.putExtra("jiesu", jiesu);
                intent.putExtra("date", LocalTimeUtil.getLastTDate());
                startActivity(intent);
            }
        });

    }

    private void InitTomorrowClassList() {
        lv_class = (ListView) list.get(2).findViewById(R.id.siginon_class_lv);
        iv_noCLass = (ImageView) list.get(2).findViewById(R.id.signon_iv_noClass);
        if (list_today.isEmpty()) {
            lv_class.setVisibility(View.GONE);
            iv_noCLass.setVisibility(View.VISIBLE);
            return;
        }
        SignonClassAdapter ada = new SignonClassAdapter(context, list_today);
        lv_class.setAdapter(ada);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String time = list_today.get(position).getClassTime() + ":00";
                SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
                String now = sf.format(new Date());
                int type = LocalTimeUtil.compareTime(time, now);
                if (type == 2) {
                    jiesu = timeToJiesu(list_today.get(position).getClassTime());
                    new SignOn().execute("issign", WelcomeActivity.adminNum);
                } else {
                    if (type == 1) {
                        Toast.makeText(context, "未到发起签到时间", Toast.LENGTH_SHORT).show();
                    } else if (type == 3) {
                        jiesu = timeToJiesu(list_today.get(position).getClassTime());
                        Intent intent = new Intent(context, SignOnRecordsDetailsActivity.class);
                        intent.putExtra("jiesu", jiesu);
                        intent.putExtra("date",LocalTimeUtil.getLocalTDate());
                        startActivity(intent);
                   }
                }
            }
        });

    }

    private class SignOn extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            if (arg[0].equals("asclass")) {
                flag = 0;
                String asclass = HttpUtil.post_asclass(arg[1], arg[2]);
                return asclass;
            }
            if (arg[0].equals("issign")) {
                flag = 1;
                String issign = HttpUtil.post_issign(arg[1]);
                return issign;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String success) {
            switch (flag) {
                case 0:
                    //Toast.makeText(context,success+"sss",Toast.LENGTH_SHORT).show();
                    list_all = JsonUtils.TeaClass(success, "calsslist");
                    for (int i = 0; i < list_all.size(); i++) {
                        int nowWeek = LocalTimeUtil.getWeek();
                        ClassInfo c = list_all.get(i);
                        if (nowWeek == 1) {
                            Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                            if (c.getWeek() == 1) {
                                list_today.add(c);
                            } else if (c.getWeek() == 7) {
                                list_yesterday.add(c);
                            } else if (c.getWeek() == 6) {
                                list_lastlast.add(c);
                            }
                        } else if (nowWeek == 2) {
                            if (c.getWeek() == 2) {
                                list_today.add(c);
                            } else if (c.getWeek() == 1) {
                                list_yesterday.add(c);
                            } else if (c.getWeek() == 7) {
                                list_lastlast.add(c);
                            }
                        } else if (nowWeek >= 3) {
                            if (c.getWeek() == nowWeek) {
                                list_today.add(c);
                            } else if (c.getWeek() == nowWeek - 1) {
                                list_yesterday.add(c);
                            } else if (c.getWeek() == nowWeek - 2) {
                                list_lastlast.add(c);
                            }
                        }
                    }
                    InitTodayClassList();
                    InitTomorrowClassList();
                    InitYesterdayClassList();
                    break;
                case 1:
                    try {
                        JSONObject jsonObject = new JSONObject(success);
                        String state = String.valueOf(jsonObject.get("Signstate"));
                        if (state.equals("0")) {
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), MapActivity.class);
                            startActivity(intent);
                        } else if (state.equals("1")) {
                            Intent intent = new Intent(context, SignOnRecordsDetailsActivity.class);
                            intent.putExtra("jiesu", jiesu);
                            intent.putExtra("date", LocalTimeUtil.getLocalTDate());
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    }

    public static String timeToJiesu(String time) {
        String jiesu = "";
        switch (time) {
            case "8:20":
                jiesu = "1";
                break;
            case "10:20":
                jiesu = "2";
                break;
            case "14:20":
                jiesu = "3";
                break;
            case "16:20":
                jiesu = "4";
                break;
            case "19:00":
                jiesu = "5";
                break;
        }
        return jiesu;
    }

}