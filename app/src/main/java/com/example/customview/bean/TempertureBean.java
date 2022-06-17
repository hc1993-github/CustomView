package com.example.customview.bean;

public class TempertureBean {
    String time;
    String temp1;
    String temp2;
    String temp3;
    boolean isSelected;
    public TempertureBean(String time, String temp1, String temp2, String temp3,boolean isSelected) {
        this.time = time;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
        this.isSelected = isSelected;
    }

    public String getTime() {
        return time;
    }

    public String getTemp1() {
        return temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
