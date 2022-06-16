package com.example.customview.bean;

public class DeviceBean {
    String name;
    String address;

    public DeviceBean(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
