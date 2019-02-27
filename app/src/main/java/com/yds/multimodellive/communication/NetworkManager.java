package com.yds.multimodellive.communication;

import android.util.Log;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    /**
     * 获取本机所有的网卡局域网地址
     * @return
     */
    public List<InetAddress> getAllLocalAddress(){
        List<InetAddress> list = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements()){
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()){
                    InetAddress address = addresses.nextElement();
                    if(address instanceof Inet4Address && !address.getHostAddress().equals("127.0.0.1")){
                        list.add(address);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "getAllLocalAddress: "+list);
        return list;
    }
}
