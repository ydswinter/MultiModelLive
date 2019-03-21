package com.yds.multimodellive.app;

import android.app.Application;
import android.util.Log;

import com.yds.multimodellive.communication.SocketPool;

import java.io.IOException;
import java.net.ServerSocket;

public class App extends Application {
    private static final String TAG = "Application";
    private static App instance;

    /**
     * 服务端单例对象
     */
    private volatile ServerSocket serverSocket;

    /**
     * 服务端Socket管理池
     */
    private volatile SocketPool serverSocketPool;;

    /**
     * 客户端Socket管理池
     */
    private volatile SocketPool clientSocketPool;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }

    /**
     * 获取app实例对象
     * @return
     */
    public static App getInstance(){
        return instance;
    }

    public ServerSocket getServerSocket(){
        synchronized (this){
            try {
                if (serverSocket==null){
                    serverSocket = new ServerSocket(7777);
                    Log.i(TAG, "getServerSocket: 服务器通信接口已创建...");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return serverSocket;
    }

    public SocketPool getServerSocketPool(){
        if (serverSocketPool==null){
            synchronized (this){
                if (serverSocketPool==null){
                    serverSocketPool = new SocketPool();
                }
            }
        }
        return serverSocketPool;
    }

    public SocketPool getClientSocketPool(){
        if (clientSocketPool==null){
            synchronized (this){
                if (clientSocketPool==null){
                    clientSocketPool = new SocketPool();
                }

            }
        }

        return clientSocketPool;
    }
}
