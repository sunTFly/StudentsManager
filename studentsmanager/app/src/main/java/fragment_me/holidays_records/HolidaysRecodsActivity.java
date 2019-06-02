package fragment_me.holidays_records;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.studentsmanager.R;

import java.util.ArrayList;
import java.util.List;

import util.ImageOperate;
import util.JsonUtils;

public class HolidaysRecodsActivity extends AppCompatActivity {

    private ListView lv_records;
    private Context context;
    private ImageView iv_return;
    private HolidaysRecordsAdapter adapter;

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
        setContentView(R.layout.activity_holidays_records);

        context = HolidaysRecodsActivity.this;

        getIntentData();

        initView();

        initData();

        initRecordsList();

        initListener();

    }

    private void getIntentData() {
        String success = getIntent().getStringExtra("success");
        adapter = new HolidaysRecordsAdapter(context, JsonUtils.ListLea(success, "stuLea"));
    }

    private void initView() {
        lv_records = (ListView) findViewById(R.id.holidays_records_lv);
        iv_return = (ImageView) findViewById(R.id.holidays_records_return);
    }

    private void initData() {

    }


    private void initListener() {

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolidaysRecodsActivity.this.finish();
                return;
            }
        });
    }

    private void initRecordsList() {
        lv_records.setAdapter(adapter);
        lv_records.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }



}
