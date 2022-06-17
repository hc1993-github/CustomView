package com.example.customview.bean;

public class BlueteethDeviceBean extends DeviceBean {
    String devicename;
    String address;

    public BlueteethDeviceBean(String name, String address) {
        super(name,address,1);
        this.devicename = name;
        this.address = address;
    }

    public String getName() {
        return devicename;
    }

    public String getAddress() {
        return address;
    }
}
