package com.yds.multimodellive.communication;


import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SocketPool {

    /**
     * socket 列表
     */
    private List<Socket> pool;


    public SocketPool(){
        pool = new ArrayList<>();
    }


    public void add(Socket socket) {
        pool.add(socket);
    }


    public Socket get(InetAddress inetAddress) {
        for (Socket socket:pool) {
            if (inetAddress.getHostAddress().equals(socket.getInetAddress().getHostAddress())) {
                return socket;
            }
        }
        return null;
    }


    public void remove(InetAddress inetAddress) {
        Iterator<Socket> iterator = pool.iterator();
        while(iterator.hasNext()){
            Socket socket = iterator.next();
            InetAddress inetAddress1 = socket.getInetAddress();
            if (inetAddress.getHostAddress().equals(inetAddress1.getHostAddress())){
                if (socket!=null&&!socket.isClosed()){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                iterator.remove();
            }

        }
    }


    public void clear() {
        Iterator<Socket> iterator = pool.iterator();
        while(iterator.hasNext()){
            Socket socket = iterator.next();
            if (socket!=null&&!socket.isClosed()){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            iterator.remove();
        }
    }



}
