package com.example.android.eat;
/*
 * Purpose: this example simulates a long-running operation
 * that is started when the user clicks a button. The long-running
 * operation is executed on a background thread, meanwhile, the UI thread
 * displays a progress bar that is removed when the background thread
 * reports the results back to the UI thread.
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class HandlerExampleActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
    }
}