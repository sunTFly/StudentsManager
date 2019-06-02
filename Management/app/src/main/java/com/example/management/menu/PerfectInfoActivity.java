package com.example.management.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.WelcomeActivity;

import com.example.management.util.BackgroundDrawable;
import com.example.management.util.HttpUtil;
import com.example.management.util.ImageOperate;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.management.util.ImageOperate.bitmapToBytes;
import static com.example.management.util.ImageOperate.bytesToBitmap;
import static com.example.management.util.ImageOperate.getBitmapSize;

public class PerfectInfoActivity extends AppCompatActivity {

    private RelativeLayout rl_perfect;
    private Context context;
    private Bitmap bitmap;
    private EditText et_name,et_num,et_pass,et_tel;
    private ImageView iv_pic,iv_return;
    private Button btn_yes,btn_cancel;
    private String num;
    private String imgStr;
    private static final int IMAGE = 1;

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
        setContentView(R.layout.activity_perfect_information);

        context = PerfectInfoActivity.this;

        initView();

        initData();

        initListener();

    }

    private void initView() {
        rl_perfect = (RelativeLayout) findViewById(R.id.perfect_rl);
        et_name = (EditText) findViewById(R.id.et_perfect_name);
        et_num = (EditText) findViewById(R.id.et_perfect_num);
        et_pass = (EditText) findViewById(R.id.et_perfect_pass);
        et_tel = (EditText) findViewById(R.id.perfect_tv_tel);
        iv_pic = (ImageView) findViewById(R.id.perfect_pic);
        btn_yes = (Button) findViewById(R.id.perfect_btn_yes);
        btn_cancel = (Button) findViewById(R.id.perfect_btn_cancel);
        iv_return = (ImageView) findViewById(R.id.perfect_return);

    }

    private void initData() {
        num = WelcomeActivity.adminNum;

        BackgroundDrawable drawable = BackgroundDrawable.builder()
                .left(38)//设置左侧斜切点的高度（取值范围是大于0，小于100）
                .right(28)//设置右侧侧斜切点的高度（取值范围是大于0，小于100）
                .topColor(Color.parseColor("#3598db"))//设置上半部分的颜色（默认是白色）
                //.bottomColor();//设置下半部分的颜色（默认是白色）
                .build();//调用build进行创建。

        rl_perfect.setBackground(drawable);

        et_name.setText(WelcomeActivity.adminName);
        et_num.setText(num);

        iv_pic.setImageBitmap(WelcomeActivity.bitmap);
        imgStr = ImageOperate.bitmapToString(WelcomeActivity.bitmap);


    }

    private void initListener() {

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = et_pass.getText().toString();
                String tel = et_tel.getText().toString();
                if (pass.isEmpty() || pass == null){
                    Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tel.isEmpty() || tel == null){
                    Toast.makeText(context, "请输入联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }

                new updateTeacher().execute("teaupdate",WelcomeActivity.adminNum,pass,tel,imgStr);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfectInfoActivity.this.finish();
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfectInfoActivity.this.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA,MediaStore.Images.Media.DISPLAY_NAME};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            String imagePath = c.getString(c.getColumnIndex(filePathColumns[0]));
            bitmap = BitmapFactory.decodeFile(imagePath);
            c.close();
            iv_pic.setImageBitmap(bitmap);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (getBitmapSize(bitmap) <= 0.3 * 1000 * 1000 * 10){
                        imgStr = ImageOperate.bitmapToString(bitmap);
                        WelcomeActivity.bitmap = bitmap;
                    }else{
                        Bitmap bm = ImageOperate.ZipImg(bitmap,0.2f);
                        imgStr = ImageOperate.bitmapToString(bm);
                        WelcomeActivity.bitmap = bm;
                    }
                }
            }).start();
        }
    }

    private class updateTeacher extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub

            return HttpUtil.post_teaupdate(arg[1], arg[2],arg[3],arg[4]);

        }

        @Override
        protected void onPostExecute(String success) {
            JSONObject jsonObject = null;
            boolean loginResult = false;
            if (success.equals("连接失败") || success.equals("提交失败")) {
                Toast.makeText(getBaseContext(), "连接失败", Toast.LENGTH_LONG)
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
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (loginResult) {
                    Toast.makeText(context, "完善资料成功", Toast.LENGTH_SHORT).show();
                    PerfectInfoActivity.this.finish();
                } else {
                    Toast.makeText(getBaseContext(), "失败", Toast.LENGTH_LONG)
                            .show();
                }
            }
        }
    }


}
