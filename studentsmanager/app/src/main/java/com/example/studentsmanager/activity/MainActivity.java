package com.example.studentsmanager.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.example.studentsmanager.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import util.PermisionUtils;


public class MainActivity extends FragmentActivity implements View.OnClickListener {

    Context context;
    private LinearLayout kindsLayout; // xx界面
    private LinearLayout mainLayout; // 主页界面
    private LinearLayout meLayout; // 我的界面
    private long exitTime = 0; // 用来计算返回键的点击间隔时间

    public Fragment[] mFragments;
    public FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    public boolean isGetPermision = false;
    private Toast toast;

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

        //是否大于6.0
        if (Build.VERSION.SDK_INT >= 23){
            PermisionUtils.verifyStoragePermissions(this);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isGetPermision){
                    try {
                        activeEngine();
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化底部菜单
        context = MainActivity.this;
        this.kindsLayout = (LinearLayout) findViewById(R.id.bottom_kinds_layout);
        this.mainLayout = (LinearLayout) findViewById(R.id.bottom_main_layout);
        this.meLayout = (LinearLayout) findViewById(R.id.bottom_me_layout);

        // 设置菜单监听事件
        this.kindsLayout.setOnClickListener(this);
        this.mainLayout.setOnClickListener(this);
        this.meLayout.setOnClickListener(this);
        this.mainLayout.setSelected(true);

        mFragments = new Fragment[3];
        this.fragmentManager = getSupportFragmentManager();// 获取fragmentManager实例，通过fragmentManager管理fragment
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragment_kinds);// 获取fragment实例
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragment_main);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragment_me);

        fragmentTransaction = fragmentManager.beginTransaction()// 获取fragmentTransaction实例
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);// 通过hide显示隐藏当前fragment
        fragmentTransaction.show(mFragments[1]).commit();

    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1])
                .hide(mFragments[2]);

        switch (v.getId()) {
            case R.id.bottom_kinds_layout:
                this.kindsLayout.setSelected(true);// 设置选择器
                this.mainLayout.setSelected(false);
                this.meLayout.setSelected(false);
                fragmentTransaction.show(mFragments[0]).commit();// 显示第一fragment
                break;
            case R.id.bottom_main_layout:
                this.kindsLayout.setSelected(false);
                this.mainLayout.setSelected(true);
                this.meLayout.setSelected(false);
                fragmentTransaction.show(mFragments[1]).commit();
                break;
            case R.id.bottom_me_layout:
                this.kindsLayout.setSelected(false);
                this.mainLayout.setSelected(false);
                this.meLayout.setSelected(true);
                fragmentTransaction.show(mFragments[2]).commit();
                break;
            default:
                break;
        }
    }

    /**
     *
     * 双击退出程序
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                // 弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // 激活引擎

    public void activeEngine() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(MainActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            showToast(getString(R.string.active_success));
                            isGetPermision = true;
                        }else if(activeCode == ErrorInfo.MERR_ASF_NETWORK_COULDNT_RESOLVE_HOST){
                            Toast.makeText(context,"网络连接失败，引擎激活失败",Toast.LENGTH_SHORT).show();
                        }else if (activeCode == ErrorInfo.MERR_ASF_READ_PHONE_STATE_DENIED){
                            Toast.makeText(context,"未获取通讯录权限，引擎激活失败，请开启权限",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void showToast(String s) {
        if (toast == null) {
            toast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast.setText(s);
            toast.show();
        }
    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this, neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }


}
