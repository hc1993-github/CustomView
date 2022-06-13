package com.example.customview.util;

import okhttp3.OkHttpClient;

public class OkhttpUtil {
    static OkHttpClient okHttpClient;
    public static OkHttpClient getInstance() {
        if(okHttpClient==null){
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
