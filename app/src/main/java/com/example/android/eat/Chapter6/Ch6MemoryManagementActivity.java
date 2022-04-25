package com.example.android.eat.Chapter6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class Ch6MemoryManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch6_memory_management);
        Utils.setActivityTitle(this, "Memory Management");
    }
}