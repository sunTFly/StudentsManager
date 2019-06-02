package fragment_me;

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
import android.os.Environment;
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


import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.MainActivity;
import com.example.studentsmanager.activity.RegisterAndRecognizeActivity;
import com.example.studentsmanager.activity.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import util.BackgroundDrawable;
import util.HttpUtil;
import util.ImageOperate;


public class PerfectInfoActivity extends AppCompatActivity {

    private RelativeLayout rl_perfect;
    private Context context;
    private Bitmap bitmap;
    private EditText et_name,et_num,et_pass,et_tel,et_address;
    private ImageView iv_pic,iv_return;
    private TextView tv_faceReg;
    private Button btn_yes,btn_cancel;
    private String num,featureStr,imgStr,tel_str,address_str;
    private final int RegisterFace = 1;
    public static boolean isFinishFace = false;
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
        et_tel = (EditText) findViewById(R.id.perfect_et_tel);
        et_address = (EditText) findViewById(R.id.perfect_et_address);
        iv_pic = (ImageView) findViewById(R.id.perfect_pic);
        btn_yes = (Button) findViewById(R.id.perfect_btn_yes);
        btn_cancel = (Button) findViewById(R.id.perfect_btn_cancel);
        tv_faceReg = (TextView) findViewById(R.id.perfect_tv_faceregister);
        iv_return = (ImageView) findViewById(R.id.perfect_return);
        if (LoginActivity.isHavingFace){
            tv_faceReg.setEnabled(false);
            isFinishFace = true;
            tv_faceReg.setText("人脸注册(已完成)");
            featureStr = LoginActivity.faceFacture;
        }

    }

    private void initData() {
        num = WelcomeActivity.studentsNum;
        if (!WelcomeActivity.tel.equals("")){
            et_tel.setText(WelcomeActivity.tel);
        }
        if (!WelcomeActivity.address.equals("")){
            et_address.setText(WelcomeActivity.address);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Matrix matrix = new Matrix();
                matrix.setScale(0.5f, 0.5f);
                Bitmap bit = WelcomeActivity.headImg;
                Bitmap bm = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                        bit.getHeight(), matrix, true);
                imgStr = ImageOperate.bitmapToString(bm);
            }
        }).start();


        BackgroundDrawable drawable = BackgroundDrawable.builder()
                .left(38)//设置左侧斜切点的高度（取值范围是大于0，小于100）
                .right(28)//设置右侧侧斜切点的高度（取值范围是大于0，小于100）
                .topColor(Color.parseColor("#3598db"))//设置上半部分的颜色（默认是白色）
                //.bottomColor();//设置下半部分的颜色（默认是白色）
                .build();//调用build进行创建。

        rl_perfect.setBackground(drawable);

        et_name.setText(WelcomeActivity.studentsName);
        et_num.setText(num);
        iv_pic.setImageBitmap(WelcomeActivity.headImg);

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

        tv_faceReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //人脸注册模块
                String name=et_name.getText().toString();
                String num=et_num.getText().toString();
                Intent intent=new Intent();
                intent.setClass(context,RegisterAndRecognizeActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("registerStatus",0);
                bundle.putString("u_name",name);
                bundle.putString("u_num",num);
                intent.putExtras(bundle);
                startActivityForResult(intent,RegisterFace);

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = et_pass.getText().toString();
                tel_str = et_tel.getText().toString();
                address_str = et_address.getText().toString();
                if (pass.isEmpty() || pass == null){
                    Toast.makeText(context, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tel_str.isEmpty() || tel_str == null){
                    Toast.makeText(context, "请输入联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (address_str.isEmpty() || address_str == null){
                    Toast.makeText(context, "请输入家庭地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isFinishFace){
                    Toast.makeText(context, "请完成人脸注册", Toast.LENGTH_SHORT).show();
                    return;
                }

                new updateStudents().execute(WelcomeActivity.studentsNum,pass,tel_str,address_str,featureStr,imgStr);

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
                    if (ImageOperate.getBitmapSize(bitmap) <= 0.3 * 1000 * 1000 * 10){
                        imgStr = ImageOperate.bitmapToString(bitmap);
                        WelcomeActivity.headImg = bitmap;
                    }else{
                        Bitmap bm = ImageOperate.ZipImg(bitmap,0.2f);
                        imgStr = ImageOperate.bitmapToString(bm);
                        WelcomeActivity.headImg = bm;
                    }

                }
            }).start();
        }else if (requestCode == RegisterFace && resultCode == Activity.RESULT_OK){
            String path = Environment.getExternalStorageDirectory().toString() + File.separator + "register" +
                    File.separator + "featu0res" + File.separator + WelcomeActivity.studentsName + WelcomeActivity.studentsNum;
            File file = new File(path);
            featureStr = ImageOperate.fileToBase64(file);
            tv_faceReg.setText("人脸注册(已完成)");
            tv_faceReg.setEnabled(false);
        }
    }
    //更新学生信息线程
    private class updateStudents extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub

          return HttpUtil.post_stuUpdate(arg[0],arg[1],arg[2],arg[3],arg[4],arg[5]);

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
