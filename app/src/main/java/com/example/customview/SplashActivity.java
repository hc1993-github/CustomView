package com.example.customview;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.customview.bean.LoginResultBean;
import com.example.customview.constant.GlobalData;
import com.example.customview.util.MD5Util;
import com.example.customview.util.OkhttpUtil;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

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
    String string;
    String downloadMD5;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    AnalyseVersion(string);
                    break;
                case 2:
                    checkMD5();
                    break;
                case 3:
                    apkInstall();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getVersion();
            }else {
                Toast.makeText(this,"?????????????????????,??????????????????",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getVersion() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            int versionCode = pi.versionCode;
            Request request = new Request.Builder().url(GlobalData.baseIp+GlobalData.versionPort+GlobalData.versionUrl+versionCode+"&androidVersion=23").build();
            OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    string = response.body().string();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void AnalyseVersion(String message) {
        if(message.contains("????????????")){
            Toast.makeText(SplashActivity.this,"??????????????????",Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
            String ip = preferences.getString("ip", "");
            String port = preferences.getString("port", "");
            String name = preferences.getString("name", "");
            String password = preferences.getString("password", "");
            if(!"".equals(ip) && !"".equals(port) && !"".equals(name) && !"".equals(password)){
                doLogin(name,password);
            }else {
                startActivity(new Intent(this,LoginActivity.class));
                finish();
            }
            //doLogin("4600000000","Dev123123");
        }else {
            Toast.makeText(SplashActivity.this,"???????????????,???????????????...",Toast.LENGTH_SHORT).show();
            String result = AnalyseDES(message,secretKey);
            JSONObject jsonObject = JSONArray.parseObject(result);
            downLoadZipAndInstall(jsonObject.getString("path"),jsonObject.getString("md5"),jsonObject.getString("compressPwd"));
        }
    }

    private void doLogin(String name, String password) {
        FormBody.Builder formBody =  new FormBody.Builder();
        formBody.add("current.code",name);
        formBody.add("current.pwd", MD5Util.md5(password));
        Request request = new Request.Builder().url(GlobalData.baseIp+GlobalData.loginPort+GlobalData.loginUrl).post(formBody.build()).build();
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
        if(message.contains("????????????")){
            Gson gson = new Gson();
            LoginResultBean bean = gson.fromJson(message, LoginResultBean.class);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("loginResultBean",bean);
            startActivity(intent);
            finish();
        }else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private void downLoadZipAndInstall(String downLoadPath,String md5,String unZipPwd) {
        downloadMD5 = md5;
        Request request = new Request.Builder().url(downLoadPath).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                downLoad(inputStream);
            }
        });
    }

    private void downLoad(InputStream inputStream) {
        FileOutputStream fileOutputStream = null;
        File file =null;
        try {
            file = new File(Environment.getExternalStorageDirectory(),"ColdchainPrint.apk");
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024*8];
            int length = 0;
            while ((length=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer,0,length);
                fileOutputStream.flush();
            }
            handler.sendEmptyMessageDelayed(2,3000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void checkMD5(){
        File file = new File(Environment.getExternalStorageDirectory(),"ColdchainPrint.apk");
        String md5ByFile = getMd5ByFile(file);
        if(!downloadMD5.equals(md5ByFile)){
            Toast.makeText(this,"????????????,????????????",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        handler.sendEmptyMessageDelayed(3,3000);

    }
    private void apkInstall(){
        File file = new File(Environment.getExternalStorageDirectory(),"ColdchainPrint.apk");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(this, getPackageName() + ".FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        startActivity(intent);
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

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 1);
        }else {
            getVersion();
        }
    }

    public String getMd5ByFile(File file){
        FileInputStream in =null ;
        StringBuffer sb = new StringBuffer();
        try {
            in = new FileInputStream(file);
            FileChannel channel = in.getChannel();
            long position = 0;
            long total = file.length();
            long page = 1024 * 1024 * 500;
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            while (position < total) {
                long size = page <= total - position ? page : total - position;
                MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, position, size);
                position += size;
                md5.update(byteBuffer);
            }
            byte[] b = md5.digest();

            for (int i = 0; i < b.length; i++) {
                sb.append(byteToChars(b[i]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString().toLowerCase();
    }
    private char[] byteToChars(byte b) {
        int h = ((b & 0xf0) >> 4);
        int l = (b & 0x0f);
        char[] r = new char[2];
        r[0] = intToChart(h);
        r[1] = intToChart(l);

        return r;
    }
    private char intToChart(int i) {
        if (i < 0 || i > 15) {
            return ' ';
        }
        if (i < 10) {
            return (char) (i + 48);
        } else {
            return (char) (i + 55);
        }
    }
}