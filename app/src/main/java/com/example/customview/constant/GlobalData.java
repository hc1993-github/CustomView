package com.example.customview.constant;

public class GlobalData {
    public static final String baseIp = "http://221.224.159.210:";
    public static final String versionUrl = "/datacenter/outward/package/autoUpdateApp?type=ColdchainPrint&vnumCurrent=";
    public static final String versionPort = "38283";
    public static final String loginUrl = "/ssmanage/accountLogin!login4Client.action";
    public static final String loginPort = "38111";
    public static final String updateDeviceStatusUrl = "/coldchain/public/deviceChangeStatus!changeStatus.action?id=";
    public static final String updateDeviceStatusPort = "38111";
    public static final String requestDeviceInfosUrl = "/coldchain/public/coldchainPrint!getAllCars.action?departmentId=";
    public static final String requestDeviceInfosPort = "38111";
    public static final String requestDeviceTasksUrl = "/coldchain/public/coldchainPrint!getTransit.action?startTime=";
    public static final String requestDeviceTasksPort = "38111";
    public static final String createOrUpdateCurrentDayTaskTimeUrl = "/coldchain/public/deviceTransit!transit.action?id=";
    public static final String createOrUpdateCurrentDayTaskTimePort = "38111";
}
