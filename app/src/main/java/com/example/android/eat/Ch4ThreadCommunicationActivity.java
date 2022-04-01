package com.example.android.eat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Ch4ThreadCommunicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch4_thread_communication);
        setupActivityTitle();

        Button looperExamples = findViewById(R.id.button_looperExamples);
        looperExamples.setOnClickListener(view -> {
            Intent intent = new Intent(Ch4ThreadCommunicationActivity.this,
                    LooperActivity.class);
            Ch4ThreadCommunicationActivity.this.startActivity(intent);
        });
        Button handlerExamples = findViewById(R.id.button_handlerExamples);
        handlerExamples.setOnClickListener(view -> {
            Intent intent = new Intent(Ch4ThreadCommunicationActivity.this,
                    HandlerCallBackActivity.class);
            startActivity(intent);
        });
        Button pipeExample = findViewById(R.id.button_pipeExamplee);
        pipeExample.setOnClickListener(view -> {
            Intent intent = new Intent(Ch4ThreadCommunicationActivity.this,
                    PipeExampleActivity.class);
            startActivity(intent);
        });

        Button twoWayMessage = findViewById(R.id.button_twoWayMessagePassing);
        twoWayMessage.setOnClickListener(view->{
            Intent intent = new Intent(Ch4ThreadCommunicationActivity.this,
                    HandlerExampleActivity.class);
            startActivity(intent);
        });
        Button messageQueueDebug = findViewById(R.id.button_messageQueueDebugExample);
        messageQueueDebug.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Ch4ThreadCommunicationActivity.this,
                    MessageQueueDebugActivity.class);
            startActivity(intent);
        });
        Button communicateWithUIThread = findViewById(R.id.button_communicateWithUIThread);
        communicateWithUIThread.setOnClickListener(view -> {
            Intent intent = new Intent(
                    Ch4ThreadCommunicationActivity.this,
                    UIThreadCommunicationActivity.class);
            startActivity(intent);
        });
    }
    private void setupActivityTitle(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Thread Communication");
        }
    }
}