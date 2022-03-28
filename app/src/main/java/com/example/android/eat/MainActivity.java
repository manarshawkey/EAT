package com.example.android.eat;

/*
 * This project is intended to be a working of the examples
 * included in Efficient Android Threads Book.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button ch4 = findViewById(R.id.button_chapter4);
        ch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        Ch4ThreadCommunicationActivity.class);
                startActivity(intent);
            }
        });
    }
}