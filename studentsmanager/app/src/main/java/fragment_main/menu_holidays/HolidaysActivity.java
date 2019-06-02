package fragment_main.menu_holidays;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.ModelLoader;
import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.RegisterAndRecognizeActivity;
import com.example.studentsmanager.activity.WelcomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fragment_main.menu_night.NightActivity;
import util.HttpUtil;
import util.ImageOperate;
import util.LocalTime;

public class HolidaysActivity extends AppCompatActivity {

    private Context context;
    private EditText et_reason;
    private GridView gv_img;
    private ImageView iv_return;
    private Button btn_add, btn_submit;
    private TextView tv_startDate, tv_endDate;
    public static List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    private final  int RecognizeFace = 3;
    public static List<Bitmap> imgList = new ArrayList<Bitmap>();
    private static final int IMAGE = 1, TAKE_PHOTO = 2;
    private Bitmap bitmap;
    private  String str_start,str_end,reason,num;


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
        setContentView(R.layout.activity_menu_holidays);

        context = HolidaysActivity.this;

        initView();

        initDate();

        initListener();


    }

    private void initView() {
        et_reason = (EditText) findViewById(R.id.holidays_et_reason);
        gv_img = (GridView) findViewById(R.id.holidays_img_reason);
        btn_add = (Button) findViewById(R.id.holidays_btn_add);
        btn_submit = (Button) findViewById(R.id.holidays_btn_submit);
        iv_return = (ImageView) findViewById(R.id.holidays_return);
        tv_startDate = (TextView) findViewById(R.id.holidays_tv_startdate);
        tv_endDate = (TextView) findViewById(R.id.holidays_tv_enddate);
    }

    private void initDate() {
        String date = LocalTime.getLocalTDate();
        tv_startDate.setText(date);
        tv_endDate.setText(date);
    }

    private void initListener() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgList.size() <= 1) {
                    showBottomDialog();
                } else {
                    Toast.makeText(context, "最多选择两张图片", Toast.LENGTH_SHORT).show();
                }

            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HolidaysActivity.this.finish();
                return;
            }
        });

        tv_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String desc = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                        tv_startDate.setText(desc);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        tv_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String desc = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                        tv_endDate.setText(desc);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交数据库....
                str_start = tv_startDate.getText().toString();
                str_end = tv_endDate.getText().toString();
                reason = et_reason.getText().toString();
                num = WelcomeActivity.studentsNum;
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    if (imgList.size() <= 0) {
                        Toast.makeText(context, "至少选择一张图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (reason.equals("")) {
                        Toast.makeText(context, "请输入请假事由", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (compareDate(sf.parse(str_start), sf.parse(str_end))) {

                        //人脸识别模块
                        String name = WelcomeActivity.studentsName;
                        Intent intent=new Intent();
                        intent.setClass(context,RegisterAndRecognizeActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt("registerStatus",2);
                        bundle.putString("u_name",name);
                        bundle.putString("u_num",num);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,RecognizeFace);
                    } else {
                        Toast.makeText(context, "日期选择错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void initMenuViews() {

        dataList.clear();
        for (int i = 0; i < imgList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", imgList.get(i));
            dataList.add(map);
        }

        HolidaysAdapter ada = new HolidaysAdapter(context, dataList);
        gv_img.setAdapter(ada);
        gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            String imagePath = c.getString(c.getColumnIndex(filePathColumns[0]));
            bitmap = BitmapFactory.decodeFile(imagePath);
            bitmap = ImageOperate.getDiskBitmap(bitmap, 300, 300);
            c.close();
            imgList.add(bitmap);
            initMenuViews();
        } else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            bitmap = ImageOperate.getDiskBitmap(bitmap, 300, 300);
            imgList.add(bitmap);
            initMenuViews();
        } else if (requestCode == RecognizeFace && resultCode == Activity.RESULT_OK) {
            String startDateTime = str_start + " 00:00:00";
            String endDateTime = str_end + " 23:00:00";
            String imgStr = "";
            for (int i = 0; i < imgList.size(); i++) {
                imgStr = imgStr + ImageOperate.bitmapToString(imgList.get(i)) + "####";
            }
            new StuLeave().execute("0",num, startDateTime, endDateTime, reason, imgStr);

            Toast.makeText(context, "提交成功", Toast.LENGTH_SHORT).show();
            HolidaysActivity.this.finish();
        }
    }

    private void showBottomDialog() {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(this, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(this, R.layout.dialog_holidays_layout, null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
                    startActivityForResult(takePhotoIntent, TAKE_PHOTO);//启动相机
                }
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用相册
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE);
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private boolean compareDate(Date startTime, Date endTime) {

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (begin.before(end) || begin.equals(end)) {
            return true;
        }
        {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataList.clear();
        imgList.clear();
    }

    private class StuLeave extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            return HttpUtil.post_leave(arg[0], arg[1], arg[2], arg[3], arg[4],arg[5]);
        }

        @Override
        protected void onPostExecute(String success) {

        }
    }
}
