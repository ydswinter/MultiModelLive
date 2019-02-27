package com.yds.multimodellive.app;

import android.Manifest;
import android.app.Application;

import com.yds.multimodellive.util.PermissionUtil;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private Application instance;

    private Socket socket;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        socket = new Socket();

    }

    /**
     * 获取app实例对象
     * @return
     */
    public Application getInstance(){
        return instance;
    }

    /**
     * 获取视频传输socket
     */

    public Socket getSocket(){
        return socket;
    }
}
