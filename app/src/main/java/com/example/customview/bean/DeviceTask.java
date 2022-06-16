package com.example.customview.bean;

public class DeviceTask {
    String taskId;
    String taskName;
    String deviceId;
    String deviceCode;
    String taskStartTime;
    String taskEndTime;
    boolean isSelected;
    boolean isRed;
    public DeviceTask(String taskId, String taskName, String deviceId, String deviceCode, String taskStartTime, String taskEndTime,boolean isSelected,boolean isRed) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.deviceId = deviceId;
        this.deviceCode = deviceCode;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
        this.isSelected = isSelected;
        this.isRed = isRed;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getTaskStartTime() {
        return taskStartTime;
    }

    public String getTaskEndTime() {
        return taskEndTime;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setTaskEndTime(String taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public boolean isRed() {
        return isRed;
    }

    public void setRed(boolean red) {
        isRed = red;
    }
}
