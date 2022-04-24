package com.example.android.eat.Chapter5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.renderscript.ScriptGroup;
import android.widget.Button;

import com.example.android.eat.R;
import com.example.android.eat.Utils;

public class Ch5InterProcessCommunicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch5_inter_process_communication);

        setupButtons();

        Utils.setActivityTitle(this, "Interprocess Communication");
    }

    private void setupButtons() {
        setupSynchronousRPCExampleButton();
        setupAsynchronousRPCExampleButton();
        setupMessengerOneWayMessagePassing();
        setUpMessengerTwoWayMessagePassing();
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
                    MessengerOneWayActivity.class);
            startActivity(intent);
        });
    }
    private void setUpMessengerTwoWayMessagePassing(){
        Button messengerTwoWay = findViewById(R.id.button_messengerTwoWay);
        messengerTwoWay.setOnClickListener(view -> {
            Intent intent = new Intent(Ch5InterProcessCommunicationActivity.this,
                    MessengerTwoWayActivity.class);
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