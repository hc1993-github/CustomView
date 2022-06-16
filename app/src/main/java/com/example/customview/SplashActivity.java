package com.example.customview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.customview.util.MD5Util;
import com.example.customview.util.OkhttpUtil;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
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
            Request request = new Request.Builder().url("http://221.224.159.210:38283/datacenter/outward/package/autoUpdateApp?type=ColdchainPrint&vnumCurrent="+6+"&androidVersion=23").build();
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
//            SharedPreferences preferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
//            String ip = preferences.getString("ip", "");
//            String port = preferences.getString("port", "");
//            String name = preferences.getString("name", "");
//            String password = preferences.getString("password", "");
//            if(!"".equals(ip) && !"".equals(port) && !"".equals(name) && !"".equals(password)){
//                doLogin(name,password);
//            }else {
//                startActivity(new Intent(this,MainActivity.class));
//                finish();
//            }
            doLogin("4600000000","Dev123123");
        }else {
            String result = AnalyseDES(message,secretKey);
            JSONObject jsonObject = JSONArray.parseObject(result);
            String downLoadPath = jsonObject.getString("path");
            String md5 = jsonObject.getString("md5");
            String unZipPwd = jsonObject.getString("compressPwd");
            downLoadZipAndInstall(downLoadPath,md5,unZipPwd);
        }
    }

    private void doLogin(String name, String password) {
        FormBody.Builder formBody =  new FormBody.Builder();
        formBody.add("current.code",name);
        formBody.add("current.pwd", MD5Util.md5(password));
        Request request = new Request.Builder().url("http://221.224.159.210:38111/ssmanage/accountLogin!login4Client.action").post(formBody.build()).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                AnalyseLogin(string);
            }
        });
    }

    private void AnalyseLogin(String message) {
        if(message.contains("登陆成功")){
            JSONObject jsonObject = JSONArray.parseObject(message);
            JSONObject account = jsonObject.getJSONObject("account");
            JSONObject department = jsonObject.getJSONObject("department");
            String department_id = department.getString("id");
            String department_name = department.getString("name");
            String department_fullName = department.getString("fullName");
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("department_id",department_id);
            intent.putExtra("department_name",department_name);
            intent.putExtra("department_fullName",department_fullName);
            startActivity(intent);
            finish();
        }else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
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