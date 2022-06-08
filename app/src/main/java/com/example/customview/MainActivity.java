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
        tempertureView.setTemperature(0);
    }
}