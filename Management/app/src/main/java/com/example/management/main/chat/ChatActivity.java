package com.example.management.main.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.management.R;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity{

    private Context context;
    private List<Msg> msgList = new ArrayList<Msg>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private int friendPicId;
    private ImageView iv_return;
    private Bitmap me_pic;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details);

        context = ChatActivity.this;

        getData();

        initMsgs();

        initView();

        initData();

        initListener();

    }

    private void getData() {
        friendPicId = getIntent().getIntExtra("friendPic",0);
        me_pic = BitmapFactory.decodeResource(getResources(),R.mipmap.head_sculpture_1);
    }

    private void initView() {
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycle_view);
        iv_return = (ImageView) findViewById(R.id.chat_return);
    }

    private void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(context,msgList);
        msgRecyclerView.setAdapter(adapter);

    }

    private void initListener() {

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT,me_pic,friendPicId);
                    msgList.add(msg);
                    // 当有新消息时，刷新ListView中的显示
                    adapter.notifyItemInserted(msgList.size() - 1);
                    // 将ListView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    // 清空输入框中的内容
                    inputText.setText("");
                }
            }
        });

        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatActivity.this.finish();
                return;
            }
        });

    }

    /**
     * 初始化聊天消息
     */
    private void initMsgs() {
        Msg msg1 = new Msg("刘老师你好，今天我有事可以请个假吗", Msg.TYPE_RECEIVED,me_pic,friendPicId);
        msgList.add(msg1);
        Msg msg2 = new Msg("好的", Msg.TYPE_SENT,me_pic,friendPicId);
        msgList.add(msg2);
        Msg msg3 = new Msg("刘老师在吗", Msg.TYPE_RECEIVED,me_pic,friendPicId);
        msgList.add(msg3);
    }


}
