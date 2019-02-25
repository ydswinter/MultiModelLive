package com.yds.multimodellive;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.yds.multimodellive.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawer;//抽屉菜单
    private ImageButton open;//菜单按钮
    private RelativeLayout feedback;//反馈选项
    private ImageView picture;//头像
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.RECORD_AUDIO);
        PermissionUtil.checkPermission(this,permissions);
    }

    private void initView(){
        drawer = findViewById(R.id.drawer);
        open = findViewById(R.id.open);
        feedback = findViewById(R.id.item_feedback);
        picture = findViewById(R.id.picture);

        //为控件设置监听器
        feedback.setOnClickListener(this);
        picture.setOnClickListener(this);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_feedback:

                break;
            case R.id.picture:
                Intent intent1 = new Intent(MainActivity.this,LiveActivity.class);
                intent1.putExtra("page_index",0);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
