package com.yds.multimodellive;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yds.multimodellive.communication.NetworkManager;
import com.yds.multimodellive.config.ConfigManager;
import com.yds.multimodellive.model.User;

import java.net.InetAddress;
import java.util.List;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UserActivity";
    private ImageView btnBack;//返回按钮
    private ImageView btnSave;//保存按钮
    private EditText etNickname;//昵称编辑
    private TextView tvAccount;//账号
    private TextView tvAddress;//ip地址
    private EditText etDescription;//描述信息

    private TaskHandler taskHandler;
    private ConfigManager configManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        configManager = new ConfigManager();
        taskHandler = new TaskHandler();

    }

    @Override
    protected void onResume() {
        super.onResume();
        appendData();

    }

    /**
     * 初始化控件
     */
    private void initView(){
        btnBack = findViewById(R.id.btn_back);
        btnSave = findViewById(R.id.btn_save);

        etNickname = findViewById(R.id.et_nickname);
        tvAccount = findViewById(R.id.tv_account);
        tvAddress = findViewById(R.id.tv_address);
        etDescription = findViewById(R.id.et_description);

        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }
    /**
     * 填充数据
     */
    private void appendData(){

        User user = configManager.getUser();
        etNickname.setText(user.getName());
        tvAccount.setText(user.getAccount());
        //获取本机ip地址并显示
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkManager networkManager = new NetworkManager();
                List<InetAddress> addresses = networkManager.getAllLocalAddress();

                if (addresses.size()>0){
                    for (InetAddress addr:addresses){
                        if (!addr.getHostAddress().equals("127.0.0.1")){
                            Message message = Message.obtain();
                            message.what = 0x2001;
                            message.obj = addr.getHostAddress();
                            taskHandler.sendMessage(message);
                        }
                    }
                }else{
                    Message message = Message.obtain();
                    message.what = 0x2001;
                    message.obj = "127.0.0.1";
                    taskHandler.sendMessage(message);
                }
            }
        }).start();
        etDescription.setText(user.getDescription());
    }

    @Override
    public void onClick(View v) {
        if (v.getId()!=R.id.tv_address) {
            tvAddress.clearFocus();
        }
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_save:
                String name = etNickname.getText().toString().trim();
                String account = tvAccount.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                User user = new User(account,name,description);
                configManager.updateUser(user);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    private class TaskHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x2001:
                    tvAddress.setText((String)msg.obj);
                    break;
            }
        }
    }
}
