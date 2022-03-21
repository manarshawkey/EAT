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
        TextView hello = findViewById(R.id.textView_hello);
        hello.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LooperActivity.class);
            MainActivity.this.startActivity(intent);
        });
        Button button_twoWayMessagePassing = findViewById(R.id.button_twoWayMessage);
        button_twoWayMessagePassing.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, HandlerExampleActivity.class);
            MainActivity.this.startActivity(intent);
        });

        setupHandlerCallbackButton();
    }

    private void setupHandlerCallbackButton() {
        Button handlerCallback = findViewById(R.id.button_handlerCallbackExample);
        handlerCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HandlerCallBackActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }
}