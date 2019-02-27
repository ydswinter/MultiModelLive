package com.yds.multimodellive;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.yds.multimodellive.fragment.SignInFragment;
import com.yds.multimodellive.fragment.SignUpFragment;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private int index;//页面索引
    private  FragmentManager fm;
    private  FragmentTransaction ft;
    private SignInFragment signInFragment;//登录界面
    private SignUpFragment signUpFragment;//注册界面

    private Button btnBack;//返回按钮
    private Button btnSwitchPage;//界面切换按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Intent data = getIntent();
        index = data.getIntExtra("page_index",0);
        initView();
//        FrameLayout container = findViewById(R.id.page_container);
        fm = getSupportFragmentManager();
        switchPage(index);
    }

    public void initView(){
        btnSwitchPage = findViewById(R.id.btn_switch_page);
        btnBack = findViewById(R.id.btn_back);

        btnSwitchPage.setOnClickListener(this);
        btnBack.setOnClickListener(this);

    }

    /**
     * 切换登录界面和注册界面
     * @param index 界面索引
     */
    public void switchPage(int index){
        switch (index){
            case 0:
                //开启事务
                ft = fm.beginTransaction();
                hideAll();
                //设置页面切换按钮内容
                btnSwitchPage.setText(R.string.sign_in);
                //创建页面对象
                if(signInFragment==null){
                    signInFragment = SignInFragment.newInstance();
                    ft.add(R.id.page_container,signInFragment,"sign_in");
                }
                //显示页面
                ft.show(signInFragment);
                break;
            case 1:
                //同上
                ft = fm.beginTransaction();
                hideAll();
                btnSwitchPage.setText(R.string.sign_up);
                if(signUpFragment==null){
                    signUpFragment = SignUpFragment.newInstance();
                    ft.add(R.id.page_container,signUpFragment,"sign_up");
                }
                ft.show(signUpFragment);
                break;
        }
        ft.commit();
    }

    /**
     *
     * 隐藏所有页面
     */
    public void hideAll(){
        if (signInFragment!=null){
            ft.hide(signInFragment);
        }
        if(signUpFragment!=null){
            ft.hide(signUpFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_switch_page:
                index = (index+1)%2;
                switchPage(index);
                break;
        }
    }
}
