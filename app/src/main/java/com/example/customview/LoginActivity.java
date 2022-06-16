package com.example.customview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.customview.bean.LoginResultBean;
import com.example.customview.util.MD5Util;
import com.example.customview.util.OkhttpUtil;
import com.example.customview.view.CustomDialog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText ed_ip;
    EditText ed_port;
    EditText ed_name;
    EditText ed_password;
    Button btn_login;
    CustomDialog dialog;
    SharedPreferences preferences;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    doLogin(ed_name.getText().toString(),ed_password.getText().toString());
                    break;
                case 1:
                    Toast.makeText(LoginActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initDatas();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void initViews() {
        ed_ip = findViewById(R.id.ed_ip);
        ed_port = findViewById(R.id.ed_port);
        ed_name = findViewById(R.id.ed_name);
        ed_password = findViewById(R.id.ed_password);
        btn_login = findViewById(R.id.btn_login);
        dialog = new CustomDialog.Builder().setLayout(R.layout.activity_login_dialog).setContext(this).build();
    }

    private void initDatas() {
        preferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        String ip = preferences.getString("ip", "");
        String port = preferences.getString("port", "");
        String name = preferences.getString("name", "");
        String password = preferences.getString("password", "");
        if(!ip.equals("")){
            ed_ip.setText(ip);
        }
        if(!port.equals("")){
            ed_port.setText(port);
        }
        if(!name.equals("")){
            ed_name.setText(name);
        }
        if(!password.equals("")){
            ed_password.setText(password);
        }
    }

    private void initEvents() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ip",ed_ip.getText().toString().trim());
                editor.putString("port",ed_port.getText().toString().trim());
                editor.putString("name",ed_name.getText().toString().trim());
                editor.putString("password",ed_password.getText().toString().trim());
                editor.commit();
                handler.sendEmptyMessageDelayed(0,1500);
            }
        });
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
        dialog.dismiss();
        if(message.contains("登陆成功")){
            JSONObject jsonObject = JSONArray.parseObject(message);
            LoginResultBean bean = new LoginResultBean(jsonObject);
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("loginResultBean",bean);
            startActivity(intent);
            finish();
        }else {
            JSONObject jsonObject = JSONArray.parseObject(message);
            String string = jsonObject.getString("message");
            Message mes = Message.obtain();
            mes.what = 1;
            mes.obj = string;
            handler.sendMessage(mes);
        }
    }

}