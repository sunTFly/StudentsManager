package fragment_me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import util.BackgroundDrawable;
import util.HttpUtil;
import util.ImageOperate;


public class LoginActivity extends Activity implements OnClickListener {

	private TextView mBtnLogin;
	private RelativeLayout rl_login;
	private Context context;
	private EditText userNum, password;
	private ImageView iv_return;
	private CheckBox cb_remberiNum,cb_remberPass;
	private String num,stuName,feature,headImg,stuState,tel,address;
	private String pass;
	public static boolean isHavingFace = false;
	public static String faceFacture = "";

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
		setContentView(R.layout.activity_login);

		context = LoginActivity.this;

		initView();

		initData();

		initListener();

	}

	private void initView() {
		mBtnLogin = (TextView) findViewById(R.id.btn_login);
		userNum = (EditText) findViewById(R.id.login_name);
		password = (EditText) findViewById(R.id.login_pass);
		rl_login = (RelativeLayout) findViewById(R.id.login_rl);
		iv_return = (ImageView) findViewById(R.id.login_return);
		cb_remberiNum = (CheckBox) findViewById(R.id.login_cb_remembername);
		cb_remberPass = (CheckBox) findViewById(R.id.login_cb_rememberpass);

	}


	private void initData() {

		String numAndPass[] = WelcomeActivity.getNumAndPass(context).split(",");

		if (WelcomeActivity.getIsRemberNum(context)){
			cb_remberiNum.setChecked(true);
			userNum.setText(numAndPass[0]);
		}

		if (WelcomeActivity.getIsRemberPass(context)){
			cb_remberPass.setChecked(true);
			password.setText(numAndPass[1]);
		}

		BackgroundDrawable drawable = BackgroundDrawable.builder()
				.left(28)//设置左侧斜切点的高度（取值范围是大于0，小于100）
				.right(38)//设置右侧侧斜切点的高度（取值范围是大于0，小于100）
				.topColor(Color.parseColor("#3598db"))//设置上半部分的颜色（默认是白色）
				//.bottomColor();//设置下半部分的颜色（默认是白色）
				.build();//调用build进行创建。

		rl_login.setBackground(drawable);
	}

	private void initListener() {
		mBtnLogin.setOnClickListener(this);

		iv_return.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});

		cb_remberiNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					cb_remberPass.setEnabled(true);
				}else{
					cb_remberPass.setEnabled(false);
					cb_remberPass.setChecked(false);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {

		num = userNum.getText().toString();
		pass = password.getText().toString();

		if (num.isEmpty() || num == null) {
			Toast.makeText(this, "学号输入为空", Toast.LENGTH_SHORT).show();
			return;
		}
		if (pass.isEmpty() || pass == null) {
			Toast.makeText(this, "密码输入为空", Toast.LENGTH_SHORT).show();
			return;
		}

		new LoginAction().execute(num,pass);

	}

	//登录线程
	private class LoginAction extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected String doInBackground(String... arg) {
			// TODO Auto-generated method stub
			return HttpUtil.post_login(arg[0], arg[1]);
		}

		@Override
		protected void onPostExecute(String success) {
			// TODO Auto-generated method stub
			JSONObject jsonObject = null;
			boolean loginResult = false;
			if (success.equals("连接失败") || success.equals("提交失败")) {
				Toast.makeText(getBaseContext(), "连接超时，请重新登录 ", Toast.LENGTH_LONG)
						.show();
			} else {
				try {
					jsonObject = new JSONObject(success);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					loginResult = (Boolean) jsonObject.get("success");
					tel = (String) jsonObject.get("StuTel");
					address = (String) jsonObject.get("StuAdr");
					stuName = (String) jsonObject.get("stuName");
					feature = (String) jsonObject.get("StuFeature");
					headImg = (String) jsonObject.get("head");
					stuState = (String) jsonObject.get("StuState");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



				if (loginResult) {
					if (cb_remberiNum.isChecked()){
						WelcomeActivity.setNumAndPass(context,num,pass);
						WelcomeActivity.setIsRemberNum(context,true);
					}else{
						WelcomeActivity.setIsRemberNum(context,false);
					}

					if (cb_remberPass.isChecked()){
						WelcomeActivity.setIsRemberPass(context,true);
					}else{
						WelcomeActivity.setIsRemberPass(context,false);
					}

					new Thread(new Runnable() {
						@Override
						public void run() {
							WelcomeActivity.setIsLogin(context,true);
							WelcomeActivity.setStudentsName(context,stuName);
							WelcomeActivity.setStudentsNum(context,num);
							WelcomeActivity.studentState = stuState;
							WelcomeActivity.tel = tel;
							WelcomeActivity.address = address;
							faceFacture = feature;
							if (headImg == null){
								WelcomeActivity.headImg = BitmapFactory.decodeResource(getResources(),R.mipmap.head_sculpture_1);
							}else{
								WelcomeActivity.headImg = ImageOperate.stringToBitmap(headImg);
							}
						}
					}).start();

					if (feature == null){
						Intent intent = new Intent(context, PerfectInfoActivity.class);
						startActivity(intent);
					}else{
						new Thread(new Runnable() {
							@Override
							public void run() {
								isHavingFace = true;
								String name = stuName + num;
								File featureDir = ImageOperate.base64ToFile(feature,name);
								if (!featureDir.exists()){
									try {
										featureDir.getParentFile().mkdirs();
										featureDir.createNewFile();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}).start();

						Toast.makeText(getBaseContext(), "登录成功", Toast.LENGTH_SHORT)
								.show();
						LoginActivity.this.finish();
						return;
					}

				} else {
					Toast.makeText(getBaseContext(), "账号密码错误", Toast.LENGTH_LONG)
							.show();
				}
			}
			// b1 = ImageOperate.stringToBitmap(sss);
			//img.setImageBitmap(b1);

			//Toast.makeText(MainActivity.this,b1+"",Toast.LENGTH_SHORT).show();

		}
	}

}