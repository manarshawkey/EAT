package com.example.android.eat.Chapter5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.Button;

import com.example.android.eat.R;
public class Ch5InterProcessCommunicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch5_inter_process_communication);

        setupButtons();

        setActivityTitle();
    }

    private void setupButtons() {
        setupSynchronousRPCExampleButton();
        setupAsynchronousRPCExampleButton();
        setupMessengerOneWayMessagePassing();
        setupLocalBinding();
    }

    private void setupAsynchronousRPCExampleButton() {
        Button synchronousRPC = findViewById(R.id.button_asynchronous_RPC);
        synchronousRPC.setOnClickListener(view -> {
            Intent intent = new Intent(Ch5InterProcessCommunicationActivity.this,
                    AsynchronousRPCActivity.class);
            startActivity(intent);
        });
    }

    private void setActivityTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Interprocess Communication");
        }
    }

    private void setupSynchronousRPCExampleButton() {
        Button synchronousRPC = findViewById(R.id.button_synchronous_RPC);
        synchronousRPC.setOnClickListener(view -> {
            Intent intent = new Intent(Ch5InterProcessCommunicationActivity.this,
                    SynchronousRPCActivity.class);
            startActivity(intent);
        });
    }
    private void setupMessengerOneWayMessagePassing(){
        Button messengerOneWay = findViewById(R.id.button_messengerOneWay);
        messengerOneWay.setOnClickListener(view -> {
            Intent intent = new Intent(Ch5InterProcessCommunicationActivity.this,
                    MessengerActivity.class);
            startActivity(intent);
        });
    }
    private void setupLocalBinding(){
        Button localBinding = findViewById(R.id.button_localBinding);
        localBinding.setOnClickListener(view -> {
            Intent intent = new Intent(Ch5InterProcessCommunicationActivity.this,
                    BindingActivity.class);
            startActivity(intent);
        });
    }
}