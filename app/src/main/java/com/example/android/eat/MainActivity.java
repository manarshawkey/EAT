package com.example.android.eat;

/*
 * This project is intended to be a working of the examples
 * included in Efficient Android Threads Book.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.android.eat.Chapter4.Ch4ThreadCommunicationActivity;
import com.example.android.eat.Chapter5.Ch5InterProcessCommunicationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button ch4 = findViewById(R.id.button_chapter4);
        ch4.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    Ch4ThreadCommunicationActivity.class);
            startActivity(intent);
        });
        Button ch5 = findViewById(R.id.button_chapter5);
        ch5.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,
                    Ch5InterProcessCommunicationActivity.class);
            startActivity(intent);
        });
    }
}