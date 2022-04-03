package com.example.android.eat.Chapter5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.android.eat.R;
public class Ch5InterProcessCommunicationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch5_inter_process_communication);

        setupSynchronousRPCExampleButton();
        setActivityTitle();
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
}