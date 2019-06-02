package com.example.management.menu.holidays;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.menu.holidays_records.HolidaysRecordsInfo;
import com.example.management.util.HttpUtil;
import com.example.management.util.ImageOperate;
import com.example.management.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HolidaysDetailsActivity extends Activity{

	private Context context;
	private ImageView iv_return;
	private TextView tv_reason;
	private GridView gv_img;
	private Button btn_yes,btn_no;
	private LinearLayout ll;
	private int stateType;
	private String reason,sNum,date;
	private HolidaysRecordsInfo h;
	public List<Map<String,Object>> dataList = new ArrayList<Map<String, Object>>();
	private List<Bitmap> imgList = new ArrayList<>();

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
		setContentView(R.layout.activity_holidays_details);

		context = HolidaysDetailsActivity.this;

		getIntentData();

		initView();

		initData();

		initListener();
		initMenuViews();

	}

	private void getIntentData(){
		h = (HolidaysRecordsInfo)getIntent().getSerializableExtra("holidays");
		stateType = h.getStateType();
		sNum = h.getsNo();
		date = h.getStartDate() + " 00:00:00";
		reason = h.getReason();
		String s[] = h.getImgStr().split("####");

		for (int i=0;i < s.length;i++){
			imgList.add(ImageOperate.stringToBitmap(s[i]));
		}
	}

	private void initView() {
		ll = (LinearLayout) findViewById(R.id.holidays_details_ll);
		btn_yes = (Button) findViewById(R.id.holidays_details_btn_yes);
		btn_no = (Button) findViewById(R.id.holidays_details_btn_no);
		gv_img = (GridView) findViewById(R.id.holidays_details_gv_reason);
		iv_return = (ImageView) findViewById(R.id.holidays_details_return);
		tv_reason = (TextView) findViewById(R.id.holidays_details_reason);
		if (stateType != 0){
			ll.setVisibility(View.GONE);
		}
	}


	private void initData() {

		tv_reason.setText("请假理由:" + reason);


	}

	private void initListener() {

		iv_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				HolidaysDetailsActivity.this.finish();
				return;
			}
		});

		btn_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new HolidayPassState().execute("leadel",sNum,date,"1");
				Toast.makeText(context,"同意成功",Toast.LENGTH_SHORT).show();
				HolidaysDetailsActivity.this.finish();
				return;
			}
		});

		btn_no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new HolidayPassState().execute("leadel",sNum,date,"2");
				Toast.makeText(context,"不同意成功",Toast.LENGTH_SHORT).show();
				HolidaysDetailsActivity.this.finish();
				return;
			}
		});

	}

	private void initMenuViews() {

		dataList.clear();
		for (int i = 0; i < imgList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("img",imgList.get(i));
			dataList.add(map);
		}

		HolidaysDetailsAdapter ada = new HolidaysDetailsAdapter(context,dataList);
		gv_img.setAdapter(ada);
		gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
			}
		});

	}

	private class HolidayPassState extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String doInBackground(String... arg) {
			// TODO Auto-generated method stub
			if (arg[0].equals("leadel")) {
				String signIn = HttpUtil.post_leadel(arg[1], arg[2], arg[3]);
				return signIn;
			}
			return "";
		}

		@Override
		protected void onPostExecute(String success) {

		}
	}


}