package fragment_me;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentsmanager.R;
import com.example.studentsmanager.activity.WelcomeActivity;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import fragment_me.class_table.ClassTbActivity;
import fragment_me.holidays_records.HolidaysRecodsActivity;
import fragment_me.holidays_records.HolidaysRecordsAdapter;
import fragment_me.night_records.NightRecordsActivity;
import fragment_me.signon_records.SignonRecordsActivity;
import util.HttpUtil;
import util.ImageOperate;
import util.JsonUtils;

/**
 * Created by Avecle on 2019/3/6.
 */

public class fragment_me extends Fragment {

    View view; // 分类碎片视图
    private Context context;
    private ListView lv_menu, lv_menu2, lv_menu3;
    private int[] picId_lv1 = {R.mipmap.icon_me_menu_qiandao, R.mipmap.icon_me_menu_holiday, R.mipmap.icon_me_menu_wangui},
            picId_lv2 = {R.mipmap.icon_me_menu_changeinfo, R.mipmap.icon_me_menu_classtb},
            picId_lv3 = {R.mipmap.icon_me_menu_feedback, R.mipmap.icon_me_menu_setting};
    private String[] menuStr_lv1 = {"签到记录", "请假记录", "晚归记录"}, menuStr_lv2 = {"资料修改", "我的课表"}, menuStr_lv3 = {"反馈", "设置"};
    private List<MenuInfo> lv_menu1_ls, lv_menu2_ls, lv_menu3_ls;
    private Button btn_login;
    private ImageView iv_pic;
    private TextView tv_name;
    private int flag = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);

        context = getActivity();

        initView();

        initData();

        initListener();


        return view;
    }

    private void initView() {
        lv_menu = (ListView) view.findViewById(R.id.me_menu_lv);
        lv_menu2 = (ListView) view.findViewById(R.id.me_menu_lv2);
        lv_menu3 = (ListView) view.findViewById(R.id.me_menu_lv3);
        btn_login = (Button) view.findViewById(R.id.me_btn_login);
        iv_pic = (ImageView) view.findViewById(R.id.me_pic);
        tv_name = (TextView) view.findViewById(R.id.me_name);
    }

    private void initData() {
        lv_menu1_ls = new ArrayList<MenuInfo>();
        lv_menu2_ls = new ArrayList<MenuInfo>();
        lv_menu3_ls = new ArrayList<MenuInfo>();
        InitMenuList(lv_menu, lv_menu1_ls, picId_lv1, menuStr_lv1);
        InitMenuList(lv_menu2, lv_menu2_ls, picId_lv2, menuStr_lv2);
        InitMenuList(lv_menu3, lv_menu3_ls, picId_lv3, menuStr_lv3);


    }


    private void initListener() {

        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (WelcomeActivity.getIsLogin(context)) {
                    switch (position) {
                        case 0:
                            intent = new Intent(context, SignonRecordsActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            flag = 1;
                            new Records().execute("leaveRecord", WelcomeActivity.studentsNum);
                            break;
                        case 2:
                            flag = 2;
                            new Records().execute("NightRecord", WelcomeActivity.studentsNum);
                            break;
                    }
                }else{
                    Toast.makeText(getActivity(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_menu2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (WelcomeActivity.getIsLogin(context)) {
                    switch (position) {
                        case 0:
                            intent = new Intent(context, PerfectInfoActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            flag=4;
                            new Records().execute("weekclass");
                            break;
                    }
                }else {
                    Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lv_menu3.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_login.getText().toString().equals("登录")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    setImageAName(false);
                }
            }
        });

    }

    private void InitMenuList(ListView lv, List<MenuInfo> ls, int picIDs[], String menus[]) {
        int jiantou = R.mipmap.icon_me_menu_jiantou;
        int rowCount = picIDs.length;
        for (int i = 0; i < rowCount; i++) {
            MenuInfo info = new MenuInfo();
            Bitmap bi = BitmapFactory.decodeStream(getResources().openRawResource(picIDs[i]));
            bi = ImageOperate.getRoundRectBitmap(bi, 20);
            info.setMenuName(menus[i]);
            info.setPic(bi);
            info.setJiantouId(jiantou);
            ls.add(info);
        }
        MenuAdapter ada = new MenuAdapter(getActivity(), ls);
        lv.setAdapter(ada);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (WelcomeActivity.getIsLogin(context)) {
            setImageAName(true);
        } else {
            setImageAName(false);
        }
    }

    public void setImageAName(boolean isLogin) {
        String name = "未登录";
        Bitmap bit = BitmapFactory.decodeResource(context.getResources(), R.mipmap.head_sculpture_1);
        if (isLogin) {
            name = WelcomeActivity.studentsName;
            bit = WelcomeActivity.headImg;
            tv_name.setText(name);
            iv_pic.setImageBitmap(bit);
            btn_login.setText("退出登录");
            GradientDrawable gd = (GradientDrawable) btn_login.getBackground();
            gd.setColor(ContextCompat.getColor(context, R.color.weiGui));
        } else {
            tv_name.setText(name);
            iv_pic.setImageBitmap(bit);
            btn_login.setText("登录");
            GradientDrawable gd = (GradientDrawable) btn_login.getBackground();
            gd.setColor(ContextCompat.getColor(context, R.color.login_btn));
            WelcomeActivity.setStudentsNum(context, "");
            WelcomeActivity.setIsLogin(context, false);
            PerfectInfoActivity.isFinishFace = false;
            LoginActivity.isHavingFace = false;
        }

    }

    private class Records extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected String doInBackground(String... arg) {
            // TODO Auto-generated method stub
            if (arg[0].equals("leaveRecord")) {
                String leaveRecord = HttpUtil.post_leaveRecord(arg[1]);
                return leaveRecord;
            } else if (arg[0].equals("NightRecord")) {
                String backRecord = HttpUtil.post_backRecord(arg[1]);
                return backRecord;
            } else if (arg[0].equals("weekclass")) {
                String weekclass = HttpUtil.post_weekclass(arg[0]);
                return weekclass;
            }
            return "返回dobanck";
        }

        @Override
        protected void onPostExecute(String success) {
            Intent intent;
            JSONException jsonException = new JSONException((success));
            if (flag == 1) {
                intent = new Intent(context, HolidaysRecodsActivity.class);
                intent.putExtra("success", success);
                startActivity(intent);
            } else if (flag == 2) {
                intent = new Intent(context, NightRecordsActivity.class);
                intent.putExtra("success", success);
                startActivity(intent);
            } else if (flag == 4) {
                intent = new Intent(context, ClassTbActivity.class);
                intent.putExtra("success", success);
                startActivity(intent);
            }

        }
    }
}

