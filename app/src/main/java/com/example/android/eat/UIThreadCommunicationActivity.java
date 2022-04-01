package com.example.android.eat;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class UIThreadCommunicationActivity extends AppCompatActivity {

    private final String LOG_TAG = UIThreadCommunicationActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uithread_communication);
        setupPostToUIThreadButton();
        setupPostFromUIThreadToItselfButton();
    }

    private void setupPostFromUIThreadToItselfButton() {
        Button fromUIToItself = findViewById(R.id.button_postFromUIThreadToItself);
        fromUIToItself.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "Post from UI thread to itself";
                String message = "On clicking this button a task message " +
                        "is posted from the UI thread to itself" +
                        " it will bypass the message passing and execute" +
                        " immediately within the currently processed message" +
                        " on the UI thread with the convenience method Activity.runOnUiThread(Runnable)";
                Utils.displayDialog(message, title, UIThreadCommunicationActivity.this);
                postFromUIThreadToUIThread();
            }
        });
    }

    private void setActivityTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Communicate With the UI Thread");
        }
    }
    private void setupPostToUIThreadButton() {
        Button postToUIThread = findViewById(R.id.button_postToUIThread);
        postToUIThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "Post to UI Thread";
                String message = "On clicking this button, " +
                        " a message is inserted in the queue of the UI thread. " +
                        "Independent of the posting thread," +
                        "If it is the UI thread that posts the message to itself, t" +
                        "he message can be processed at the earliest after the current message is done";
                Utils.displayDialog(message, title, UIThreadCommunicationActivity.this);
                postToUIThread();
            }
        });
    }

    private void postToUIThread(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Executing a task posted to the UI thread. " +
                        "This message is supposed to execute after the current message is done");
            }
        });
        Log.d(LOG_TAG, "This code is part of a message being processed. " +
                "It is supposed to be executed before the posted message");
    }
    private void postFromUIThreadToUIThread(){
        runOnUiThread(() -> Log.d(LOG_TAG, "This message is sent from the UI thread to " +
                "itself, it should bypass the massage passing and execute immediately " +
                "within the currently processed message on the UI Thread."));
        Log.d(LOG_TAG, "This code should be executed after processing the message.");
    }
}