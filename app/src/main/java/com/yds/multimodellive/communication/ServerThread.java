package com.yds.multimodellive.communication;

import android.util.Log;

import com.yds.multimodellive.app.App;
import com.yds.multimodellive.config.ConfigManager;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread implements Runnable {
    private static final String TAG = "ServerThread";
    private ServerSocket serverSocket = App.getInstance().getServerSocket();
    @Override
    public void run() {
        Log.i(TAG, "服务器运行中");
        while(true){

            try {
                Socket socket = serverSocket.accept();
                App.getInstance().getServerSocketPool().add(socket);
                OutputStream out = socket.getOutputStream();
                ConfigManager config = new ConfigManager();
                out.write(("用户"+((InetSocketAddress)socket.getLocalSocketAddress()).getHostName()).getBytes());
                out.flush();
                out.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
