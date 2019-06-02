package com.example.management.menu.goHome;

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
import com.example.management.menu.holidays_records.HolidaysRecordsAdapter;
import com.example.management.menu.holidays_records.HolidaysRecordsInfo;
import com.example.management.menu.signOn.SignonClassAdapter;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class GoHomeRecordsActivity extends Activity{

	private Context context;
	private Spinner sp_yuanxi;
	private ImageView iv_return;
	private ListView lv_holidaysRecords;
	private ArrayList<String> arrayList_yuanxi = new ArrayList<String>();
	private List<GoHomeInfo> menuList = new ArrayList<>();

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
		setContentView(R.layout.activity_gohome_records);

		context = GoHomeRecordsActivity.this;


		initView();

		initData();

		initSpinner();

		initListener();

	}

	private void initView() {
		sp_yuanxi = (Spinner) findViewById(R.id.gohome_sp_yuanxi);
		lv_holidaysRecords = (ListView) findViewById(R.id.gohome_records_lv);
		iv_return = (ImageView) findViewById(R.id.gohome_return);
	}


	private void initData() {

		String[] s1 = {"计算机工程学院","土木工程学院","工商管理学院","美院","体院"};
		for (int i=0;i < s1.length;i++){
			arrayList_yuanxi.add(s1[i]);
		}

	}

	private void initListener() {

		iv_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GoHomeRecordsActivity.this.finish();
				return;
			}
		});

	}

	private void initSpinner(){

		ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList_yuanxi);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
		sp_yuanxi.setAdapter(ada);//将适配器给下拉框
		sp_yuanxi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//当改变下拉框的时候会触发
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
				new GoHomeRecordsState().execute("homerecord", sp_yuanxi.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {//没有改变的时候

			}
		});

	}

	private void initRecordsList() {
		GoHomeRecordsAdapter ada = new GoHomeRecordsAdapter(context, menuList);
		lv_holidaysRecords.setAdapter(ada);
		lv_holidaysRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private class GoHomeRecordsState extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String doInBackground(String... arg) {
			// TODO Auto-generated method stub
            if (arg[0].equals("homerecord")) {
                String homerecord = HttpUtil.post_homerecord(arg[1]);
                return homerecord;
            }
			return "";
		}

		@Override
		protected void onPostExecute(String success) {
			//Toast.makeText(context,success,Toast.LENGTH_SHORT).show();
			menuList = JsonUtils.GoHomeRecords(success,"homelist");
			initRecordsList();
		}
	}



}