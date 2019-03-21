package com.yds.multimodellive.communication;

import android.util.Log;

import com.yds.multimodellive.utils.IPUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class UserFinder {
    private static final String TAG = "TAG";

    public List<Contact> findLocalUser(){
        List<Contact> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                    while (interfaces.hasMoreElements()){
                        NetworkInterface networkInterface = interfaces.nextElement();
//                        String hardAddress = new String(networkInterface.getHardwareAddress());
//                        Log.i(TAG, "run: hardAddress;"+hardAddress);
                        Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                        while (inetAddresses.hasMoreElements()){
                            InetAddress inetAddress = inetAddresses.nextElement();
                            if (inetAddress instanceof Inet4Address){
                                if (inetAddress.getHostAddress().equals("127.0.0.1")){
                                    continue;
                                }
                                Log.i(TAG, "本机地址："+inetAddress.getHostAddress());

                                //根据本机地址生成所有可能的局域网主机地址
                                List<InetAddress> allLocalAddress = IPUtil.executeLocalAddress(inetAddress);
                                //连接探测
                                for (InetAddress addr:allLocalAddress){
                                    Log.i(TAG, "探测地址："+addr.getHostAddress());
                                    try {
                                        Socket socket = new Socket(addr,7777);
                                        if(socket.isConnected()){
                                            Log.e(TAG, "连接到主机: "+addr.getHostAddress());
                                            InputStream input = socket.getInputStream();
                                            byte[] info = new byte[1024];
                                            int length=0;
                                            OutputStream out = new ByteArrayOutputStream();
                                            while ((length=input.read(info))!=-1) {
                                                out.write(info,0,length);
                                            }
                                            Log.e(TAG, "run: 主机信息1" );
                                            Log.e(TAG, "run: "+new String(((ByteArrayOutputStream) out).toByteArray()) );
                                            Log.e(TAG, "run: 主机信息2" );
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                    }
                }catch (SocketException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return list;
    }

    public List<Contact> findRemoteFriends(){
        List<Contact> list = new ArrayList<>();
        return list;
    }


}
