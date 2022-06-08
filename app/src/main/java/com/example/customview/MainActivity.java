package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TempertureView tempertureView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tempertureView = findViewById(R.id.tempView);
        tempertureView.setPrecentTemp(-9,9,6,-6,3,-3);
        tempertureView.setTemperature(-6);
    }
}