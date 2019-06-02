package com.example.management.menu.holidays;

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
import com.example.management.menu.holidays_records.HolidaysRecordsActivity;
import com.example.management.menu.holidays_records.HolidaysRecordsAdapter;
import com.example.management.menu.holidays_records.HolidaysRecordsInfo;
import com.example.management.util.HttpUtil;
import com.example.management.util.JsonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class HolidaysActivity extends Activity{

	private Context context;
	private Spinner sp_yuanxi,sp_state;
	private TextView tv_yes,tv_date;
	private ImageView iv_return;
	private ListView lv_holidays;
	private ArrayList<String> arrayList_state = new ArrayList<String>();
	private ArrayList<String> arrayList_yuanxi = new ArrayList<String>();
	private List<HolidaysRecordsInfo> menuList = new ArrayList<>();
	private String searchType = "3";
	private String desc;

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
		setContentView(R.layout.activity_holidays);

		context = HolidaysActivity.this;

		initView();

		initData();

		initSpinner();

		initListener();

	}

	private void initView() {

		sp_yuanxi = (Spinner) findViewById(R.id.holidays_sp_yuanxi);
		sp_state = (Spinner) findViewById(R.id.holidays_sp_state);
		lv_holidays = (ListView) findViewById(R.id.holidays_lv);
		iv_return = (ImageView) findViewById(R.id.holidays_return);
		tv_yes = (TextView) findViewById(R.id.holidays_tv_yes);
		tv_date = (TextView) findViewById(R.id.holidays_date);
	}


	private void initData() {
		String[] s = {"全部","未处理","已处理"};
		for (int i=0;i < s.length;i++){
			arrayList_state.add(s[i]);
		}

		String[] s1 = {"选择院系","计算机工程学院","土木工程学院","工商管理学院","美院","体院"};
		for (int i=0;i < s1.length;i++){
			arrayList_yuanxi.add(s1[i]);
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
						desc = String.format("%d-%d-%d",year,month+1,dayOfMonth);
						tv_date.setText(desc);
					}
				}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
				dialog.show();
			}
		});

		tv_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (sp_yuanxi.getSelectedItemPosition() == 0){
					Toast.makeText(context,"请选择院系",Toast.LENGTH_SHORT).show();
					return ;
				}else if (tv_date.getText().toString().equals("选择日期")){
					Toast.makeText(context,"请选择日期",Toast.LENGTH_SHORT).show();
					return ;
				}

				new HolidaysState().execute("deplea",sp_yuanxi.getSelectedItem().toString(),"3",desc + " 00:00:00");

			}
		});


		iv_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HolidaysActivity.this.finish();
				return;
			}
		});

	}

	private void initSpinner(){

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList_state);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
		sp_state.setAdapter(arrayAdapter);//将适配器给下拉框
		sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//当改变下拉框的时候会触发
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候
				switch (position){
					case 0:
						searchType = "3";
						break;
					case 1:
						searchType = "0";
						break;
					case 2:
						searchType = "1";
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {//没有改变的时候

			}

		});

		ArrayAdapter<String> ada = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrayList_yuanxi);//创建适配器  this--上下文  android.R.layout.simple_spinner_item--显示的模板   arrayList--显示的内容
		ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//设置下拉之后的布局的样式 这里采用的是系统的一个布局
		sp_yuanxi.setAdapter(ada);//将适配器给下拉框
		sp_yuanxi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {//当改变下拉框的时候会触发
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {//改变内容的时候

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {//没有改变的时候

			}
		});

	}

	private void initRecordsList() {
		HolidaysRecordsAdapter ada = new HolidaysRecordsAdapter(context, menuList);
		lv_holidays.setAdapter(ada);
		lv_holidays.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(context,HolidaysDetailsActivity.class);
				intent.putExtra("holidays",menuList.get(position));
				startActivity(intent);
			}
		});

	}

	private class HolidaysState extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String doInBackground(String... arg) {
			// TODO Auto-generated method stub
			if (arg[0].equals("deplea")) {
				String signIn = HttpUtil.post_depleas(arg[1], arg[2], arg[3]);
				return signIn;
			}
			return "";
		}

		@Override
		protected void onPostExecute(String success) {
			//Toast.makeText(context,success,Toast.LENGTH_LONG).show();
			menuList = JsonUtils.HolidayRecords(success,"stuLea",sp_yuanxi.getSelectedItem().toString());
			initRecordsList();
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		new HolidaysState().execute("deplea",sp_yuanxi.getSelectedItem().toString(),"3",desc + " 00:00:00");
	}
}