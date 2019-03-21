package com.yds.multimodellive.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class IPUtil {
    public static List<InetAddress> executeLocalAddress(InetAddress local){
        List<InetAddress> list = new ArrayList<>();
        byte[] localAddress = local.getAddress();
        for (int i = 0;i<256;i++){
            byte[] address = new byte[4];
            address[0] = localAddress[0];
            address[1] = localAddress[1];
            address[2]  = localAddress[2];
            address[3] = (byte)i;
            if (address[3]==localAddress[3]){
                continue;
            }
            try {
                InetAddress inetAddress = InetAddress.getByAddress(address);

                list.add(inetAddress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        }
        return list;
    }
}
