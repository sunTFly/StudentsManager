package fragment_me.class_table;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentsmanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fragment_me.holidays_records.HolidaysRecordsAdapter;
import util.JsonUtils;

public class ClassTbActivity extends AppCompatActivity {

    private Context context;
    private Spinner spinner_week,spinner_term;
    private GridView gv_class;
    private ImageView iv_return;
    public List<ClassTbInfo> dataList = new ArrayList<ClassTbInfo>();
    private final int totalClassNum = 49;
    private Random random = new Random();
    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<String> arrayList_term = new ArrayList<String>();

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
        setContentView(R.layout.activity_classtb);

        context = ClassTbActivity.this;

        getIntentData();

        initView();

        initData();

        initTbList();
        initSpinner();

        initListener();

    }

    private void getIntentData() {
        String success = getIntent().getStringExtra("success");
        dataList = JsonUtils.Listclass(success, "weekclass");
    }

    private void initView() {
        spinner_week = (Spinner) findViewById(R.id.classtb_spinner_week);
        spinner_term = (Spinner) findViewById(R.id.classtb_spinner_term);
        gv_class = (GridView) findViewById(R.id.classtb_gridview);
        iv_return = (ImageView) findViewById(R.id.classtb_return);
    }


    private void initData() {
        for (int i=0;i < 18;i++){
            arrayList.add((i+1) + "周");
        }

        for (int i=1;i < 3;i++){
            for (int j=2017;j < 2022;j++){
                arrayList_term.add((j) + "年第" + i + "学期");
            }
        }

    }

    private void initSpinner(){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
        spinner_week.setAdapter(arrayAdapter);//将适配器给下拉框
        spinner_week.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//当改变下拉框的时候会触发
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
                initTbList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {//没有改变的时候

            }

            });

        ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList_term);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
        spinner_term.setAdapter(ada);//将适配器给下拉框
        spinner_term.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//当改变下拉框的时候会触发
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
                spinner_week.setSelection(0);
                initTbList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {//没有改变的时候

            }
        });

    }

    private void initTbList() {

        List<ClassTbInfo> list = new ArrayList<ClassTbInfo>();
        boolean isHavingClass = false;
        for (int i = 0;i < 7;i++){
            for (int j = 0;j < 7;j++){
                for (int n=0;n < dataList.size();n++){
                    ClassTbInfo c = dataList.get(n);
                    if (c.getJiesu() == i && c.getDay() == j){
                        list.add(c);
                        isHavingClass = true;
                        break;
                    }else{
                        isHavingClass = false;
                    }
                }
                if (!isHavingClass){
                    ClassTbInfo c1 = new ClassTbInfo();
                    c1.setClassName("");
                    c1.setClassName("");
                    c1.setJiesu(i);
                    c1.setDay(j);
                    c1.setColor(Color.WHITE);
                    list.add(c1);
                }
            }
        }

        ClasTbAdapter adapter = new ClasTbAdapter(context,list);
        gv_class.setAdapter(adapter);

        gv_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initListener() {
        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassTbActivity.this.finish();
                return;
            }
        });
    }

}
