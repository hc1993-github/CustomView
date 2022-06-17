package com.example.customview.bean;

public class DeviceBean extends Bean{
    String devicename;
    String address;

    public DeviceBean(String name, String address) {
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
