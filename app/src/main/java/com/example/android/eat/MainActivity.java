package com.example.android.eat;

/*
 * This project is intended to be a working of the examples
 * included in Efficient Android Threads Book.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView hello = findViewById(R.id.textView_hello);
        hello.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LooperActivity.class);
            MainActivity.this.startActivity(intent);
        });
    }
}