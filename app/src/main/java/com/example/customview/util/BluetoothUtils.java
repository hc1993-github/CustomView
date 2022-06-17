package com.example.customview.util;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.customview.bean.DeviceBean;

import java.util.ArrayList;

public class BluetoothUtils {
    final String TAG = getClass().getName();
    Context context;
    private static BluetoothUtils bluetoothInstance;
    private BluetoothAdapter bluetoothAdapter ;
    private BluetoothInterface bluetoothInterface;
    private BluetoothUtils (){}
    private String dev_mac_adress = "";
    ArrayList<DeviceBean> deviceBeans = new ArrayList<>();

    public static BluetoothUtils getInstance() {
        if (bluetoothInstance == null) {
            bluetoothInstance = new BluetoothUtils();
        }
        return bluetoothInstance;
    }

    public void initBluetooth(Context context){
        this.context = context;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        registerBroadcas(context);
    }
    public  void setBluetoothListener(BluetoothInterface bluetoothInterface){
        this.bluetoothInterface = bluetoothInterface;
    }
    private void registerBroadcas(Context context){
        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);
        intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intent.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.registerReceiver(bluetoothBroadcast, intent);

    }

    BroadcastReceiver bluetoothBroadcast = new BroadcastReceiver(){

        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())) {
                /* 搜索结果 */
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if( device.getName() != null && !dev_mac_adress.contains(device.getAddress())){
                    deviceBeans.add(new DeviceBean(device.getName(),device.getAddress()));
                    dev_mac_adress += device.getAddress();
                }
            }else if(BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(intent.getAction())){
                //正在搜索
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(intent.getAction())){
                // 搜索完成
                dev_mac_adress = "";
                bluetoothInterface.getBluetoothList(deviceBeans);
                deviceBeans.clear();
            }
        }
    };


    /** 开启蓝牙 */
    @SuppressLint("MissingPermission")
    public void enable(){
        if (bluetoothAdapter !=null && !bluetoothAdapter.isEnabled()){
            bluetoothAdapter.enable();
        }
    }
    /** 关闭蓝牙 */
    @SuppressLint("MissingPermission")
    public void disable(){
        if (bluetoothAdapter !=null && bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();
        }
    }

    /** 取消搜索 */
    @SuppressLint("MissingPermission")
    public void cancelDiscovery(){
        if(isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
        }
    }

    /** 开始搜索 */
    @SuppressLint("MissingPermission")
    public void startDiscovery(){
        if (bluetoothAdapter !=null && bluetoothAdapter.isEnabled()){
            bluetoothAdapter.startDiscovery();
        }
    }

    /** 判断蓝牙是否打开 */
    public boolean isEnabled(){
        if (bluetoothAdapter !=null){
            return bluetoothAdapter.isEnabled();
        }
        return false;
    }
    /** 判断当前是否正在查找设备，是返回true */
    @SuppressLint("MissingPermission")
    public boolean isDiscovering(){
        if (bluetoothAdapter !=null){
            return bluetoothAdapter.isDiscovering();
        }
        return false;
    }

    public void onDestroy(){
        context.unregisterReceiver(bluetoothBroadcast);
    }
    public interface BluetoothInterface{
        /* 获取蓝牙列表 */
        void getBluetoothList(ArrayList<DeviceBean> deviceBeans);

    }
}
