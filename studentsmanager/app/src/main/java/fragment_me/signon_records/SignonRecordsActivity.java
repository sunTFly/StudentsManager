package fragment_me.signon_records;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.WelcomeActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fragment_main.menu_sign_on.ClassInfo;
import fragment_main.menu_sign_on.SignOnActivity;
import util.HttpUtil;
import util.JsonUtils;
import util.LocalTime;


public class SignonRecordsActivity extends AppCompatActivity {

    private ListView lv_records;
    private Context context;
    private TextView tv_date;
    private Button btn_selectDate;
    private ImageView iv_return;
    private List<ClassInfo> menuList = new ArrayList<>();

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
        setContentView(R.layout.activity_signon_records);

        context = SignonRecordsActivity.this;
        new SignOnRecordsState().execute("signIn", WelcomeActivity.studentsNum,LocalTime.getLocalTDate(),"2");

        initView();

        initData();

        //initRecordsList();

        initListener();

    }

    private void initView() {
        lv_records = (ListView) findViewById(R.id.signon_records_lv);
        tv_date = (TextView) findViewById(R.id.signon_records_date);
        btn_selectDate = (Button) findViewById(R.id.sugnon_records_btn_selectdate);
        iv_return = (ImageView) findViewById(R.id.signon_records_return);
        tv_date.setText(LocalTime.getLocalTDate());
    }

    private void initData() {

    }


    private void initListener() {
        btn_selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String desc = String.format("%d-%d-%d",year,month+1,dayOfMonth);
                        tv_date.setText(desc);
                        new SignOnRecordsState().execute("signIn", WelcomeActivity.studentsNum,desc,"2");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignonRecordsActivity.this.finish();
                return;
            }
        });
    }

    private void initRecordsList() {
        SignonRecordsAdapter ada = new SignonRecordsAdapter(context, menuList);
        lv_records.setAdapter(ada);
        lv_records.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class SignOnRecordsState extends AsyncTask<String, Void, String> {
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
            menuList = JsonUtils.ListSignIn(success,"stuSignIn");
            initRecordsList();
        }
    }


}
