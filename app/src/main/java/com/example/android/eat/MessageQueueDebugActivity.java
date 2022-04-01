package com.example.android.eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.LogPrinter;
import android.widget.Button;

public class MessageQueueDebugActivity extends AppCompatActivity {

    private final String LOG_TAG = MessageQueueDebugActivity.class.getSimpleName();
    private Handler mWorkerHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_queue_debug);

        displayDialog();
        setupButton();
        setActivityTitle();

        Thread t = new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                mWorkerHandler = new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        Log.d(LOG_TAG, "handling message - what: " + msg.what);
                    }
                };
                Looper.myLooper().setMessageLogging(new LogPrinter(Log.DEBUG, LOG_TAG));
                Looper.loop();
            }
        };
        t.start();
    }

    private void setActivityTitle() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("Observing Message Queue Example");
        }
    }

    private void setupButton() {
        Button messageEnqueueButton = findViewById(R.id.button_messageQueueDebug);
        messageEnqueueButton.setOnClickListener(view -> {
            if(mWorkerHandler != null){
                mWorkerHandler.sendEmptyMessageDelayed(1, 2000);
                mWorkerHandler.sendEmptyMessage(2);
                mWorkerHandler.obtainMessage(3, 0, 0, new Object())
                        .sendToTarget();
                mWorkerHandler.sendEmptyMessageDelayed(4, 300);
                mWorkerHandler.postDelayed(() -> Log.d(LOG_TAG, "executing task message"), 400);
                mWorkerHandler.sendEmptyMessage(5);
                mWorkerHandler.dump(new LogPrinter(Log.DEBUG, LOG_TAG), "");
            }
        });
    }

    private void displayDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Observe Message Queue");
        builder.setMessage("This example creates a worker thread " +
                "when the activity is created. When the user clicks " +
                "a button causing 'onClick' to be called, six messages " +
                "are added to the queue in different ways. Afterwards, " +
                "we observe the state of the message queue.");
        builder.create().show();
    }
}