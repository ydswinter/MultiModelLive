package com.yds.multimodellive;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yds.multimodellive.communication.ContactsManager;
import com.yds.multimodellive.communication.ServerThread;
import com.yds.multimodellive.config.ConfigManager;
import com.yds.multimodellive.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private int backPressedCount;

    private DrawerLayout drawer;//抽屉菜单
    private ImageButton open;//菜单按钮
    private RelativeLayout feedback;//反馈选项
    private ImageView picture;//头像
    private TextView name;//姓名

    private LinearLayout btnContacts;//联系人选项

    ContactsManager contactsManager;
    ConfigManager configManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configManager = new ConfigManager();
        initView();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.RECORD_AUDIO);
        PermissionUtil.checkPermission(this,permissions);
        contactsManager = new ContactsManager();

        new Thread(new ServerThread()).start();

    }

    private void initView(){
        drawer = findViewById(R.id.drawer);
        open = findViewById(R.id.open);
        feedback = findViewById(R.id.item_feedback);
        picture = findViewById(R.id.picture);
        name = findViewById(R.id.tv_name);
        btnContacts = findViewById(R.id.btn_contacts);
        name.setText(configManager.getUser().getName());

        //为控件设置监听器
        feedback.setOnClickListener(this);
        picture.setOnClickListener(this);
        open.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.open:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.item_feedback:

                break;
            case R.id.picture:
                Intent intent1 = new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_contacts:
                Intent intent2 = new Intent(MainActivity.this,ContactActivity.class);
                startActivity(intent2);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "onBackPressed: 再按一次退出");
        if(backPressedCount%2==0){
            Toast.makeText(MainActivity.this,"再按一次退出",Toast.LENGTH_SHORT).show();
            backPressedCount+=1;
            taskHanlder.sendEmptyMessageDelayed(0x1001,2000);
        }else{
            finish();
        }
    }

    private Handler taskHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x1001:
                    backPressedCount = 0;
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
