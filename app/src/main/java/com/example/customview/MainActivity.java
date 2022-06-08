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
        tempertureView.setPrecentTemp(-15,15,10,-10,5,-5);
        tempertureView.setTemperature(8);
    }
}