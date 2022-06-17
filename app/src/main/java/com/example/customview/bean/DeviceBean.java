package com.example.customview.bean;

public class DeviceBean {
    String id;
    String name;
    int status;
    public DeviceBean(String id, String name, int status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }
}
