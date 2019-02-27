package com.yds.multimodellive.communication;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
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
                                Log.i(TAG, "run: "+inetAddress.getHostAddress());
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
