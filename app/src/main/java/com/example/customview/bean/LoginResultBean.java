package com.example.customview.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class LoginResultBean implements Serializable {
    JSONObject loginResult;

    public LoginResultBean(JSONObject loginResult) {
        this.loginResult = loginResult;
    }
    public String getId(){
        return loginResult.getJSONObject("department").getString("id");
    }
    public String getName(){
        return loginResult.getJSONObject("department").getString("name");
    }
    public String getFullName(){
        return loginResult.getJSONObject("department").getString("fullName");
    }
}
