package com.example.customview;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.customview.adapter.HomeLeftAdapter;
import com.example.customview.adapter.HomeTemptureAdapter;
import com.example.customview.bean.DeviceBean;
import com.example.customview.bean.BlueteethDeviceBean;
import com.example.customview.bean.DeviceTask;
import com.example.customview.bean.TempertureBean;
import com.example.customview.bean.LoginResultBean;
import com.example.customview.constant.GlobalData;
import com.example.customview.util.BluetoothUtils;
import com.example.customview.util.OkhttpUtil;
import com.example.customview.view.CustomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements BluetoothUtils.BluetoothInterface,
        View.OnClickListener,HomeLeftAdapter.LeftItemClick,
        Toolbar.OnMenuItemClickListener,HomeTemptureAdapter.TempOnItemClick,
        DatePickerDialog.OnDateSetListener,CustomDialog.DialogOnItemClick,
        CustomDialog.OnClickListener{
    ImageView img_home_task_list;
    TextView tv_home_task_list_deviceName;
    ImageView img_direction;
    TextView tv_currentState;
    ImageView img_left;
    ImageView img_right;
    TextView tv_dataTime;
    TextView tv_taskState;
    TextView tv_taskInfo;
    ImageView img_allPrintSelect;
    TextView liner_tv_ku_one;
    TextView liner_tv_ku_two;
    TextView liner_tv_ku_three;
    RecyclerView recyclerView;
    List<TempertureBean> tempertureBeans;
    HomeTemptureAdapter temptureAdapter;
    TextView tv_noData;
    Toolbar toolbar;
    DrawerLayout drawer_layout;
    int mYear;
    int mMonth;
    int mDayOfMonth;
    DatePickerDialog dialog_dateTime;
    CustomDialog dialog_deviceNames;
    CustomDialog dialog_stateNames;
    CustomDialog exitdialog;
    CustomDialog blueteethdialog;
    List<DeviceBean> deviceNames;
    List<DeviceBean> stateNames;
    List<DeviceBean> blueteethNames;
    String department_id;
    String currentDeviceId;
    List<DeviceTask> tasks_am;
    HomeLeftAdapter adapter_am;
    List<DeviceTask> tasks_pm;
    HomeLeftAdapter adapter_pm;
    RecyclerView recyclerView_am;
    RecyclerView recyclerView_pm;
    TextView am_tv;
    TextView pm_tv;
    ImageView img_am_tasks;
    ImageView img_pm_tasks;
    boolean am_task_selected;
    boolean pm_task_selected;
    boolean all_tempertures_selected;
    TextView tv_drawlayout_left_loginName;
    TextView tvdrawlayout_left_loginDept;
    ProgressBar progress_refresh;
    Calendar calendar;
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    String devices = (String) msg.obj;
                    AnalyseDevices(devices);
                    break;
                case 1:
                    requestDeviceInfos();
                    break;
                case 2:
                    progress_refresh.setVisibility(View.GONE);
                    String tasks = (String) msg.obj;
                    AnalyseDeviceTasks(tasks);
                    break;
                case 3:
                    progress_refresh.setVisibility(View.GONE);
                    String taskId = (String) msg.obj;
                    requestTempturesByTaskId(taskId);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BluetoothUtils.getInstance().initBluetooth(this);
        BluetoothUtils.getInstance().setBluetoothListener(this);
        initViews();
        initOthers();
        initEvents();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private void initDatas() {
        Intent intent = getIntent();
        LoginResultBean bean = (LoginResultBean) intent.getSerializableExtra("loginResultBean");
        department_id = bean.getDeptId();
        tv_drawlayout_left_loginName.setText("登录用户:"+bean.getDeptName());
        tvdrawlayout_left_loginDept.setText("登录部门:"+bean.getDeptFullName());
        requestDeviceInfos();
        requestDeviceTasks();
    }

    private void initEvents() {
        tv_dataTime.setOnClickListener(this);
        img_home_task_list.setOnClickListener(this);
        img_right.setOnClickListener(this);
        img_left.setOnClickListener(this);
        tv_home_task_list_deviceName.setOnClickListener(this);
        tv_currentState.setOnClickListener(this);
        adapter_am.setLeftItemClick(this);
        adapter_pm.setLeftItemClick(this);
        img_am_tasks.setOnClickListener(this);
        img_pm_tasks.setOnClickListener(this);
        tv_taskState.setOnClickListener(this);
        toolbar.setOnMenuItemClickListener(this);
        img_allPrintSelect.setOnClickListener(this);
    }

    private void initOthers() {
        calendar = Calendar.getInstance();
        toolbar.inflateMenu(R.menu.home_more_menu);
        dialog_dateTime = new DatePickerDialog(this, this,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH));
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH)+1;
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        tv_dataTime.setText(mYear+"-"+mMonth+"-"+mDayOfMonth);

        deviceNames = new ArrayList<>();
        dialog_deviceNames = new CustomDialog.Builder().setLayout(R.layout.activity_home_devicenames_dialog).setDatas(deviceNames)
                .setRecyclerViewId(R.id.recyclerView_deviceNames).setContext(this).setType(1).build();
        dialog_deviceNames.setDialogOnItemClick(this);
        stateNames = new ArrayList<>();
        stateNames.add(new DeviceBean("正常","正常",0));
        stateNames.add(new DeviceBean("除霜","除霜-记录不报警",2));
        stateNames.add(new DeviceBean("盘苗","盘苗-记录不报警",6));
        stateNames.add(new DeviceBean("停用","停用-不记录不报警",3));
        dialog_stateNames = new CustomDialog.Builder().setLayout(R.layout.activity_home_currentstates_dialog).setDatas(stateNames)
                .setRecyclerViewId(R.id.recyclerView_currentStates).setContext(this).setType(1).build();
        dialog_stateNames.setDialogOnItemClick(this);
        blueteethNames = new ArrayList<>();
        blueteethdialog = new CustomDialog.Builder().setLayout(R.layout.activity_home_blueteeth_dialog).setDatas(blueteethNames)
                .setRecyclerViewId(R.id.recyclerView_blueteeths).setContext(this).setType(2).build();
        blueteethdialog.setDialogOnItemClick(this);
        exitdialog = new CustomDialog.Builder().setLayout(R.layout.activity_home_exit_dialog).setContext(this)
                .setLeftBtnId(R.id.exit_cancel).setRightBtnId(R.id.exit_confirm).setListener(this).build();
    }

    private void initViews() {
        img_home_task_list = findViewById(R.id.img_home_task_list);
        tv_home_task_list_deviceName = findViewById(R.id.tv_home_task_list_deviceName);
        img_direction = findViewById(R.id.img_direction);
        tv_currentState = findViewById(R.id.tv_currentState);
        img_left = findViewById(R.id.img_left);
        img_right = findViewById(R.id.img_right);
        tv_dataTime = findViewById(R.id.tv_dataTime);
        tv_taskState = findViewById(R.id.tv_taskState);
        tv_taskInfo = findViewById(R.id.tv_taskInfo);
        img_allPrintSelect = findViewById(R.id.img_allPrintSelect);
        liner_tv_ku_one = findViewById(R.id.liner_tv_ku_one);
        liner_tv_ku_two = findViewById(R.id.liner_tv_ku_two);
        liner_tv_ku_three = findViewById(R.id.liner_tv_ku_three);
        tv_noData = findViewById(R.id.tv_noData);
        toolbar = findViewById(R.id.toolbar);
        drawer_layout = findViewById(R.id.drawer_layout);
        am_tv = findViewById(R.id.am_tv);
        pm_tv = findViewById(R.id.pm_tv);
        img_am_tasks = findViewById(R.id.img_am_tasks);
        img_pm_tasks = findViewById(R.id.img_pm_tasks);
        tv_drawlayout_left_loginName = findViewById(R.id.drawlayout_left_loginName);
        tvdrawlayout_left_loginDept = findViewById(R.id.drawlayout_left_loginDept);
        progress_refresh = findViewById(R.id.progress_refresh);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tempertureBeans = new ArrayList<>();
        temptureAdapter = new HomeTemptureAdapter(tempertureBeans,this);
        temptureAdapter.setTempOnItemClick(this);
        recyclerView.setAdapter(temptureAdapter);

        recyclerView_am = findViewById(R.id.recyclerView_am);
        recyclerView_am.setLayoutManager(new LinearLayoutManager(this));
        tasks_am = new ArrayList<>();
        adapter_am = new HomeLeftAdapter(tasks_am,this);
        recyclerView_am.setAdapter(adapter_am);

        recyclerView_pm = findViewById(R.id.recyclerView_pm);
        recyclerView_pm.setLayoutManager(new LinearLayoutManager(this));
        tasks_pm = new ArrayList<>();
        adapter_pm = new HomeLeftAdapter(tasks_pm,this);
        recyclerView_pm.setAdapter(adapter_pm);
    }

    private void updateDeviceStatus(int status) {
        Request request = new Request.Builder().url(GlobalData.baseIp+GlobalData.updateDeviceStatusPort+GlobalData.updateDeviceStatusUrl+currentDeviceId+"&newstatus="+ status).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                handler.sendEmptyMessage(1);
            }
        });
    }

    private void setStatus(int status) {
        if(status==0){
            tv_currentState.setText("正常");
        }else if(status==2){
            tv_currentState.setText("除霜");
        }else if(status==6){
            tv_currentState.setText("盘苗");
        }else {
            tv_currentState.setText("停用");
        }
    }

    private void requestDeviceInfos(){
        Request request = new Request.Builder().url(GlobalData.baseIp+GlobalData.requestDeviceInfosPort+GlobalData.requestDeviceInfosUrl+department_id).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                Message message = Message.obtain();
                message.what = 0;
                message.obj = string;
                handler.sendMessage(message);
            }
        });
    }

    private void requestDeviceTasks(){
        Request request = new Request.Builder().url(GlobalData.baseIp+GlobalData.requestDeviceTasksPort+GlobalData.requestDeviceTasksUrl+mYear+"-"+mMonth+"-"+mDayOfMonth+"&deviceId="+currentDeviceId).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String string = response.body().string();
                Message message = Message.obtain();
                message.what = 2;
                message.obj = string;
                handler.sendMessageDelayed(message,1000);
            }
        });
    }

    private void AnalyseDevices(String string) {
        deviceNames.clear();
        try {
            JSONArray jsonArray = JSONArray.parseArray(string);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String deviceId = jsonObject.getString("id");
                String deviceName = jsonObject.getString("name");
                Integer deviceType = jsonObject.getInteger("status");
                deviceNames.add(new DeviceBean(deviceId,deviceName,deviceType));
            }
            if(currentDeviceId==null){
                currentDeviceId = deviceNames.get(0).getId();
                tv_home_task_list_deviceName.setText(deviceNames.get(0).getName());
                setStatus(deviceNames.get(0).getStatus());
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"设备信息解析失败",Toast.LENGTH_SHORT).show();
        }

    }

    private void AnalyseDeviceTasks(String tasks) {
        tasks_am.clear();
        tasks_pm.clear();
        try {
            JSONArray jsonArray = JSONArray.parseArray(tasks);
            for(int i=0;i<jsonArray.size();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String taskId = jsonObject.getString("id");
                String taskName = jsonObject.getString("task");
                String taskDeviceId = jsonObject.getString("deviceId");
                String taskDeviceCode = jsonObject.getString("deviceCode");
                String taskStartTime = jsonObject.getString("startTime");
                String taskEndTime = jsonObject.getString("endTime");
                int xsp = Integer.parseInt(taskStartTime.substring(11, 13).substring(0,1));
                int xss = Integer.parseInt(taskStartTime.substring(11, 13).substring(1));
                if(xsp==0 || (xsp==1 && xss==0) || (xsp==1 && xss==1)){
                    tasks_am.add(new DeviceTask(taskId,taskName,taskDeviceId,taskDeviceCode,taskStartTime,taskEndTime,false,false));
                }else {
                    tasks_pm.add(new DeviceTask(taskId,taskName,taskDeviceId,taskDeviceCode,taskStartTime,taskEndTime,false,false));
                }
            }
            if(tasks_pm.size()>0 && tasks_am.size()==0){
                DeviceTask task = tasks_pm.get(tasks_pm.size() - 1);
                task.setRed(true);
                task.setSelected(true);
                recyclerView_am.setVisibility(View.GONE);
                recyclerView_pm.setVisibility(View.VISIBLE);
                am_tv.setVisibility(View.VISIBLE);
                pm_tv.setVisibility(View.GONE);

                String name = tasks_pm.get(tasks_pm.size()-1).getTaskName();
                String start = tasks_pm.get(tasks_pm.size()-1).getTaskStartTime().substring(11);
                String end = tasks_pm.get(tasks_pm.size()-1).getTaskEndTime()==null?"": tasks_pm.get(tasks_pm.size()-1).getTaskEndTime().substring(11);
                tv_taskInfo.setText(name+"   "+start+" - "+end);
                if(tasks_pm.get(tasks_pm.size()-1).getTaskEndTime()==null){
                    tv_taskState.setText("运输中");
                }else {
                    tv_taskState.setText("已停运");
                }
                requestTempturesByTaskId(task.getTaskId());
            }else if(tasks_pm.size()==0 && tasks_am.size()>0){
                DeviceTask task = tasks_am.get(tasks_am.size() - 1);
                task.setRed(true);
                task.setSelected(true);
                recyclerView_am.setVisibility(View.VISIBLE);
                recyclerView_pm.setVisibility(View.GONE);
                am_tv.setVisibility(View.GONE);
                pm_tv.setVisibility(View.VISIBLE);

                String name = tasks_am.get(tasks_am.size()-1).getTaskName();
                String start = tasks_am.get(tasks_am.size()-1).getTaskStartTime().substring(11);
                String end = tasks_am.get(tasks_am.size()-1).getTaskEndTime()==null?"": tasks_am.get(tasks_am.size()-1).getTaskEndTime().substring(11);
                tv_taskInfo.setText(name+"   "+start+" - "+end);
                if(tasks_am.get(tasks_am.size()-1).getTaskEndTime()==null){
                    tv_taskState.setText("运输中");
                }else {
                    tv_taskState.setText("已停运");
                }
                requestTempturesByTaskId(task.getTaskId());
            }else if(tasks_pm.size()>0 && tasks_am.size()>0){
                DeviceTask task = tasks_pm.get(tasks_pm.size() - 1);
                task.setRed(true);
                task.setSelected(true);
                recyclerView_am.setVisibility(View.VISIBLE);
                recyclerView_pm.setVisibility(View.VISIBLE);
                am_tv.setVisibility(View.GONE);
                pm_tv.setVisibility(View.GONE);

                String name = tasks_pm.get(tasks_pm.size()-1).getTaskName();
                String start = tasks_pm.get(tasks_pm.size()-1).getTaskStartTime().substring(11);
                String end = tasks_pm.get(tasks_pm.size()-1).getTaskEndTime()==null?"": tasks_pm.get(tasks_pm.size()-1).getTaskEndTime().substring(11);
                tv_taskInfo.setText(name+"   "+start+" - "+end);
                if(tasks_pm.get(tasks_pm.size()-1).getTaskEndTime()==null){
                    tv_taskState.setText("运输中");
                }else {
                    tv_taskState.setText("已停运");
                }
                requestTempturesByTaskId(task.getTaskId());
            }else if(tasks_am.size()==0 && tasks_pm.size()==0){
                recyclerView_am.setVisibility(View.GONE);
                recyclerView_pm.setVisibility(View.GONE);
                am_tv.setVisibility(View.VISIBLE);
                pm_tv.setVisibility(View.VISIBLE);

                tv_taskInfo.setText("当天无任务");
                tv_taskState.setText("已停运");
                tv_noData.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                all_tempertures_selected = false;
                img_allPrintSelect.setImageResource(R.drawable.img_white_printer);
                liner_tv_ku_one.setText("一");
                liner_tv_ku_two.setText("一");
                liner_tv_ku_three.setText("一");
            }
            adapter_am.notifyDataSetChanged();
            adapter_pm.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"任务信息获取失败",Toast.LENGTH_SHORT).show();
        }
    }

    private void adapterItemSelected(DeviceTask task) {
        String name = task.getTaskName();
        String start = task.getTaskStartTime().substring(11);
        String end = task.getTaskEndTime()==null?"": task.getTaskEndTime().substring(11);
        tv_taskInfo.setText(name+"   "+start+" - "+end);
        if(task.getTaskEndTime()==null){
            tv_taskState.setText("运输中");
        }else {
            tv_taskState.setText("已停运");
        }
        drawer_layout.closeDrawer(Gravity.LEFT);
        progress_refresh.setVisibility(View.VISIBLE);
        img_allPrintSelect.setImageResource(R.drawable.img_white_printer);
        all_tempertures_selected = false;
        Message message = new Message();
        message.what = 3;
        message.obj = task.getTaskId();
        handler.sendMessageDelayed(message,1000);
    }

    private void requestTempturesByTaskId(String taskId){
        tempertureBeans.clear();
        tempertureBeans.add(new TempertureBean("02-22","22.5°C","22.5°C","22.5°C",false));
        tempertureBeans.add(new TempertureBean("02-22","22.4°C","22.4°C","22.4°C",false));
        tempertureBeans.add(new TempertureBean("02-22","22.3°C","22.3°C","22.3°C",false));
        tempertureBeans.add(new TempertureBean("02-22","22.2°C","22.2°C","22.2°C",false));
        temptureAdapter.notifyDataSetChanged();
        if(tempertureBeans.size()>0){
            tv_noData.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            liner_tv_ku_one.setText("50420045");
            liner_tv_ku_two.setText("82006845");
            liner_tv_ku_three.setText("52007889");
        }else {
            tv_noData.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            liner_tv_ku_one.setText("一");
            liner_tv_ku_two.setText("一");
            liner_tv_ku_three.setText("一");
        }
    }

    private void createOrUpdateCurrentDayTaskTime() {
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH)+1;
        mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        tv_dataTime.setText(mYear+"-"+mMonth+"-"+mDayOfMonth);
        dialog_dateTime.updateDate(mYear,mMonth-1,mDayOfMonth);
        tv_taskState.setText("运输中");
        Request request = new Request.Builder().url(GlobalData.baseIp+GlobalData.createOrUpdateCurrentDayTaskTimePort+GlobalData.createOrUpdateCurrentDayTaskTimeUrl+currentDeviceId).build();
        OkhttpUtil.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                requestDeviceTasks();
            }
        });

    }

    @Override
    public void getBluetoothList(ArrayList<BlueteethDeviceBean> deviceBeans) {
        blueteethNames.clear();
        for(int i=0;i<deviceBeans.size();i++){
            blueteethNames.add(new DeviceBean(deviceBeans.get(i).getName(),deviceBeans.get(i).getAddress(),1));
        }
        TextView textView = blueteethdialog.findViewById(R.id.tv_blueteeth);
        blueteethdialog.setData(blueteethNames);
        if(blueteethNames.size()>0){
            textView.setText("已搜索到的蓝牙");
        }else {
            textView.setText("未搜索到蓝牙");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    searchBlooTeeth();
                } else {
                    Toast.makeText(this,"拒绝了蓝牙权限,无法使用蓝牙打印",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);
            }
        }else {
            searchBlooTeeth();
        }
    }

    private void searchBlooTeeth(){
        if(!BluetoothUtils.getInstance().isEnabled()){
            BluetoothUtils.getInstance().enable();
        }
        blueteethdialog.show();
        BluetoothUtils.getInstance().startDiscovery();
    }

    private void imgRightOrLeftClick(boolean isRight){
        am_task_selected = false;
        pm_task_selected = false;
        if(isRight){
            setAfter(calendar);
        }else {
            setBefore(calendar);
        }
        tv_dataTime.setText(getYear()+"-"+getMonth()+"-"+getDate());
        dialog_dateTime.updateDate(getYear(),getMonth()-1,getDate());
        progress_refresh.setVisibility(View.VISIBLE);
        requestDeviceTasks();
    }

    private Calendar setBefore(Calendar cal){
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE,day-1);
        return cal;
    }
    private Calendar setAfter(Calendar cal){
        int day = cal.get(Calendar.DATE);
        cal.set(Calendar.DATE,day+1);
        return cal;
    }
    private int getYear(){
        return calendar.get(Calendar.YEAR);
    }
    private int getMonth(){
        return calendar.get(Calendar.MONTH)+1;
    }
    private int getDate(){
        return calendar.get(Calendar.DATE);
    }
    private void selectedAmOrPmTasks(boolean isAm){
        if(isAm){
            if(!am_task_selected){
                am_task_selected = true;
                img_am_tasks.setImageResource(R.drawable.img_green_printer);
            }else {
                am_task_selected = false;
                img_am_tasks.setImageResource(R.drawable.img_white_printer);
            }
            adapter_am.setAllSelected();
        }else {
            if(!pm_task_selected){
                pm_task_selected = true;
                img_pm_tasks.setImageResource(R.drawable.img_green_printer);
            }else {
                pm_task_selected = false;
                img_pm_tasks.setImageResource(R.drawable.img_white_printer);
            }
            adapter_pm.setAllSelected();
        }
    }

    private void selectedAllTemptures(){
        if(!all_tempertures_selected){
            all_tempertures_selected = true;
            img_allPrintSelect.setImageResource(R.drawable.img_green_printer);
        }else {
            all_tempertures_selected = false;
            img_allPrintSelect.setImageResource(R.drawable.img_white_printer);
        }
        temptureAdapter.selectedAll(all_tempertures_selected);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_dataTime:
                dialog_dateTime.show();
                break;
            case R.id.img_home_task_list:
                drawer_layout.openDrawer(Gravity.LEFT);
                break;
            case R.id.img_right:
                imgRightOrLeftClick(true);
                break;
            case R.id.img_left:
                imgRightOrLeftClick(false);
                break;
            case R.id.tv_home_task_list_deviceName:
                am_task_selected = false;
                pm_task_selected = false;
                dialog_deviceNames.show();
                dialog_deviceNames.setData(deviceNames);
                break;
            case R.id.tv_currentState:
                dialog_stateNames.show();
                dialog_stateNames.setData(stateNames);
                break;
            case R.id.img_am_tasks:
                selectedAmOrPmTasks(true);
                break;
            case R.id.img_pm_tasks:
                selectedAmOrPmTasks(false);
                break;
            case R.id.tv_taskState:
                createOrUpdateCurrentDayTaskTime();
                break;
            case R.id.img_allPrintSelect:
                selectedAllTemptures();
                break;
        }
    }

    @Override
    public void itemClick(int position, DeviceTask task) {
        adapterItemSelected(task);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.blueTeethPrint:
                checkPermission();
                break;
            case R.id.exit:
                exitdialog.show();
                break;
        }
        return false;
    }

    @Override
    public void itemClick(int position, TempertureBean bean) {
        for(TempertureBean b:tempertureBeans){
            if(!b.isSelected()){
                all_tempertures_selected = false;
                break;
            }
            all_tempertures_selected = true;
        }
        if(all_tempertures_selected){
            img_allPrintSelect.setImageResource(R.drawable.img_green_printer);
        }else {
            img_allPrintSelect.setImageResource(R.drawable.img_white_printer);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month+1;
        mDayOfMonth = dayOfMonth;
        tv_dataTime.setText(year+"-"+(month+1)+"-"+dayOfMonth);
        progress_refresh.setVisibility(View.VISIBLE);
        img_allPrintSelect.setImageResource(R.drawable.img_white_printer);
        all_tempertures_selected = false;
        requestDeviceTasks();
    }

    @Override
    public void itemClick(CustomDialog dialog, String id, String name, int status) {
        if(dialog==dialog_deviceNames){
            currentDeviceId = id;
            tv_home_task_list_deviceName.setText(name);
            setStatus(status);
            dialog_deviceNames.dismiss();
            progress_refresh.setVisibility(View.VISIBLE);
            img_allPrintSelect.setImageResource(R.drawable.img_white_printer);
            all_tempertures_selected = false;
            mYear = calendar.get(Calendar.YEAR);
            mMonth = calendar.get(Calendar.MONTH)+1;
            mDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            tv_dataTime.setText(mYear+"-"+mMonth+"-"+mDayOfMonth);
            dialog_dateTime.updateDate(mYear,mMonth-1,mDayOfMonth);
            requestDeviceTasks();
        }else if(dialog==dialog_stateNames){
            updateDeviceStatus(status);
            tv_currentState.setText(id);
            dialog_stateNames.dismiss();
        }else if(dialog==blueteethdialog){
            blueteethdialog.dismiss();
            Toast.makeText(HomeActivity.this,id+name,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLeftClick(Dialog dialog) {
        exitdialog.dismiss();
    }

    @Override
    public void onRightClick(Dialog dialog) {
        exitdialog.dismiss();
        finish();
    }
}