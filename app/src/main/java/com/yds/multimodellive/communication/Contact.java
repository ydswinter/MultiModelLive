package com.yds.multimodellive.communication;

import java.net.Socket;

public class Contact {
    private String ip;
    private String host;
    private String name;
    private String remark;//备注
    private Socket socket;

    public Contact(String ip) {
        this.ip = ip;
    }


    public Contact() {

    }

    public Contact(String ip, String host,String name, String remark, Socket socket) {
        this.ip = ip;
        this.host = host;
        this.name = name;
        this.remark = remark;
        this.socket = socket;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;

    }


}
