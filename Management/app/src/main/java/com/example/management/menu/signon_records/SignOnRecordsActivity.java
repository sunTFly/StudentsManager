package com.example.management.menu.signon_records;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.management.WelcomeActivity;
import com.example.management.menu.signOn.ClassInfo;
import com.example.management.menu.signOn.SignOnActivity;
import com.example.management.menu.signOn.SignonClassAdapter;
import com.example.management.menu.signon_records.details.SignOnRecordsDetailsActivity;
import com.example.management.menu.signon_records.details.SignOnRecordsDetailsAdapter;
import com.example.management.menu.signon_records.details.StudentSignOnInfo;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;
import com.example.management.util.LocalTimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SignOnRecordsActivity extends Activity{

	private Context context;
	private Spinner sp_time;
	private ListView lv_signonRecords;
	private TextView tv_yes,tv_date;
	private ImageView iv_return;
	private List<StudentSignOnInfo> menuList = new ArrayList<>();
	private ArrayList<String> arrayList_time = new ArrayList<String>();

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
		setContentView(R.layout.activity_signon_records);

		context = SignOnRecordsActivity.this;


		initView();

		initData();
		initSpinner();

		initListener();

	}

	private void initView() {
		sp_time = (Spinner) findViewById(R.id.signon_records_sp_time);
		tv_date =  (TextView) findViewById(R.id.signon_records_date);
		lv_signonRecords = (ListView) findViewById(R.id.signon_records_lv);
		tv_yes = (TextView) findViewById(R.id.signon_records_tv_yes);
		iv_return = (ImageView) findViewById(R.id.signon_records_return);
	}


	private void initData() {

		String[] s1 = {"选择时间","8:20","10:20","14:20","16:20","19:00"};
		for (int i=0;i < s1.length;i++){
			arrayList_time.add(s1	[i]);
		}

	}

	private void initListener() {

		tv_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar calendar = Calendar.getInstance();
				DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
						String desc = String.format("%d-%d-%d",year,month+1,dayOfMonth);
						tv_date.setText(desc);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}
		});

		tv_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tv_date.getText().toString().equals("选择日期")){
					Toast.makeText(context,"请选择日期",Toast.LENGTH_SHORT).show();
					return ;
				}else if (sp_time.getSelectedItemPosition() == 0){
					Toast.makeText(context,"请选择时间",Toast.LENGTH_SHORT).show();
					return ;
				}

				new SignOnRecords().execute("teachersign",WelcomeActivity.adminName,SignOnActivity.timeToJiesu(sp_time.getSelectedItem().toString()),tv_date.getText().toString());
			}
		});

		iv_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SignOnRecordsActivity.this.finish();
				return;
			}
		});

	}

	private void initSpinner(){

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList_time);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
		sp_time.setAdapter(arrayAdapter);//将适配器给下拉框

	}

	private void initRecordsList() {

		SignOnRecordsDetailsAdapter ada = new SignOnRecordsDetailsAdapter(context,menuList);
		lv_signonRecords.setAdapter(ada);
		lv_signonRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});

	}

	private class SignOnRecords extends AsyncTask<String, Void, String> {
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
			menuList = JsonUtils.SignOnDetails(success, "signlist");
			initRecordsList();
		}
	}



}