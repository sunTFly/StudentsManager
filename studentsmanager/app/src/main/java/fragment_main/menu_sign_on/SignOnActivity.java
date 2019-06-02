package fragment_main.menu_sign_on;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.WelcomeActivity;


import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fragment_main.menu_night.NightActivity;
import fragment_me.night_records.NightRecordsInfo;
import fragment_message.MessageViewPageAdapter;
import util.HttpUtil;
import util.JsonUtils;

public class SignOnActivity extends AppCompatActivity {

    private Context context;
    private ViewPager viewPager;
    private List<View> list;
    private List<TextView> list_menu;
    private List<TextView> list_line;
    private MessageViewPageAdapter adapter;
    private ImageView iv_return;
    private LinearLayout ll_yesterday, ll_today, ll_tomorrow;
    private ListView lv_class;
    private List<ClassInfo> ls_yesterday = new ArrayList<>(),ls_today = new ArrayList<>(),ls_lastlast = new ArrayList<>();
    private List<ClassInfo> list_all = new ArrayList<>();
    private SignonClassAdapter ada_today;

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
        setContentView(R.layout.activity_menu_signon);

        context = SignOnActivity.this;

        getIntentData();

        initView();

        initDate();

        initListener();

        InitLastLastClassList();
        InitYesterdayClassList();
        InitTodayClassList();

    }

    private void getIntentData() {
        String success = getIntent().getStringExtra("success");
        changeList(success);
    }

    private void changeList(String success){
        list_all = new ArrayList<>();
        ls_today = new ArrayList<>();
        ls_lastlast = new ArrayList<>();
        ls_yesterday = new ArrayList<>();
        list_all = JsonUtils.ListSignIn(success,"stuSignIn");
        for (int i = 0; i < list_all.size(); i++){
            int day = list_all.get(i).getDay();
            ClassInfo c = list_all.get(i);
            if (day == getDays(0)){
                ls_lastlast.add(c);
            }else if (day == getDays(1)) {
                ls_yesterday.add(c);
            }else if (day == getDays(2)){
                ls_today.add(c);
            }
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.signon_viewPager);
        ll_yesterday = (LinearLayout) findViewById(R.id.sigonon_yesterday_ll);
        ll_today = (LinearLayout) findViewById(R.id.sigonon_today_ll);
        ll_tomorrow = (LinearLayout) findViewById(R.id.sigonon_tomorrow_ll);
        iv_return = (ImageView) findViewById(R.id.signon_return);
    }

    private void initDate() {
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

        adapter = new MessageViewPageAdapter(list, context);
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

    public static String getDate(int i) {
        Date date = null;
        //0前天日期 1昨天日期 2今天日期 3明天日期
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
            case 4:
                date = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
                break;
        }
        SimpleDateFormat df = new SimpleDateFormat("MM-dd");//设置日期格式
        return df.format(date);
    }

    public static int getDays(int i){
        Date date = null;
        //0前天日期 1昨天日期 2今天日期
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
        SimpleDateFormat df = new SimpleDateFormat("dd");//设置日期格式
        return Integer.valueOf(df.format(date));
    }


    private void InitLastLastClassList() {
        lv_class = (ListView) list.get(0).findViewById(R.id.siginon_class_lv);
        SignonClassAdapter ada = new SignonClassAdapter(context,ls_lastlast);
        lv_class.setAdapter(ada);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SignOnDetailsActivity.class);
                intent.putExtra("className", ls_lastlast.get(position).getClassName());
                intent.putExtra("teacher", ls_lastlast.get(position).getTeacher());
                intent.putExtra("classRoom", ls_lastlast.get(position).getClassRoom());
                intent.putExtra("classTime", ls_lastlast.get(position).getClassTime());
                intent.putExtra("teaTel",ls_yesterday.get(position).getTeaTel());
                intent.putExtra("stateType", ls_lastlast.get(position).getStateType());
                intent.putExtra("teaNo",ls_lastlast.get(position).getTeaNo());
                intent.putExtra("days", list_menu.get(0).getText().toString());
                startActivity(intent);
            }
        });

    }

    private void InitYesterdayClassList() {
        lv_class = (ListView) list.get(1).findViewById(R.id.siginon_class_lv);
        SignonClassAdapter ada = new SignonClassAdapter(context, ls_yesterday);
        lv_class.setAdapter(ada);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SignOnDetailsActivity.class);
                intent.putExtra("className", ls_yesterday.get(position).getClassName());
                intent.putExtra("teacher", ls_yesterday.get(position).getTeacher());
                intent.putExtra("classRoom", ls_yesterday.get(position).getClassRoom());
                intent.putExtra("classTime", ls_yesterday.get(position).getClassTime());
                intent.putExtra("stateType", ls_yesterday.get(position).getStateType());
                intent.putExtra("teaTel",ls_yesterday.get(position).getTeaTel());
                intent.putExtra("teaNo",ls_yesterday.get(position).getTeaNo());
                intent.putExtra("days", list_menu.get(1).getText().toString());
                startActivity(intent);
            }
        });

    }


    private void InitTodayClassList() {
        lv_class = (ListView) list.get(2).findViewById(R.id.siginon_class_lv);
        ada_today = new SignonClassAdapter(context, ls_today);
        lv_class.setAdapter(ada_today);
        lv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SignOnDetailsActivity.class);
                intent.putExtra("className", ls_today.get(position).getClassName());
                intent.putExtra("teacher", ls_today.get(position).getTeacher());
                intent.putExtra("classRoom", ls_today.get(position).getClassRoom());
                intent.putExtra("classTime", ls_today.get(position).getClassTime());
                intent.putExtra("stateType", ls_today.get(position).getStateType());
                intent.putExtra("teaTel",ls_today.get(position).getTeaTel());
                intent.putExtra("teaNo",ls_today.get(position).getTeaNo());
                intent.putExtra("days", list_menu.get(2).getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new NightState().execute("signIn", WelcomeActivity.studentsNum, "2018-08-21 00:00:00", "1");
    }

    private class NightState extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
           if (arg[0].equals("signIn")) {
                String signIn = HttpUtil.post_signInRecord(arg[1], arg[2], arg[3]);
                return signIn;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String success) {
            changeList(success);
            InitTodayClassList();
        }
    }
}
