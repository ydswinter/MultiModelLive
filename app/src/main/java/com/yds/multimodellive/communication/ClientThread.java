package com.yds.multimodellive.communication;

import android.util.Log;

import com.yds.multimodellive.utils.IPUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class ClientThread implements Runnable {
    private String TAG = "ClientThread";

    @Override
    public void run() {
        NetworkManager networkManager = new NetworkManager();
        List<InetAddress> allLocalAddress = networkManager.getAllLocalAddress();
        for (InetAddress localAddress:allLocalAddress){
            List<InetAddress> others = IPUtil.executeLocalAddress(localAddress);
            for (InetAddress addr:others){
                try {
                    Socket socket = new Socket(addr,7777);
                    InputStream in = socket.getInputStream();
                    int length = 0;
                    byte[] buff = new byte[1024];
                    while((length=in.read(buff))!=-1){
                        String info = new String(buff,0,length);
                        Log.i(TAG, "run: "+info);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
