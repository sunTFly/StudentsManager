package com.example.management.menu.signon_records.details;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.WelcomeActivity;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;
import com.example.management.util.LocalTimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class SignOnRecordsDetailsActivity extends Activity{

	private Context context;
	private int sNum ;
	private ListView lv_signonRecordsDetails;
	private ImageView iv_return;
	private TextView tv_bjName,tv_num;
	private String bjName = "";
	private String jiesu = "";
	private int isPeople = 0;
	private String date;
	private List<StudentSignOnInfo> list_all = new ArrayList<>();

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
		setContentView(R.layout.activity_signon_records_details);

		context = SignOnRecordsDetailsActivity.this;

		getData();

		initView();

        new SignOnDetails().execute("teachersign",WelcomeActivity.adminName,jiesu,date);

		initData();

		initListener();

	}

	private void getData(){
		jiesu = getIntent().getStringExtra("jiesu");
		date = getIntent().getStringExtra("date");
	}

	private void initView() {

		tv_num = (TextView) findViewById(R.id.peopleNum);
		lv_signonRecordsDetails = (ListView) findViewById(R.id.signon_records_details_lv);
		iv_return = (ImageView) findViewById(R.id.signon_details_return);
		tv_bjName = (TextView) findViewById(R.id.signon_records_details_bjname);

	}


	private void initData() {
		tv_bjName.setText(bjName);
	}

	private void initListener() {
		iv_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SignOnRecordsDetailsActivity.this.finish();
				return;
			}
		});

	}

	private void initRecordsList() {
		tv_bjName.setText(list_all.get(0).getClassName());
		tv_num.setText("签到人数统计:" + isPeople + "/" + list_all.size());
		SignOnRecordsDetailsAdapter ada = new SignOnRecordsDetailsAdapter(context,list_all);
		lv_signonRecordsDetails.setAdapter(ada);
		lv_signonRecordsDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private class SignOnDetails extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String doInBackground(String... arg) {
			// TODO Auto-generated method stub
			if (arg[0].equals("teachersign")) {
				String signIn = HttpUtil. post_teachersign(arg[1], arg[2], arg[3]);
				return signIn;
			}
			return "";
		}

		@Override
		protected void onPostExecute(String success) {
			list_all = JsonUtils.SignOnDetails(success, "signlist");
			for (int i=0;i < list_all.size();i++){
				if (list_all.get(i).getState().equals("1")){
					isPeople++;
				}
			}
			initRecordsList();
		}
	}


}