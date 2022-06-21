package com.example.customview.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkhttpUtil {
    static OkHttpClient okHttpClient;
    public static OkHttpClient getInstance() {
        if(okHttpClient==null){
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(6000, TimeUnit.MILLISECONDS)
                    .readTimeout(6000, TimeUnit.MILLISECONDS)
                    .writeTimeout(6000, TimeUnit.MILLISECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
