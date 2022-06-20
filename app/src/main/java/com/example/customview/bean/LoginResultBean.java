package com.example.customview.bean;

import java.io.Serializable;

public class LoginResultBean implements Serializable {
    String message;
    String SESSIONID;
    department department;
    account account;
    protected class department implements Serializable{
        String id;
        String code;
        String name;
        int type;
        String fullName;
        float lon;
        float lat;
        String address;
        String nationalNo;
    }
    protected class account implements Serializable{
        String id;
        String name;
        String code;
        String departmentId;
        String mobile;
        String phone;
        String pwd;
        boolean app;
        int job;
    }
    public String getDeptId(){
        return department.id;
    }
    public String getDeptName(){
        return department.name;
    }
    public String getDeptFullName(){
        return department.fullName;
    }
}
