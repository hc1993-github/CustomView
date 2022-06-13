package com.example.customview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.customview.util.OkhttpUtil;

import java.io.IOException;
import java.io.PipedReader;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    public static final String secretKey = "8t13YLhm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getVersion();
    }

    private void getVersion() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            int versionCode = pi.versionCode;
            Request request = new Request.Builder().url("http://221.224.159.210:38283/datacenter/outward/package/autoUpdateApp?type=ColdchainPrint&vnumCurrent="+1+"&androidVersion=23").build();
            OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    String string = response.body().string();
                    AnalyseVersion(string);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AnalyseVersion(String message) {
        if(message.contains("无更新包")){

        }else {
            String result = AnalyseDES(message,secretKey);
            JSONObject jsonObject = JSONArray.parseObject(result);
            String downLoadPath = jsonObject.getString("path");
            String md5 = jsonObject.getString("md5");
            String unZipPwd = jsonObject.getString("compressPwd");
            downLoadZipAndInstall(downLoadPath,md5,unZipPwd);
        }
    }

    private void downLoadZipAndInstall(String downLoadPath,String md5,String unZipPwd) {
        Request request = new Request.Builder().url(downLoadPath).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                response.body().byteStream();
            }
        });
    }

    private String AnalyseDES(String message,String secretKey) {
        try {
            byte[] bytes = secretKey.getBytes();
            byte[] decode = Base64.decode(message, 0);
            IvParameterSpec zeroIv = new IvParameterSpec(bytes);
            SecretKeySpec key = new SecretKeySpec(bytes,"DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE,key,zeroIv);
            byte[] doFinal = cipher.doFinal(decode);
            return new String(doFinal);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}