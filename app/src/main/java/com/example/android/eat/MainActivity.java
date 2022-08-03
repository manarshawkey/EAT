package com.example.android.eat;

/*
 * This project is intended to be a working of the examples
 * included in Efficient Android Threads Book.
 */


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.android.eat.Chapter4.Ch4ThreadCommunicationActivity;
import com.example.android.eat.Chapter5.Ch5InterProcessCommunicationActivity;
import com.example.android.eat.Chapter6.Ch6MemoryManagementActivity;
import com.example.android.eat.Chapter7.Ch7ThreadLifeCycle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        boolean hasPermission = (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            //ask permission
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        else {

            //you have permission, create your file
        }

        Utils.setUpIntentToOpenChapterActivity((Button) findViewById(R.id.button_chapter4)
                , MainActivity.this,
                Ch4ThreadCommunicationActivity.class);

        /*ch4.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    Ch4ThreadCommunicationActivity.class);
            startActivity(intent);
        });*/
        Button ch5 = findViewById(R.id.button_chapter5);
        ch5.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    Ch5InterProcessCommunicationActivity.class);
            startActivity(intent);
        });
        Button ch6 = findViewById(R.id.button_chapter6);
        ch6.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    Ch6MemoryManagementActivity.class);
            startActivity(intent);
        });
        Button ch7 = findViewById(R.id.button_chapter7);
        ch7.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Ch7ThreadLifeCycle.class);
            startActivity(intent);
        });
    }
}