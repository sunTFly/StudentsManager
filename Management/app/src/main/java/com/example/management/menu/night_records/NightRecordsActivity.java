package com.example.management.menu.night_records;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NightRecordsActivity extends Activity {

    private Context context;
    private Spinner sp_dorm, sp_room;
    private ListView lv_nightRecords;
    private TextView tv_yes, tv_time;
    private ImageView iv_return;
    private ArrayList<String> arrayList_dorm = new ArrayList<String>();
    private ArrayList<String> arrayList_room = new ArrayList<String>();
    private List<NightRecordsInfo> menuList = new ArrayList<>();

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
        setContentView(R.layout.activity_night_records);

        context = NightRecordsActivity.this;


        initView();

        initData();
        initSpinner();

        initListener();

    }

    private void initView() {
        tv_time = (TextView) findViewById(R.id.night_records_time);
        sp_dorm = (Spinner) findViewById(R.id.night_records_sp_dorm);
        sp_room = (Spinner) findViewById(R.id.night_records_sp_room);
        lv_nightRecords = (ListView) findViewById(R.id.night_records_lv);
        tv_yes = (TextView) findViewById(R.id.night_records_tv_yes);
        iv_return = (ImageView) findViewById(R.id.night_records_return);
    }


    private void initData() {

        String[] s1 = {"计算机工程学院","土木工程学院","工商管理学院","美院","体院"};
        for (int i = 0; i < s1.length; i++) {
            arrayList_dorm.add(s1[i]);
        }

        String[] s2 = {"全部","海棠苑3舍433", "海棠苑3舍434", "海棠苑3舍435"};
        for (int i = 0; i < s2.length; i++) {
            arrayList_room.add(s2[i]);
        }

    }

    private void initListener() {

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String desc = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                        tv_time.setText(desc);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv_time.getText().toString().equals("选择日期")) {
                    Toast.makeText(context, "请选择日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                new NightRecordsState().execute("teabackrec",sp_dorm.getSelectedItem().toString(),sp_room.getSelectedItem().toString(),tv_time.getText().toString());
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NightRecordsActivity.this.finish();
                return;
            }
        });

    }

    private void initSpinner() {

        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_dorm);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
        sp_dorm.setAdapter(ada);//将适配器给下拉框

        ArrayAdapter<String> ada1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList_room);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
        sp_room.setAdapter(ada1);//将适配器给下拉框

    }

    private void initRecordsList() {

        NightRecordsAdapter ada = new NightRecordsAdapter(context, menuList);
        lv_nightRecords.setAdapter(ada);
        lv_nightRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class NightRecordsState extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            if (arg[0].equals("teabackrec")) {
                String signIn = HttpUtil.post_teabackrec(arg[1], arg[2], arg[3]);
                return signIn;
            }
            return "";
        }

        @Override
        protected void onPostExecute(String success) {
            menuList = JsonUtils.NightRecords(success,"stuBack");
            initRecordsList();
        }
    }

}