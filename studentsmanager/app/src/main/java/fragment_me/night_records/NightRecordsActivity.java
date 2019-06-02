package fragment_me.night_records;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import fragment_me.holidays_records.HolidaysRecordsAdapter;
import fragment_message.MessageViewPageAdapter;
import util.JsonUtils;

public class NightRecordsActivity extends AppCompatActivity {

    private Context context;
    private ViewPager viewPager;
    private List<View> list;
    private List<TextView> list_menu;
    private List<TextView> list_line;
    private List<NightRecordsInfo> list_lastlast_info = new ArrayList<NightRecordsInfo>(),list_last_info = new ArrayList<NightRecordsInfo>(),list_info = new ArrayList<NightRecordsInfo>();
    private ImageView iv_return;
    private LinearLayout ll_lastlastmonth, ll_lastmonth, ll_month;
    private ListView lv_night;
    private Random random = new Random();
    private NightRecordsAdapter adapter;

    int views[] = {R.layout.night_recoreds_viewpager_month, R.layout.night_recoreds_viewpager_month, R.layout.night_recoreds_viewpager_month};
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_records);

        context = NightRecordsActivity.this;

        getIntentData();

        initView();

        initData();

        initMonthList();
        initLastLastMonthList();
        initLastMonthList();

        initListener();

    }

    private void getIntentData() {
        String success = getIntent().getStringExtra("success");
        List<NightRecordsInfo> list_all = JsonUtils.ListBack(success, "stuBack");
        for (int i = 0; i < list_all.size(); i++){
            int month = list_all.get(i).getMonth();
            NightRecordsInfo n = list_all.get(i);
            if (month == getMonthDays(0)){
                list_lastlast_info.add(n);
            }else if (month == getMonthDays(1)){
                list_last_info.add(n);
            }else if (month == getMonthDays(2)){
                list_info.add(n);
            }
      }

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.night_records_viewPager);
        ll_lastlastmonth = (LinearLayout) findViewById(R.id.sigonon_yesterday_ll);
        ll_lastmonth = (LinearLayout) findViewById(R.id.sigonon_today_ll);
        ll_month = (LinearLayout) findViewById(R.id.sigonon_tomorrow_ll);
        iv_return = (ImageView) findViewById(R.id.night_records_return);
    }

    private void initData() {
        list_menu = new ArrayList<TextView>();
        list_line = new ArrayList<TextView>();
        list = new ArrayList<View>();

        for (int i = 0; i < menus.length; i++) {
            TextView tv = (TextView) findViewById(menus[i]);
            tv.setText(getMonthDays(i) + "月");
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
                NightRecordsActivity.this.finish();
                return;
            }
        });

        MessageViewPageAdapter adapter = new MessageViewPageAdapter(list, context);
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

        ll_lastlastmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(0);
            }
        });
        ll_lastmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(1);
            }
        });
        ll_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeColor(2);
            }
        });
    }

    public int getMonthDays(int i) {
        //0上上个月日期 1上个月日期 2这个月日期
        Calendar now = Calendar.getInstance();
        int month = now.get(Calendar.MONTH) + 1;
        int reMonth = 0;
        if (month == 1){
            reMonth = month;
            if (i == 0){
                reMonth = 11;
            }else if(i == 1){
                reMonth = 12;
            }
            return reMonth;
        }else if (month == 2){
            if (i == 0){
                reMonth = 12;
                return reMonth;
            }
        }
        switch (i) {
            case 0:
                reMonth = (month-2);
                break;
            case 1:
                reMonth = (month-1);
                break;
            case 2:
                reMonth =  month;
                break;
        }
        return reMonth;
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

    private void initLastLastMonthList() {
        lv_night = (ListView) list.get(0).findViewById(R.id.night_records_lv);
        NightRecordsAdapter ada = new NightRecordsAdapter(context,list_lastlast_info);
        lv_night.setAdapter(ada);

    }

    private void initLastMonthList() {
        lv_night = (ListView) list.get(1).findViewById(R.id.night_records_lv);
        NightRecordsAdapter ada = new NightRecordsAdapter(context, list_last_info);
        lv_night.setAdapter(ada);
    }

    private void initMonthList() {
        lv_night = (ListView) list.get(2).findViewById(R.id.night_records_lv);
        NightRecordsAdapter ada = new NightRecordsAdapter(context,list_info);
        lv_night.setAdapter(ada);

    }

}
