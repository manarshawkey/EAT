package com.example.android.eat;
/*
 * Purpose: this example simulates a long-running operation
 * that is started when the user clicks a button. The long-running
 * operation is executed on a background thread, meanwhile, the UI thread
 * displays a progress bar that is removed when the background thread
 * reports the results back to the UI thread.
 */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Random;

public class HandlerExampleActivity extends AppCompatActivity {

    private static final String LOG_TAG = HandlerExampleActivity.class.getSimpleName();
    private static final int SHOW_PROGRESS_BAR = 1;
    private static final int HIDE_PROGRESS_BAR = 0;
    private final Handler mUiHandler;
    ProgressBar mProgressBar;
    private BackgroundThread mBackgroundThread;

    public HandlerExampleActivity() {
        mUiHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == SHOW_PROGRESS_BAR) {
                    Log.d(LOG_TAG, "MainLooper is handling a message " +
                            "to show progress bar");
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_example);
        mProgressBar = findViewById(R.id.progressBar);
        mBackgroundThread = new BackgroundThread();
        mBackgroundThread.start();
        mBackgroundThread.doWork();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundThread.exit();
    }

    private class BackgroundThread extends Thread{
        Handler mBackgroundHandler;

        @Override
        public void run() {
            //associate a looper with this thread
            Looper.prepare();
            //initialize a handler
            mBackgroundHandler = new Handler(Looper.myLooper());
            //start dispatching messages
            Looper.loop();
        }
        public void doWork(){
            //obtain a message to show the progress bar in the main ui thread
            Message uiMessage = Message.obtain(mUiHandler, SHOW_PROGRESS_BAR);
            mUiHandler.sendMessage(uiMessage);
            //sleep for a little while
            int sleepTime = new Random().nextInt(3000);
            SystemClock.sleep(sleepTime);
        }
        public void exit(){
            Log.d(LOG_TAG, "Background Thread Looper is quitting safely.");
            mBackgroundHandler.getLooper().quitSafely();
        }
    }
}