package com.yds.multimodellive.app;

import android.Manifest;
import android.app.Application;

import com.yds.multimodellive.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    /**
     * 获取app实例对象
     * @return
     */
    public Application getInstance(){
        return instance;
    }
}
